package net.sledzdev.shoppinglist.handlers;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.eventbus.Subscribe;

import net.sledzdev.shoppinglist.ShoppingListDetailFragment;
import net.sledzdev.shoppinglist.event.ItemChangedEvent;
import net.sledzdev.shoppinglist.model.ShoppingItem;

/**
 * Created by mariusz on 12.02.14.
 */
public class ItemChangedEventHandler {

    private ShoppingListDetailFragment fragment;

    public ItemChangedEventHandler(ShoppingListDetailFragment fragment) {
        this.fragment = fragment;
    }

    @Subscribe
    public void onItemChangedEvent(final ItemChangedEvent event) {
        Optional<ShoppingItem> itemOptional = changeModelData(event);

        saveToDatabase(itemOptional);
    }

    private Optional<ShoppingItem> changeModelData(final ItemChangedEvent event) {
        if (fragment.getCurrentItemAdapter() != null) {
            Optional<ShoppingItem> itemOptional =
                    fragment.getCurrentItemAdapter().getModel().getAtId(event.itemId);

            return itemOptional.transform(new Function<ShoppingItem, ShoppingItem>() {
                @Override
                public ShoppingItem apply(ShoppingItem item) {
                    item.price = event.price;
                    item.name = event.name;
                    fragment.getCurrentItemAdapter().notifyDataSetChanged();
                    return item;
                }
            });
        }
        return Optional.absent();
    }

    private void saveToDatabase(Optional<ShoppingItem> itemOptional) {
        if (fragment.getManager() != null && itemOptional.isPresent()) {
            ShoppingItem item = itemOptional.get();
            fragment.getManager().save(item);
        }
    }
}
