package pe.area51.sharedpreferencestest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final static String PREFERENCES_NAME = "last_visitor";
    private final static String KEY_LAST_VISITOR = "last_visitor";

    private TextView textViewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewMessage = (TextView) findViewById(R.id.textview_message);
        showLastVisitor();
        final EditText editTextVisitorName = (EditText) findViewById(R.id.edittext_visitor_name);
        findViewById(R.id.button_register_visitor)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String name = editTextVisitorName.getText().toString();
                        showWelcome(name);
                        saveLastVisitorName(name);
                    }
                });
    }

    private SharedPreferences getSharedPreferences() {
        return getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private String loadLastVisitorName() {
        return getSharedPreferences().getString(KEY_LAST_VISITOR, null);
    }

    private boolean saveLastVisitorName(final String name) {
        return getSharedPreferences()
                .edit()
                .putString(KEY_LAST_VISITOR, name)
                .commit();
    }

    private void showLastVisitor() {
        /*
        Cargar datos se considera una operación larga (operación de entrada/salida
        a la memoria secundaria). Sin embargo, en operaciones simples como esta
        podemos sacrificar un poco la velocidad para ganar simplicidad.
         */
        final String lastVisitorName = loadLastVisitorName();
        if (lastVisitorName == null) {
            textViewMessage.setText(getString(R.string.first_visitor));
        } else {
            textViewMessage.setText(getString(R.string.last_visitor, lastVisitorName));
        }
    }

    private void showWelcome(final String name) {
        textViewMessage.setText(getString(R.string.greetings, name));
    }

}
