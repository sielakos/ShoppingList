package net.sledzdev.shoppinglist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.common.base.Optional;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import net.sledzdev.shoppinglist.adapter.DataModel;
import net.sledzdev.shoppinglist.adapter.ItemAdapter;
import net.sledzdev.shoppinglist.event.ClearListEvent;
import net.sledzdev.shoppinglist.event.EventBusFactory;
import net.sledzdev.shoppinglist.event.ListTitleChangedEvent;
import net.sledzdev.shoppinglist.event.NewItemEvent;
import net.sledzdev.shoppinglist.event.TextWatcherAdapter;
import net.sledzdev.shoppinglist.manager.ContentManager;
import net.sledzdev.shoppinglist.manager.OnUiThreadFutureCallback;
import net.sledzdev.shoppinglist.model.ShoppingItem;
import net.sledzdev.shoppinglist.model.ShoppingList;

public class ShoppingListDetailFragment extends Fragment {
    public static final String LIST_ID = "shopping_list_id";
    private final DetailFragmentHandlersRegister detailFragmentHandlersRegister = new DetailFragmentHandlersRegister(this);

    private ContentManager manager;
    private ItemAdapter currentItemAdapter;
    private ListDetailViews listDetailViews;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        manager = ContentManager.createContentManager(activity);

        detailFragmentHandlersRegister.registerEventHandlers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_shoppinglist_detail, container, false);

        initViews(rootView);

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        detailFragmentHandlersRegister.unregisterAll();
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

        listDetailViews = new ListDetailViews(rootView).invoke();

        updateViews(list);

        addEventListeners(list);
    }

    private void updateViews(ShoppingList list) {
        listDetailViews.getListTitle().setText(list.name);
        populateItemsList(list, listDetailViews.getItems());
    }

    private void populateItemsList(ShoppingList list, final ListView items) {
        ListenableFuture<DataModel<ShoppingItem>> future = manager.loadItems(list);
        Futures.addCallback(future, new OnUiThreadFutureCallback<DataModel<ShoppingItem>>(getActivity()) {

            @Override
            protected void onSuccessUiThread(DataModel<ShoppingItem> shoppingItems) {
                if (getActivity() != null) {
                    currentItemAdapter = new ItemAdapter(getActivity(), shoppingItems);
                    items.setAdapter(currentItemAdapter);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("shopping app error", "exception: " + throwable);
            }
        });
    }

    private void addEventListeners(final ShoppingList list) {
       final EventBus eventBus = EventBusFactory.getEventBus();

       listDetailViews.getListTitle().addTextChangedListener(new TextWatcherAdapter() {
           @Override
           public void afterTextChanged(Editable s) {
               eventBus.post(new ListTitleChangedEvent(manager, list, s.toString()));
           }
       });

       listDetailViews.getClearList().setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                eventBus.post(new ClearListEvent(currentItemAdapter, list, manager));
           }
       });

       listDetailViews.getNewItem().setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                eventBus.post(new NewItemEvent(currentItemAdapter, list, manager));
           }
       });
    }

    public ContentManager getManager() {
        return manager;
    }

    public ItemAdapter getCurrentItemAdapter() {
        return currentItemAdapter;
    }

    private class ListDetailViews {
        private View rootView;
        private EditText listTitle;
        private ListView items;
        private Button newItem;
        private Button clearList;

        public ListDetailViews(View rootView) {
            this.rootView = rootView;
        }

        public EditText getListTitle() {
            return listTitle;
        }

        public ListView getItems() {
            return items;
        }

        public Button getNewItem() {
            return newItem;
        }

        public Button getClearList() {
            return clearList;
        }

        public ListDetailViews invoke() {
            listTitle = (EditText) rootView.findViewById(R.id.listTitle);
            items = (ListView) rootView.findViewById(R.id.items);
            newItem = (Button) rootView.findViewById(R.id.add_new_item);
            clearList = (Button) rootView.findViewById(R.id.clear_list);
            return this;
        }
    }
}
