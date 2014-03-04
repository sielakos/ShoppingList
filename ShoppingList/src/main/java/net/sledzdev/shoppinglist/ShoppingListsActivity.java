package net.sledzdev.shoppinglist;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import net.sledzdev.shoppinglist.manager.ContentManager;

public class ShoppingListsActivity extends FragmentActivity {

    private ShoppingListActivityHandlersRegister shoppingListActibityHandlersRegister;
    private boolean mTwoPane;
    private ContentManager contentManager;
    private long currentListId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist_list);
        contentManager = ContentManager.createContentManager(this);

        if (findViewById(R.id.shoppinglist_detail_container) != null) {
            mTwoPane = true;

            ((ShoppingListsFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.shoppinglist_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        shoppingListActibityHandlersRegister = new ShoppingListActivityHandlersRegister(this);
        shoppingListActibityHandlersRegister.registerHandlers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        shoppingListActibityHandlersRegister.unregisterAll();
    }

    public boolean ismTwoPane() {
        return mTwoPane;
    }

    public ContentManager getContentManager() {
        return contentManager;
    }

    public long getCurrentListId() {
        return currentListId;
    }

    public void setCurrentListId(long currentListId) {
        this.currentListId = currentListId;
    }
}
