package net.sledzdev.shoppinglist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.sledzdev.shoppinglist.adapter.ShoppingList;

/**
 * A fragment representing a single Shopping List detail screen.
 * This fragment is either contained in a {@link ShoppingListsActivity}
 * in two-pane mode (on tablets) or a {@link ShoppingListDetailActivity}
 * on handsets.
 */
public class ShoppingListDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String LIST_ID = "shopping_list_id";

    /**
     * Shopping list this fragment is presenting.
     */
    private ShoppingList mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShoppingListDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(LIST_ID)) {
            //TODO: Load selected list from database.
            mItem = new ShoppingList(1, "ala ma kota");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shoppinglist_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.shoppinglist_detail)).setText(mItem.name);
        }

        return rootView;
    }
}
