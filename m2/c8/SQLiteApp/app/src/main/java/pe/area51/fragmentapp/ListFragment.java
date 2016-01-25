package pe.area51.fragmentapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ListFragment extends Fragment {

    private SQLiteManager sqLiteManager;
    private NotesAdapter notesAdapter;
    private ListView listView;

    private FragmentInterface fragmentInterface;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        sqLiteManager = SQLiteManager.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) view.findViewById(R.id.listview_elements);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final List<Note> notes = getNotes();
        notesAdapter = new NotesAdapter(getActivity(), notes);
        listView.setAdapter(notesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (fragmentInterface != null) {
                    fragmentInterface.onNoteSelected(notes.get(position));
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list_fragment_insert_note:
                insertNoteAndUpdateAdapter();
                return true;
        }
        return false;
    }

    private void insertNoteAndUpdateAdapter() {
        final String title = "Test Title";
        final String content = "Test Content";
        final long creationTimestamp = System.currentTimeMillis();
        final long id = sqLiteManager.insertNote(new Note(-1, title, content, creationTimestamp));
        final Note note = new Note(id, title, content, creationTimestamp);
        notesAdapter.add(note);
        notesAdapter.notifyDataSetChanged();
    }

    private ArrayList<Note> getNotes() {
        return sqLiteManager.getNotes();
    }

    public static class NotesAdapter extends ArrayAdapter<Note> {

        public NotesAdapter(final Context context, final List<Note> notes) {
            super(context, 0, notes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /*
            Este método es muy ineficiente, ya que continuamente estamos buscando los elementos
            por su ID, lo cual es un proceso lento. Recordar que por cada elemento de vista que
            se deba mostrar en el momento se llama a este método, esto quiere decir que si desplazamos
            el listado, se llamará a este método por cada elemento que se deba recrear en esa ubicación
            del listado.
             */
            final LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            final View elementView = layoutInflater.inflate(R.layout.list_element, parent, false);
            final TextView dateTextView = (TextView) elementView.findViewById(R.id.list_element_date);
            final TextView titleTextView = (TextView) elementView.findViewById(R.id.list_element_title);
            final TextView contentTextView = (TextView) elementView.findViewById(R.id.list_element_content);
            final Note note = getItem(position);
            final Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(note.getCreationTimestamp());
            dateTextView.setText(DateFormat.getInstance().format(calendar.getTime()));
            titleTextView.setText(note.getTitle());
            contentTextView.setText(note.getContent());
            return elementView;
        }
    }

    public ListFragment setFragmentInterface(final FragmentInterface fragmentInterface) {
        this.fragmentInterface = fragmentInterface;
        return this;
    }

    public static interface FragmentInterface {

        public void onNoteSelected(final Note note);

    }

}
