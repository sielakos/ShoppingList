package net.sledzdev.shoppinglist;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.common.eventbus.EventBus;

import net.sledzdev.shoppinglist.event.EventBusFactory;
import net.sledzdev.shoppinglist.handlers.ItemDeleteEventHandler;
import net.sledzdev.shoppinglist.handlers.ListDeleteEventHandler;
import net.sledzdev.shoppinglist.handlers.ListSelectedEventHandler;
import net.sledzdev.shoppinglist.handlers.NewListEventHandler;
import net.sledzdev.shoppinglist.manager.ContentManager;

public class ShoppingListsActivity extends FragmentActivity {

    private boolean mTwoPane;
    private ContentManager contentManager;
    private long currentListId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist_list);
        contentManager = ContentManager.createContentManager(this);

        registerListeners();

        if (findViewById(R.id.shoppinglist_detail_container) != null) {
            mTwoPane = true;

            ((ShoppingListsFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.shoppinglist_list))
                    .setActivateOnItemClick(true);
        }
    }

    private void registerListeners() {
        EventBus eventBus = EventBusFactory.getEventBus();

        ListSelectedEventHandler listSelectedEventHandler = new ListSelectedEventHandler(this);
        eventBus.register(listSelectedEventHandler);

        ListDeleteEventHandler listDeleteHandler = new ListDeleteEventHandler(this);
        eventBus.register(listDeleteHandler);

        ItemDeleteEventHandler itemDeleteEventHandler = new ItemDeleteEventHandler(contentManager);
        eventBus.register(itemDeleteEventHandler);

        NewListEventHandler newListEventHandler = new NewListEventHandler(this);
        eventBus.register(newListEventHandler);
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
