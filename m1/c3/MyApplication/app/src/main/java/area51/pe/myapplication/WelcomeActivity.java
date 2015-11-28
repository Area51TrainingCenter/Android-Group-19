package area51.pe.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Asignamos el Layout para este Activity.
        setContentView(R.layout.activity_welcome);
        //Obtenemos el nombre del Intent que inició este Activity.
        String name = getIntent().getStringExtra(MainActivity.EXTRA_NAME);
        showWelcome(name);
    }

    private void showWelcome(String name) {
        //Obtenemos la referencia de nuestro "TextView" identificado por el ID "textview_welcome"
        // y la guardamos en la variable "welcomeTextView".
        TextView welcomeTextView = (TextView) findViewById(R.id.textview_welcome);
        //Combinamos el nombre con el recurso String "welcome". El "String" resultante se asignará
        //como texto a "welcomeTextView".
        welcomeTextView.setText(getString(R.string.welcome, name));
    }

}
