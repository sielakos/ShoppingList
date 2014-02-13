package net.sledzdev.shoppinglist.handlers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.common.eventbus.Subscribe;

import net.sledzdev.shoppinglist.ItemDialogActivity;
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
        Intent intent = new Intent(activity, ItemDialogActivity.class);
        Bundle extras = getExtras(event);
        intent.putExtras(extras);
        Log.i("Shopping app", "opening dialog for editing item " + event.item);
        activity.startActivity(intent);
    }

    private Bundle getExtras(ItemDialogOpenRequestEvent event) {
        Bundle extras = new Bundle();
        extras.putLong(ItemDialogActivity.ITEM_ID_ARG, event.item.id);
        extras.putString(ItemDialogActivity.ITEM_NAME_ARG, event.item.name);
        extras.putDouble(ItemDialogActivity.ITEM_PRICE_ARG, event.item.price);
        return extras;
    }
}
