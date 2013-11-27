package net.sledzdev.shoppinglist.content;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mariusz on 22.11.13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String NAME = "shopping.db";
    private static final int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ItemsTable.onCreate(db);
        ListsTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ItemsTable.onUpdate(db, oldVersion, newVersion);
        ListsTable.onUpdate(db, oldVersion, newVersion);
    }
}
