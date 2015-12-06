package pe.area51.servicetest;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class MyService extends Service {

    //Este ID debe ser único a nivel de nuestra App.
    public static final int MY_SERVICE_FOREGROUND_ID = 1;

    private boolean isRunningForFirstTime = true;

    @Override
    public void onCreate() {
        super.onCreate();
        //Para iniciar nuestro servicio en modo Foreground, necesitamos primero una notificación.
        //Crearemos la notificación utilizando la clase Notification.Builder (Builder es una clase interna de Notification).
        final Notification.Builder notificationBuilder =
                new Notification.Builder(this)
                        .setContentTitle(getString(R.string.foreground_service_title))
                        .setContentText(getString(R.string.foreground_service_message))
                        .setSmallIcon(R.drawable.ic_service);
        //Ahora que tenemos descrita nuestra notificación, la vamos a construir.
        final Notification myServiceNotification;
        /*
        En el API 16 apareció un método llamado "build()" para construir la notificación.
        Sin embargo como nuestra App debe ser compatible desde el API 15, no podremos utilizar este método
        directamente, ya que ocurriría un error en tiempo de ejecución si se ejecuta en una versión anterior
        al API 16 (ya que el método no existe). Para evitar este problema pondremos un condicional donde
        utilizaremos el nuevo método si la versión de Android donde se ejecuta nuestra App es mayor o igual al
        API 16 y de lo contrario utilizaremos el método antiguo.
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            myServiceNotification = notificationBuilder.build();
        } else {
            myServiceNotification = notificationBuilder.getNotification();
        }
        /*
        Si iniciamos el servicio en modo Foreground, entonces el sistema le dará más prioridad a nuestro servicio,
        Siendo mucho más improbable que lo mate (por problemas de carga de batería, falta de memoria RAM, etc.).
        Este modo de inicio no es obligatorio. Cabe resaltar que este modo de inicio muestra una notificación en el sistema.
         */
        startForeground(MY_SERVICE_FOREGROUND_ID, myServiceNotification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isRunningForFirstTime) {
            Toast.makeText(this, R.string.service_first_execution, Toast.LENGTH_SHORT).show();
            isRunningForFirstTime = false;
        } else {
            Toast.makeText(this, R.string.service_in_execution, Toast.LENGTH_SHORT).show();
        }
        /*
        Este flag es muy importante:
        -START_NOT_STICKY: Si el sistema mata al servicio, este no se reiniciará.
        -START_STICKY: Si el sistema mata al servicio, este se reiniciará pero no se reenviará el último Intent que se envió al servicio.
        -START_REDELIVER_INTENT: Si el sistema mata al servicio, este se reiniciará y se reenviará el último Intent que recibió el servicio.
         */
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
    Si se ejecuta esta tarea larga, se bloquearán todos los componentes de nuestra App.
    Esto es así porque todos los componentes comparten el mismo hilo de ejecución
    (Main Thread, también conocido como UI Thread).
     */
    private void executeLongTask() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
