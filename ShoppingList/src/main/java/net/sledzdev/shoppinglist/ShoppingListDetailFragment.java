package net.sledzdev.shoppinglist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import net.sledzdev.shoppinglist.adapter.DataModel;
import net.sledzdev.shoppinglist.adapter.ItemAdapter;
import net.sledzdev.shoppinglist.manager.ContentManager;
import net.sledzdev.shoppinglist.model.ShoppingItem;
import net.sledzdev.shoppinglist.model.ShoppingList;

public class ShoppingListDetailFragment extends Fragment {
    public static final String LIST_ID = "shopping_list_id";

    private ContentManager manager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        manager = ContentManager.createContentManager(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_shoppinglist_detail, container, false);

        initViews(rootView);

        return rootView;
    }

    private ListenableFuture<Optional<ShoppingList>> initList() {
        if (getArguments().containsKey(LIST_ID)) {
            final long listId = getArguments().getLong(LIST_ID);
            return manager.getList(listId);
        }

        Optional<ShoppingList> absent = Optional.absent();
        return Futures.immediateFuture(absent);
    }

    private void initViews(final View rootView) {
        Futures.addCallback(initList(), new FutureCallback<Optional<ShoppingList>>() {
            @Override
            public void onSuccess(Optional<ShoppingList> shoppingListOptional) {
                if (shoppingListOptional.isPresent()) {
                    initViewsForList(rootView, shoppingListOptional);
                } else {
                    Log.d("shopping app error", "list with id " + getArguments().getLong(LIST_ID) + " couldn't be found!");
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("shopping app error", "exception: " + throwable);
            }
        });
    }

    private void initViewsForList(View rootView, Optional<ShoppingList> shoppingListOptional) {
        ShoppingList list = shoppingListOptional.get();

        TextView listTitle = (TextView) rootView.findViewById(R.id.listTitle);
        final ListView items = (ListView) rootView.findViewById(R.id.items);

        listTitle.setText(list.name);

        ListenableFuture<DataModel<ShoppingItem>> future = manager.loadItems(list);
        Futures.addCallback(future, new FutureCallback<DataModel<ShoppingItem>>() {
            @Override
            public void onSuccess(DataModel<ShoppingItem> shoppingItems) {
                if (getActivity() != null) {
                    ListAdapter listAdapter = new ItemAdapter(getActivity(), shoppingItems);
                    items.setAdapter(listAdapter);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("shopping app error", "exception: " + throwable);
            }
        });
    }
}
