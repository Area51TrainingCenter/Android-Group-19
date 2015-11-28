package area51.pe.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

//Esta clase hereda de "AppCompatActivity" pero también implementa la interfaz "View.OnClickListener".
//Es por este motivo que podemos pasar este Activity como argumento a los método que requieran un objeto
//"OnClickListener", por ejemplo el método "setOnClickListener" de nuestro botón con ID "button_say_hello".
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_NAME = "pe.area51.myapplication.NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Establecemos el layout para este Activity.
        setContentView(R.layout.activity_main);
        //Del layout establecido, buscamos el View con ID "button_say_hello".
        //Si lo encuentra nos devolverá la referencia, de lo contrario devolverá "null".
        //Una vez obtenido el View, estableceremos su "OnClickListener".
        findViewById(R.id.button_say_hello)
                .setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_say_hello:
                EditText nameEditText = (EditText) findViewById(R.id.edittext_name);
                String name = extractText(nameEditText);
                showWelcome(name);
                break;
        }
    }

    private String extractText(EditText editText) {
        //Obtenemos el texto mediante el método "getText". Sin embargo como debemos devolver un
        //objeto "String", a este objeto "Editable" devuelto por el método "getText" debemos
        //convertirlo a "String", es por eso que llamamos al método "toString".
        return editText.getText().toString();
    }

    private void showWelcome(String name) {
        //Creamos el objeto "Intent".
        //En Android a este tipo de Intents les llaman "Explicit intent", ya que indicamos
        //de forma explícita la clase (en este caso Activity) que deseamos iniciar.
        Intent intent = new Intent(this, WelcomeActivity.class);
        //Asignamos el nombre que queremos enviar como parámetro en el Intent.
        intent.putExtra(EXTRA_NAME, name);
        //Iniciamos el Activity descrito en el Intent.
        startActivity(intent);
    }
}
