package pe.area51.fragmentapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListFragment listFragment = new ListFragment();
        listFragment.setFragmentInterface(new ListFragment.FragmentInterface() {
            @Override
            public void onNoteSelected(Note note) {
                Toast.makeText(MainActivity.this, note.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.main_container, listFragment)
                .commit();
    }

}
