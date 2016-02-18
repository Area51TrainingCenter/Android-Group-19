package pe.area51.listadapter;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

public class MyContentProvider extends ContentProvider {

    private SQLiteManager sqLiteManager;

    private static UriMatcher uriMatcher;

    private final static int URI_NO_MATCH = 0;
    private final static int URI_NOTE = 1;
    private final static int URI_NOTE_BY_ID = 2;

    static {
        uriMatcher = new UriMatcher(URI_NO_MATCH);
        uriMatcher.addURI(NoteContract.URI.getAuthority(), NoteContract.URI.getPath(), URI_NOTE);
        uriMatcher.addURI(NoteContract.URI.getAuthority(), NoteContract.URI.getPath() + "/#", URI_NOTE_BY_ID);
    }

    @Override
    public boolean onCreate() {
        sqLiteManager = SQLiteManager.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (uriMatcher.match(uri) != URI_NO_MATCH) {
            if (uriMatcher.match(uri) == URI_NOTE_BY_ID) {
                selection = SQLiteManager.TABLE_NOTES_ID + "=" + uri.getLastPathSegment();
            }
            return sqLiteManager.getReadableDatabase().query(SQLiteManager.TABLE_NOTES,
                    projection, selection, selectionArgs, null, null, sortOrder);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        if (uri != null) {
            switch (uriMatcher.match(uri)) {
                case URI_NOTE:
                    return NoteContract.MIME_DIR;
                case URI_NOTE_BY_ID:
                    return NoteContract.MIME_ITEM;
            }
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) == URI_NOTE) {
            final long id = sqLiteManager.getWritableDatabase()
                    .insert(SQLiteManager.TABLE_NOTES, null, values);
            return id != -1 ? ContentUris.withAppendedId(uri, id) : null;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (uriMatcher.match(uri) == URI_NOTE_BY_ID) {
            selection = SQLiteManager.TABLE_NOTES_ID + "=" + uri.getLastPathSegment();
            return sqLiteManager.getWritableDatabase()
                    .delete(SQLiteManager.TABLE_NOTES, selection, selectionArgs);
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (uriMatcher.match(uri) == URI_NOTE_BY_ID) {
            selection = SQLiteManager.TABLE_NOTES_ID + "=" + uri.getLastPathSegment();
            return sqLiteManager.getWritableDatabase()
                    .update(SQLiteManager.TABLE_NOTES, values, selection, selectionArgs);
        }
        return 0;
    }
}
