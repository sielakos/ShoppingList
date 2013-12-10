package net.sledzdev.shoppinglist.content;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by Mariusz on 23.11.13.
 */
public abstract class TableUriProvider extends DatabaseUriContentProvider {

    private String tableName;

    public TableUriProvider(String tableName, int id, SQLiteOpenHelper openHelper) {
        super(id, openHelper);
        this.tableName = tableName;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        checkProjectionAndThrowIfWrong(projection);
        return getDatabase().query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)  {
        checkContentValues(values);
        long id = getDatabase().insert(tableName, null, values);
        if (id == -1) {
            throw new IllegalArgumentException("Bad values, couldn't insert row");
        }
        return getUriForId(id);
    }

    public abstract Uri getUriForId(long id);

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return getDatabase().delete(tableName, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        checkContentValues(values);
        return getDatabase().update(tableName, values, selection, selectionArgs);
    }
}
