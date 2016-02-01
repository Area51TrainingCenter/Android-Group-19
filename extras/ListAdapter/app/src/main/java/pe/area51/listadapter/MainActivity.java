package pe.area51.listadapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements ListFragment.ListFragmentInterface {

    private FragmentManager fragmentManager;

    private static final String LIST_FRAGMENT_TAG = "list_fragment";
    private static final String CONTENT_FRAGMENT_TAG = "content_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            final ListFragment listFragment = new ListFragment();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, listFragment, LIST_FRAGMENT_TAG)
                    .commit();
            listFragment.setListFragmentInterface(this);
        } else {
            final Fragment fragment = fragmentManager.findFragmentByTag(LIST_FRAGMENT_TAG);
            if (fragment != null) {
                ((ListFragment) fragment).setListFragmentInterface(this);
            }
        }
    }

    @Override
    public void onNoteSelected(Note note) {
        final ContentFragment contentFragment = ContentFragment.newInstance(note);
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, contentFragment, CONTENT_FRAGMENT_TAG)
                .commit();
    }
}
