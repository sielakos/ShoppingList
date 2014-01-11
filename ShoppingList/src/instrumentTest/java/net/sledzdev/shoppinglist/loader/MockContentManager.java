package net.sledzdev.shoppinglist.loader;

import android.content.ContentResolver;
import android.content.Context;

/**
 * Created by Mariusz on 11.01.14.
 */
public class MockContentManager extends ContentManager {

    private ContentResolver mockContentResolver;

    public MockContentManager(ContentResolver mockContentResolver) {
        super(null);
        this.mockContentResolver = mockContentResolver;
    }

    @Override
    protected ContentResolver initResolver(Context context) {
        return mockContentResolver;
    }
}
