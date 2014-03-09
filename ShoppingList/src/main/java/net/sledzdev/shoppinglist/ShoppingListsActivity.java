package net.sledzdev.shoppinglist;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import net.sledzdev.shoppinglist.event.EventBusFactory;
import net.sledzdev.shoppinglist.event.ListSelectedEvent;
import net.sledzdev.shoppinglist.manager.ContentManager;

public class ShoppingListsActivity extends FragmentActivity {

    public static final String CURRENT_LIST_ID = "current_list_id";

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

        if (savedInstanceState != null) {
            setCurrentListId(savedInstanceState.getLong(CURRENT_LIST_ID));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        shoppingListActibityHandlersRegister = new ShoppingListActivityHandlersRegister(this);
        shoppingListActibityHandlersRegister.registerHandlers();

        if (currentListId >= 0) {
            EventBusFactory.getEventBus().post(new ListSelectedEvent(currentListId));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        shoppingListActibityHandlersRegister.unregisterAll();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(CURRENT_LIST_ID, getCurrentListId());
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
