package pe.area51.listadapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class ContentFragment extends Fragment {

    private static final String ARG_NOTE = "note";

    private TextView textViewTitle;
    private TextView textViewDate;
    private TextView textViewContent;

    public static ContentFragment newInstance(final Note note) {
        final ContentFragment contentFragment = new ContentFragment();
        final Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_NOTE, note);
        contentFragment.setArguments(arguments);
        return contentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_content, container, false);
        textViewTitle = (TextView) view.findViewById(R.id.content_fragment_title);
        textViewDate = (TextView) view.findViewById(R.id.content_fragment_date);
        textViewContent = (TextView) view.findViewById(R.id.content_fragment_content);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showNote();
    }

    private void showNote() {
        final Bundle arguments = getArguments();
        final Note note = arguments.getParcelable(ARG_NOTE);
        textViewTitle.setText(note.getTitle());
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(note.getCreationTimestamp());
        textViewDate.setText(DateFormat.getInstance().format(calendar.getTime()));
        textViewContent.setText(note.getContent());
    }
}
