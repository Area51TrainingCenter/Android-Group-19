package pe.area51.fragmentmanager;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FirstFragment firstFragment = new FirstFragment();
        final SecondFragment secondFragment = new SecondFragment();
        firstFragment.setFragmentListInterface(new FirstFragment.FragmentInterface() {
            @Override
            public void onTouch() {
                fragmentManager
                        //Inicia la transacción.
                        .beginTransaction()
                        /*
                        Reemplaza los fragments del contenedor "place_holder" por
                        el fragment "secondFragment". Esto es lo mismo que quitar
                        todos los fragments de dicho contenedor y agregar este.
                         */
                        .replace(R.id.place_holder, secondFragment)
                        /*
                        Agrega esta transacción al back stack. Esto sirve para que la transacción
                        se revierta automáticamente al presionar el botón "back", de tal forma que
                        tendremos el fragment (o fragments) de la transacción anterior (en este caso
                        regresará a lo establecido en la transacción inicial).
                         */
                        .addToBackStack(null)
                        //Confirma la transacción.
                        .commit();
            }
        });
        //Realizamos la transacción inicial.
        fragmentManager
                .beginTransaction()
                .replace(R.id.place_holder, firstFragment)
                .commit();
    }

}
