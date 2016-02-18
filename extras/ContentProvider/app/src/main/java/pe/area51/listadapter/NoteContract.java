package pe.area51.listadapter;

import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

public class NoteContract {

    public static final Uri URI = Uri.parse("content://pe.area51.listadapter.ContentProvider/note");

    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String CREATION_TIMESTAMP = "creationTimestamp";
    public static final String MODIFICATION_TIMESTAMP = "modificationTimestamp";

    public static final String MIME_DIR = "vnd.android.cursor.dir/vnd.pe.area51.listadapter.note_dir";
    public static final String MIME_ITEM = "vnd.android.cursor.item/vnd.pe.area51.listadapter.note_item";

    public static ArrayList<Note> fromCursor(final Cursor cursor) {
        final ArrayList<Note> notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            final long id = cursor.getLong(cursor.getColumnIndex(ID));
            final String title = cursor.getString(cursor.getColumnIndex(TITLE));
            final String content = cursor.getString(cursor.getColumnIndex(CONTENT));
            final long creationTimestamp = cursor.getLong(cursor.getColumnIndex(CREATION_TIMESTAMP));
            final long modificationTimestamp = cursor.getLong(cursor.getColumnIndex(MODIFICATION_TIMESTAMP));
            final Note note = new Note(id, title, content, creationTimestamp, modificationTimestamp);
            notes.add(note);
        }
        cursor.close();
        return notes;
    }

}