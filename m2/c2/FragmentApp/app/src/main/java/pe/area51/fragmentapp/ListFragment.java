package pe.area51.fragmentapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

    private final static int TEST_NOTES_COUNT = 100;
    private List<Note> notes;
    private ListView listView;

    private FragmentInterface fragmentInterface;

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
        notes = getTestNotes(TEST_NOTES_COUNT);
        listView.setAdapter(new NotesAdapter(getActivity(), notes));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (fragmentInterface != null) {
                    fragmentInterface.onNoteSelected(notes.get(position));
                }
            }
        });
    }

    private static ArrayList<Note> getTestNotes(final int count) {
        final ArrayList<Note> notes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            final Note note = new Note(
                    i + 1,
                    "Title " + (i + 1),
                    "Content " + (i + 1),
                    System.currentTimeMillis());
            notes.add(note);
        }
        return notes;
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
            calendar.setTimeInMillis(note.getTimestamp());
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
