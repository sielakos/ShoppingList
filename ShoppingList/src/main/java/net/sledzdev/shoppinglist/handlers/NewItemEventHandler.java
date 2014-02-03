package net.sledzdev.shoppinglist.handlers;

import android.app.Activity;
import android.util.Log;

import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import net.sledzdev.shoppinglist.R;
import net.sledzdev.shoppinglist.event.NewItemEvent;
import net.sledzdev.shoppinglist.manager.OnUiThreadFutureCallback;
import net.sledzdev.shoppinglist.model.ShoppingItem;
import net.sledzdev.shoppinglist.model.ShoppingItemBuilder;

/**
 * Created by mariusz on 03.02.14.
 */
public class NewItemEventHandler {

    private Activity activity;

    public NewItemEventHandler(Activity activity) {
        this.activity = activity;
    }

    @Subscribe
    public void onNewItem(final NewItemEvent event) {
        final ShoppingItem item = getItem(event);
        ListenableFuture future = event.contentManager.save(item);
        Futures.addCallback(future, new OnUiThreadFutureCallback(activity) {
            @Override
            protected void onSuccessUiThread(Object o) {
                event.adapter.addItem(item);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e("Shopping app", throwable.toString());
            }
        });
    }

    private ShoppingItem getItem(NewItemEvent event) {
        ShoppingItemBuilder builder = new ShoppingItemBuilder();
        builder.setName(activity.getString(R.string.new_item_name));
        builder.setPrice(0);
        builder.setList(event.list);
        return builder.createShoppingItem();
    }
}
