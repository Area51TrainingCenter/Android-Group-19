package pe.area51.fragmentapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;

import java.util.ArrayList;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager instance;

    private final static String DATABASE_NAME = "notes";
    private final static int DATABASE_VERSION = 1;

    public final static String TABLE_NOTES = "notes";
    public final static String NOTES_COLUMN_ID = "_id";
    public final static String NOTES_COLUMN_TITLE = "title";
    public final static String NOTES_COLUMN_CONTENT = "content";
    public final static String NOTES_COLUMN_CREATION_TIMESTAMP = "creationTimestamp";

    public static SQLiteManager getInstance(final Context context) {
        if (instance == null) {
            instance = new SQLiteManager(context.getApplicationContext());
        }
        return instance;
    }

    private SQLiteManager(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String creationScript = "CREATE TABLE notes (_id INTEGER PRIMARY KEY, title TEXT, content TEXT, creationTimestamp INTEGER)";
        db.execSQL(creationScript);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        Este método no debería ejecutarse ya que nuestra base de datos
        está en su primera versión.
         */
    }

    public ArrayList<Note> getNotes() {
        final SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        final String query = "SELECT * FROM notes";
        final Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        final ArrayList<Note> notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            final long id = cursor.getLong(cursor.getColumnIndex(NOTES_COLUMN_ID));
            final String title = cursor.getString(cursor.getColumnIndex(NOTES_COLUMN_TITLE));
            final String content = cursor.getString(cursor.getColumnIndex(NOTES_COLUMN_CONTENT));
            final long creationTimestamp = cursor.getLong(cursor.getColumnIndex(NOTES_COLUMN_CREATION_TIMESTAMP));
            final Note note = new Note(id, title, content, creationTimestamp);
            notes.add(note);
        }
        cursor.close();
        return notes;
    }

    public long insertNote(final Note note) {
        final SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        final ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_COLUMN_TITLE, note.getTitle());
        contentValues.put(NOTES_COLUMN_CONTENT, note.getContent());
        contentValues.put(NOTES_COLUMN_CREATION_TIMESTAMP, note.getCreationTimestamp());
        return sqLiteDatabase.insert(TABLE_NOTES, null, contentValues);
    }

}
