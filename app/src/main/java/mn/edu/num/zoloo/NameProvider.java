package mn.edu.num.zoloo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class NameProvider extends ContentProvider {
    public static final String AUTHORITY = "mn.edu.num.zoloo.NameProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/names");

    // MIME types
    public static final String CONTENT_TYPE = "mn.edu.num.zoloo/name";
    public static final String CONTENT_ITEM_TYPE = "mn.edu.num.zoloo/name";

    private static final UriMatcher nameUriMatcher;
    private static final int NAMES = 1;
    private static final int NAMES_ID = 2;

    static {
        nameUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        nameUriMatcher.addURI(AUTHORITY, "names", NAMES);
        nameUriMatcher.addURI(AUTHORITY, "names/#", NAMES_ID);
    }

    SQLiteDatabase db;
    @Override
    public boolean onCreate() {
        db = new DatabaseHelper(getContext()).getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, // id, name
                        @Nullable String selection, // where clause
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        Log.i(this.getClass().toString(), uri.toString());
//        Cursor cursor = db.rawQuery("select * from names", null);
        Cursor cursor = db.query("names", projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = nameUriMatcher.match(uri);
        switch (match) {
            case NAMES:
                return CONTENT_TYPE;
            case NAMES_ID:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.i(this.getClass().toString(), uri.toString());
        db.insert("names", null, contentValues);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String where, @Nullable String[] params) {
        Log.i(this.getClass().toString(), uri.toString());
        return db.delete("names", where, params);
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues contentValues,
                      @Nullable String where,
                      @Nullable String[] params) {
        Log.i(this.getClass().toString(), uri.toString());
        return db.update("names", contentValues, where, params);
    }
}
