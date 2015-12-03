package pe.area51.broadcastapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String MY_CUSTOM_BROADCAST = "pe.area51.broadcastapp.MY_CUSTOM_ACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_explicit_broadcast).setOnClickListener(this);
        findViewById(R.id.button_implicit_broadcast).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_explicit_broadcast:
                sendBroadcast(new Intent(this, MyBroadcastReceiver.class));
                break;
            case R.id.button_implicit_broadcast:
                //No es necesario lanzar nuestro intent mediante broadcast con este flag.
                //Este flag se utiliza para que los broadcast receivers de las aplicaciones
                //que están en estado detenido se activen de igual manera.
                //Recuerden que las aplicaciones que están en estado detenido son aquellas
                //que nunca se han ejecutado o aquellas a las que se les ha forzado el cierre
                //con la opción "Forzar cierre".
                //Desde Android Honeycomb TODOS los Intents de los eventos del sistema se lanzan
                //con el flag "Intent.FLAG_EXCLUDE_STOPPED_PACKAGES", por lo que no activarán
                //aquellos broadcast receivers de las aplicaciones que estén en estado detenido.
                sendBroadcast(new Intent(MY_CUSTOM_BROADCAST)
                        .setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES));
                //Si una aplicación que está en estado detenido se ejecuta al menos una vez ya sea
                //por el método convencional desde el lanzador o a través de un broadcast con un
                //Intent con el flag "Intent.FLAG_INCLUDE_STOPPED_PACKAGES", entonces dejará de estar
                //en estado detenido en ese momento.
                break;
        }
    }
}
