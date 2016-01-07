package pe.area51.parcelabletest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_PERSON = "person";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_send_parcelable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Person person = new Person("Pedro", "Ortega", 44);
                startActivity(new Intent(MainActivity.this, InfoActivity.class)
                        .putExtra(EXTRA_PERSON, person));
            }
        });
    }
}
