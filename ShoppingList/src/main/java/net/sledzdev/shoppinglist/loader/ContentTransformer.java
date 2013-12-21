package net.sledzdev.shoppinglist.loader;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

/**
 * Created by Mariusz on 21.12.13.
 */
public interface ContentTransformer<T> {
    List<T> transformCursor(Cursor cursor);
    ContentValues transformValue(T value);
}
