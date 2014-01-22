package net.sledzdev.shoppinglist.handlers;

import android.content.Intent;
import android.os.Bundle;

import com.google.common.eventbus.Subscribe;

import net.sledzdev.shoppinglist.R;
import net.sledzdev.shoppinglist.ShoppingListDetailActivity;
import net.sledzdev.shoppinglist.ShoppingListDetailFragment;
import net.sledzdev.shoppinglist.ShoppingListsActivity;
import net.sledzdev.shoppinglist.event.ListSelectedEvent;

public class ListSelectedEventHandler {
    private final ShoppingListsActivity shoppingListsActivity;

    public ListSelectedEventHandler(ShoppingListsActivity shoppingListsActivity) {
        this.shoppingListsActivity = shoppingListsActivity;
    }

    @Subscribe
    public void onItemSelected(ListSelectedEvent event) {
        long id = event.selectId;
        Bundle arguments = new Bundle();
        arguments.putLong(ShoppingListDetailFragment.LIST_ID, id);

        if (shoppingListsActivity.ismTwoPane()) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            ShoppingListDetailFragment fragment = new ShoppingListDetailFragment();
            fragment.setArguments(arguments);
            shoppingListsActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.shoppinglist_detail_container, fragment)
                    .commit();
        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(shoppingListsActivity, ShoppingListDetailActivity.class);
            detailIntent.putExtras(arguments);
            shoppingListsActivity.startActivity(detailIntent);
        }
    }
}