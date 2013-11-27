package net.sledzdev.shoppinglist.content;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Mariusz on 23.11.13.
 */
public interface UriContentProvider {

    public int getId();

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder);

    public String getType(Uri uri);

    public Uri insert(Uri uri, ContentValues values);

    public int delete(Uri uri, String selection, String[] selectionArgs);

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs);
}
