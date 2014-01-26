package net.sledzdev.shoppinglist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import net.sledzdev.shoppinglist.adapter.DataModel;
import net.sledzdev.shoppinglist.adapter.ItemAdapter;
import net.sledzdev.shoppinglist.event.EventBusFactory;
import net.sledzdev.shoppinglist.event.ListTitleChangedEvent;
import net.sledzdev.shoppinglist.handlers.ListTitleChangedEventHandler;
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

        registerEventHandlers();
    }

    private void registerEventHandlers() {
        EventBusFactory.getEventBus().register(new ListTitleChangedEventHandler());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_shoppinglist_detail, container, false);

        initViews(rootView);

        return rootView;
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

    private ListenableFuture<Optional<ShoppingList>> initList() {
        if (getArguments().containsKey(LIST_ID)) {
            final long listId = getArguments().getLong(LIST_ID);
            return manager.getList(listId);
        }

        Optional<ShoppingList> absent = Optional.absent();
        return Futures.immediateFuture(absent);
    }

    private void initViewsForList(View rootView, Optional<ShoppingList> shoppingListOptional) {
        final ShoppingList list = shoppingListOptional.get();

        final EditText listTitle = (EditText) rootView.findViewById(R.id.listTitle);
        final ListView items = (ListView) rootView.findViewById(R.id.items);

        listTitle.setText(list.name);

        populateItemsList(list, items);

        addEventListeners(list, listTitle);
    }

    private void populateItemsList(ShoppingList list, final ListView items) {
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

    private void addEventListeners(final ShoppingList list, final EditText listTitle) {
        listTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                EventBusFactory.getEventBus().post(new ListTitleChangedEvent(manager, list, listTitle.getText().toString()));
            }
        });
    }
}
