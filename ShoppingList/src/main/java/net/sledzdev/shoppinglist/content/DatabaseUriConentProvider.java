package net.sledzdev.shoppinglist.content;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mariusz on 23.11.13.
 */
public abstract class DatabaseUriConentProvider implements UriContentProvider {

    private int id;
    private SQLiteOpenHelper openHelper;

    protected DatabaseUriConentProvider(int id, SQLiteOpenHelper openHelper) {
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
}
