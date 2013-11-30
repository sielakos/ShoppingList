package net.sledzdev.shoppinglist.content;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by Mariusz on 22.11.13.
 */
public class ItemsTable {

    public static final String TABLE_NAME = "items";
    public static final String C_ID = BaseColumns._ID;
    public static final String C_NAME = "name";
    public static final String C_PRICE = "price";
    public static final String C_LIST_ID = "list_id";

    private static final String CREATE_QUERY = "CREATE TABLE " +
            TABLE_NAME + "(" +
            C_ID + " INTEGER PRIMARY KEY AUTOINCREAMENT, " +
            C_NAME + " TEXT, " +
            C_LIST_ID + " INTEGER, " +
            C_PRICE + " REAL, "+
            "FOREIGN KEY(" + C_LIST_ID + ") REFERENCES " +
            ListsTable.TABLE_NAME + "(" + ListsTable.C_ID + ")" +
            ");";

    public static final String[] ALLOWED_COLUMNS = new String[] {
      C_ID, C_NAME, C_PRICE, C_LIST_ID
    };

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_QUERY);
    }

    public static void onUpdate(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

}
