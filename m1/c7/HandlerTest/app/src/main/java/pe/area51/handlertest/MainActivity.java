package pe.area51.handlertest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MyAsyncTaskImplementation myAsyncTaskImplementation = new MyAsyncTaskImplementation<Boolean>() {
            ProgressDialog progressDialog;

            @Override
            public void onPreExecute() {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage(getString(R.string.loading_dialog_message));
                progressDialog.setTitle(R.string.loading_dialog_title);
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            public Boolean doInBackground() {
                longTask();
                return true;
            }

            @Override
            public void onPostExecute(Boolean result) {
                progressDialog.dismiss();
            }
        };
        findViewById(R.id.button_execute_long_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAsyncTaskImplementation.execute();
            }
        });

    }

    private void longTask() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
