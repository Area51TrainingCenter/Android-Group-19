package pe.area51.handlertest;

import android.os.Handler;

public abstract class MyAsyncTaskImplementation<Result> implements AsyncTaskInterface {

    private Thread workerThread;
    private Handler callingThreadHandler;
    private boolean isRunning = false;

    public abstract void onPreExecute();

    public abstract Result doInBackground();

    public abstract void onPostExecute(Result result);

    @Override
    public boolean execute() {
        /*
        Creamos este booleano para evitar que este método se ejecute
        si ya se encuentra en ejecución.
         */
        if (!isRunning) {
            isRunning = true;
            /*
            Cuando creamos el objeto Handler, este podrá manipular el hilo desde
            donde se creó.
             */
            callingThreadHandler = new Handler();
            onPreExecute();
            //Creamos otro hilo.
            workerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    /*
                    Ejecutamos el método "doInBackground()" en este hilo
                    y capturamos la respuesta.
                     */
                    final Result result = doInBackground();
                    /*
                    Utilizando nuestro objeto Handler, ejecutaremos el método
                    "onPostExecute()" en el hilo inicial.
                     */
                    callingThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onPostExecute(result);
                            isRunning = false;
                        }
                    });
                }
            });
            workerThread.start();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean stop() {
        /*
        Ya que este método no está implementado, lanzamos una excepción "Operación no soportada".
         */
        throw new UnsupportedOperationException();
    }
}
