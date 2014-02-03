package net.sledzdev.shoppinglist.handlers;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;

import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import net.sledzdev.shoppinglist.R;
import net.sledzdev.shoppinglist.ShoppingListsActivity;
import net.sledzdev.shoppinglist.event.NewListEvent;
import net.sledzdev.shoppinglist.manager.ContentManager;
import net.sledzdev.shoppinglist.manager.OnUiThreadFutureCallback;
import net.sledzdev.shoppinglist.model.ShoppingList;

import java.util.concurrent.Future;

/**
 * Created by mariusz on 31.01.14.
 */
public class NewListEventHandler {

    private ContentManager contentManager;
    private Resources resources;
    private Activity activity;

    public NewListEventHandler(ShoppingListsActivity activity) {
        this.contentManager = activity.getContentManager();
        this.resources = activity.getResources();
        this.activity = activity;
    }

    @Subscribe
    public void onNewListEvent(final NewListEvent event) {
        final ShoppingList list = new ShoppingList(resources.getString(R.string.new_list_name));
        ListenableFuture future = contentManager.save(list);
        Futures.addCallback(future, new OnUiThreadFutureCallback(activity) {
            @Override
            protected void onSuccessUiThread(Object o) {
                event.adapter.addItem(list);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e("Shopping app error", throwable.toString());
            }
        });
    }
}
