package net.sledzdev.shoppinglist.content;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mariusz on 23.11.13.
 */
public abstract class DatabaseUriContentProvider implements UriContentProvider {

    private int id;
    private SQLiteOpenHelper openHelper;

    protected DatabaseUriContentProvider(int id, SQLiteOpenHelper openHelper) {
        this.id = id;
        this.openHelper = openHelper;
    }

    @Override
    public int getId() {
        return id;
    }

    protected SQLiteDatabase getDatabase() {
        return openHelper.getWritableDatabase();
    }

    protected abstract boolean checkProjection(String[] projection);

    protected void checkProjectionAndThrowIfWrong(String[] projection) {
        if (projection != null && (!checkProjection(projection) || projection.length <= 0)) {
            throw new IllegalArgumentException("invalid projection argument!");
        }
    }
}
