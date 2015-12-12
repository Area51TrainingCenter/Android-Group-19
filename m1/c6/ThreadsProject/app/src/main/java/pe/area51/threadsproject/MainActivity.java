package pe.area51.threadsproject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_ui_thread).setOnClickListener(this);
        findViewById(R.id.button_worker_thread).setOnClickListener(this);
        findViewById(R.id.button_async_task).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_ui_thread:
                runLongTaskOnUiThread();
                break;
            case R.id.button_worker_thread:
                runOnWorkerThread();
                break;
            case R.id.button_async_task:
                runOnAsyncTask();
                break;
        }
    }

    private ProgressDialog createProgressDialog() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.loading_dialog_title);
        progressDialog.setMessage(getString(R.string.loading_dialog_message));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    private void runLongTaskOnUiThread() {
        /*
        En este caso ejecutaremos la tarea larga en el hilo principal.
        Esta ejecución bloqueará el hilo principal, ya que en un mismo
        hilo todas las rutinas se ejecutan en serie. Debemos recordar
        que en el hilo principal la pantalla se dibuja, por lo que ejecutar
        la tarea larga bloqueará también las rutinas de dibujado.
        */
        ProgressDialog progressDialog = createProgressDialog();
        //Mostramos el diálogo de carga.
        progressDialog.show();
        /*
        Inmediatamente después ejecuatamos la tarea larga.
        Al ejecutarla, la pantalla no se dibujará hasta que esta
        tarea termine. El dibujado de la pantalla está implícito en este
        hilo (no tenemos que llamar a las instrucciones de dibujo a mano,
        el sistema lo hace por nosotros).
        */
        longTask();
        /*
        Luego de ejecutar la tarea larga, ejecutamos la rutina para ocultar el diálogo.
        Parecerá que nunca llamamos al diálogo (puesto que no se verá en la pantalla).
        Lo que en verdad ocurre es que luego de llamar al diálogo ejecutamos la tarea larga y
        esto evita que la pantalla se redibuje, e inmediatamente después de ejecutarla quitamos
        el diálogo, por lo que este no tuvo oportunidad de ser dibujado.
         */
        progressDialog.dismiss();
    }

    private void runOnWorkerThread() {
        final ProgressDialog progressDialog = createProgressDialog();
        //Mostramos el diálogo.
        progressDialog.show();
        /*
        Creamos el objeto Thread y le pasamos como argumento
        el objeto Runnable con las rutinas a ser ejecutadas.
        Luego de esto ejecutamos el hilo llamando a su método
        "start()".
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                La tarea larga se ejecuta en un hilo separado, por lo que el diálogo
                se está dibujando desde donde lo llamamos, en el hilo principal.
                 */
                longTask();
                /*
                El diálogo dejará de mostrarse. Recordar que sólo podemos modificar
                la interfaz gráfica desde el hilo principal (o hilo de la interfaz gráfica).
                Sin embargo el método "dismiss()" de este diálogo se ejecuta automáticamente
                desde el hilo de donde se creó originalmente (lo creamos en el hilo principal).
                Es por eso que esto no genera una excepción.
                 */
                progressDialog.dismiss();
            }
        }).start();
    }

    private void runOnAsyncTask() {
        new AsyncTask<Void, Void, Void>() {
            private ProgressDialog progressDialog;

            //Este método se ejecuta primero.
            @Override
            protected void onPreExecute() {
                progressDialog = createProgressDialog();
                progressDialog.show();
            }

            /*
            Este método se ejecuta después, en otro hilo.
            La respuesta de este método se envía como argumento al método
            "onPostExecute()".
             */
            @Override
            protected Void doInBackground(Void[] params) {
                longTask();
                return null;
            }


            /*
            Este método se ejecuta al final, de vuelta en el hilo principal.
            Su parámetro de entrada es la respuesta del método "doInBackground()".
             */
            @Override
            protected void onPostExecute(Void o) {
                progressDialog.dismiss();
            }
        }.execute();
    }

    private void longTask() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
