package net.sledzdev.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;


/**
 * An activity representing a list of Lists. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ShoppingListDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ShoppingListsFragment} and the item details
 * (if present) is a {@link ShoppingListDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ShoppingListsFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ShoppingListsActivity extends FragmentActivity
        implements ShoppingListsFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist_list);

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

    /**
     * Callback method from {@link ShoppingListsFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(long id) {
        Bundle arguments = new Bundle();
        arguments.putLong(ShoppingListDetailFragment.LIST_ID, id);

        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            ShoppingListDetailFragment fragment = new ShoppingListDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.shoppinglist_detail_container, fragment)
                    .commit();
        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ShoppingListDetailActivity.class);
            detailIntent.putExtras(arguments);
            startActivity(detailIntent);
        }
    }
}
