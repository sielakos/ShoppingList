package net.sledzdev.shoppinglist.handlers;

import android.app.Activity;
import android.util.Log;

import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import net.sledzdev.shoppinglist.event.ClearListEvent;
import net.sledzdev.shoppinglist.manager.OnUiThreadFutureCallback;
import net.sledzdev.shoppinglist.model.ShoppingItem;

/**
 * Created by mariusz on 01.02.14.
 */
public class ClearListEventHandler {

    Activity activity;

    public ClearListEventHandler(Activity activity) {
        this.activity = activity;
    }

    @Subscribe
    public void onClearList(final ClearListEvent event) {
        ListenableFuture<Integer> futureClearResult = event.contentManager.clearCheckedForList(event.list);
        Futures.addCallback(futureClearResult, new OnUiThreadFutureCallback<Integer>(activity) {
            @Override
            protected void onSuccessUiThread(Integer o) {
                for (ShoppingItem item : event.adapter.getModel()) {
                    item.checked = false;
                }
                event.adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e("Shopping app error", throwable.toString());
            }
        });
    }

}
