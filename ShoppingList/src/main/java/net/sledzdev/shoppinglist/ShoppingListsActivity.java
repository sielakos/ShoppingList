package net.sledzdev.shoppinglist;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.common.eventbus.EventBus;

import net.sledzdev.shoppinglist.event.EventBusFactory;
import net.sledzdev.shoppinglist.handlers.ListDeleteEventHandler;
import net.sledzdev.shoppinglist.handlers.ListSelectedEventHandler;
import net.sledzdev.shoppinglist.manager.ContentManager;


/**
 * An activity representing a list of Lists. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ShoppingListDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ShoppingListsFragment} and the item details
 * (if present) is a {@link ShoppingListDetailFragment}.
 * <p/>
 */
public class ShoppingListsActivity extends FragmentActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ContentManager contentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist_list);
        contentManager = ContentManager.createContentManager(this);

        registerListeners();

        if (findViewById(R.id.shoppinglist_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
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
    }

    public boolean ismTwoPane() {
        return mTwoPane;
    }

    public ContentManager getContentManager() {
        return contentManager;
    }
}
