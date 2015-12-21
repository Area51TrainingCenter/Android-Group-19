package pe.area51.fragmentproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Los fragments están declarados de forma estática en el layout.
        Sin embargo, este método no se presta para cambiar de forma dinámica
        los fragments que tenemos. En su lugar si se deseam cambiar los fragments
        de forma dinámica, debemos utilizar la clase FragmentManager.
         */
        //Más información: http://developer.android.com/intl/es/training/basics/fragments/index.html
        setContentView(R.layout.activity_main);
    }
}
