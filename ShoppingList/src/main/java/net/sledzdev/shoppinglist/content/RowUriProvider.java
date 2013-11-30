package net.sledzdev.shoppinglist.content;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by Mariusz on 23.11.13.
 */
public abstract class RowUriProvider extends DatabaseUriContentProvider {

    private String tableName;
    private String columnId;

    public RowUriProvider(String tableName, String columnId, int id, SQLiteOpenHelper openHelper) {
        super(id, openHelper);
        this.tableName = tableName;
        this.columnId = columnId;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        checkProjectionAndThrowIfWrong(projection);
        return getDatabase().query(tableName, projection, columnId + " = ?", new String[]{uri.getLastPathSegment()},
                null, null, null);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new IllegalArgumentException("Can't insert on single row!");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return getDatabase().delete(tableName,
                columnId + " = ?", new String[]{uri.getLastPathSegment()});
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return getDatabase().update(tableName, values,
                columnId + " = ?", new String[]{uri.getLastPathSegment()});
    }
}
