package net.sledzdev.shoppinglist.content;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by Mariusz on 22.11.13.
 */
public class ListsTable {

    public static final String TABLE_NAME = "lists";
    public static final String C_ID = BaseColumns._ID;
    public static final String C_NAME = "name";
    public static final String C_SHORTCUT = "shortcut";

    private static final String CREATE_QUERY = "CREATE TABLE " +
            TABLE_NAME + "(" +
            C_ID + " INTEGER PRIMARY KEY AUTOINCREAMENT, " +
            C_NAME + " TEXT, " +
            C_SHORTCUT + " INTEGER);";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_QUERY);
    }

    public static void onUpdate(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

}
