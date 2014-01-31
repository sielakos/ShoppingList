package net.sledzdev.shoppinglist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import net.sledzdev.shoppinglist.adapter.DataModel;
import net.sledzdev.shoppinglist.adapter.ShoppingListsAdapter;
import net.sledzdev.shoppinglist.event.EventBusFactory;
import net.sledzdev.shoppinglist.event.ListSelectedEvent;
import net.sledzdev.shoppinglist.manager.ContentManager;
import net.sledzdev.shoppinglist.manager.OnUiThreadFutureCallback;
import net.sledzdev.shoppinglist.model.ShoppingItem;
import net.sledzdev.shoppinglist.model.ShoppingItemBuilder;
import net.sledzdev.shoppinglist.model.ShoppingList;

/**
 * A list fragment representing a list of Lists. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link ShoppingListDetailFragment}.
 * <p/>
 */
public class ShoppingListsFragment extends ListFragment {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;
    private ContentManager contentManager;
    private Button newListBtn;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShoppingListsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.shopping_lists, container);
        newListBtn = (Button) rootView.findViewById(R.id.new_list);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        initContentManagerAndLoadLists();
    }

    private void initContentManagerAndLoadLists() {
        contentManager = ContentManager.createContentManager(this.getActivity());

        //Temporary creation of Shopping List each time this method is run
        initMockData();

        ListenableFuture<DataModel<ShoppingList>> future = contentManager.loadShoppingListsModel();
        Futures.addCallback(future, new OnUiThreadFutureCallback<DataModel<ShoppingList>>(getActivity()) {
            @Override
            protected void onSuccessUiThread(DataModel<ShoppingList> shoppingLists) {
                ShoppingListsAdapter shoppingListsAdapter = new ShoppingListsAdapter(getActivity(), shoppingLists);
                setListAdapter(shoppingListsAdapter);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("shopping app error", "couldn't load shopping lists, error: " + throwable);
            }
        });
    }

    private void initMockData() {
        for (int i = 0; i < 2; i++) {
            createList("list nr." + i);
        }
    }

    private void createList(String listName) {
        final ShoppingList list = new ShoppingList(listName);

        FutureCallback callback = new FutureCallback() {
            @Override
            public void onSuccess(Object o) {
                for (int i = 0; i < 4; i++) {
                    ShoppingItemBuilder builder = new ShoppingItemBuilder();
                    builder.setName("item nr." + i);
                    builder.setList(list);
                    builder.setPrice(i * 2);
                    ShoppingItem item = builder.createShoppingItem();
                    contentManager.save(item);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("shopping app error", "exception: " + throwable);
            }
        };

        Futures.addCallback(contentManager.save(list), callback);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        EventBusFactory.getEventBus().post(new ListSelectedEvent(id));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

}
