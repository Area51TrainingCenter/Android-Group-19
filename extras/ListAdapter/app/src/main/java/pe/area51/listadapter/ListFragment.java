package pe.area51.listadapter;

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

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private final static int TEST_NOTES_SIZE = 100;
    private ListView listView;
    private ListFragmentInterface listFragmentInterface;

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
        listView.setAdapter(new NoteAdapter(getContext(), getTestData(TEST_NOTES_SIZE)));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listFragmentInterface != null) {
                    listFragmentInterface.onNoteSelected(((NoteAdapter) parent.getAdapter()).getItem(position));
                }
            }
        });
    }

    public void setListFragmentInterface(final ListFragmentInterface listFragmentInterface) {
        this.listFragmentInterface = listFragmentInterface;
    }

    private ArrayList<Note> getTestData(final int size) {
        final ArrayList<Note> notes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            final Note note = new Note("Title " + (i + 1),
                    "Content " + (i + 1),
                    System.currentTimeMillis());
            notes.add(note);
        }
        return notes;
    }

    public static class NoteAdapter extends ArrayAdapter<Note> {

        private final LayoutInflater layoutInflater;

        NoteAdapter(final Context context, final ArrayList<Note> notes) {
            super(context, 0, notes);
            layoutInflater = LayoutInflater.from(getContext());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View viewElement = layoutInflater.inflate(R.layout.note_element, parent, false);
            final TextView title = (TextView) viewElement.findViewById(R.id.note_title);
            final TextView date = (TextView) viewElement.findViewById(R.id.note_creation_date);
            final Note note = getItem(position);
            title.setText(note.getTitle());
            date.setText(String.valueOf(note.getCreationTimestamp()));
            return viewElement;
        }
    }

    public interface ListFragmentInterface {

        public void onNoteSelected(final Note note);

    }
}
