package net.sledzdev.shoppinglist.handlers;

import android.app.Activity;

import com.google.common.eventbus.Subscribe;

import net.sledzdev.shoppinglist.event.ItemDialogOpenRequestEvent;

/**
 * Created by mariusz on 05.02.14.
 */
public class ItemDialogOpenRequestEventHandler {

    private Activity activity;

    public ItemDialogOpenRequestEventHandler(Activity activity) {
        this.activity = activity;
    }

    @Subscribe
    public void onItemDialogRequest(ItemDialogOpenRequestEvent event) {

    }
}
