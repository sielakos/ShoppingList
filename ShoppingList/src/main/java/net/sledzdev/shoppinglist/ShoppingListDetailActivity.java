package net.sledzdev.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

import com.google.common.eventbus.EventBus;

import net.sledzdev.shoppinglist.event.EventBusFactory;
import net.sledzdev.shoppinglist.handlers.ItemDeleteEventHandler;
import net.sledzdev.shoppinglist.manager.ContentManager;

/**
 * An activity representing a single Shopping List detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ShoppingListsActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link ShoppingListDetailFragment}.
 */
public class ShoppingListDetailActivity extends FragmentActivity {

    ContentManager contentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist_detail);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        contentManager = ContentManager.createContentManager(this);

        registerListeners();

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = getIntent().getExtras();
            ShoppingListDetailFragment fragment = new ShoppingListDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.shoppinglist_detail_container, fragment)
                    .commit();
        }
    }

    private void registerListeners() {
        EventBus eventBus = EventBusFactory.getEventBus();
        eventBus.register(new ItemDeleteEventHandler(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ShoppingListsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public ContentManager getContentManager() {
        return contentManager;
    }
}
