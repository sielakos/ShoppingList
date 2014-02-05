package net.sledzdev.shoppinglist.adapter;

import android.view.View;
import android.widget.CompoundButton;

import com.google.common.eventbus.EventBus;

import net.sledzdev.shoppinglist.event.EventBusFactory;
import net.sledzdev.shoppinglist.event.ItemCheckedChangedEvent;
import net.sledzdev.shoppinglist.event.ItemDeleteEvent;
import net.sledzdev.shoppinglist.event.ItemDialogOpenRequestEvent;
import net.sledzdev.shoppinglist.model.ShoppingItem;

public class ItemAdapterEventController {
    private final ItemAdapter itemAdapter;

    public ItemAdapterEventController(ItemAdapter itemAdapter) {
        this.itemAdapter = itemAdapter;
    }

    void addEvents(ItemAdapter.ShoppingItemHolder holder, final ShoppingItem item) {
        final EventBus eventBus = EventBusFactory.getEventBus();

        setOpenItemDialogListener(holder, item, eventBus);
        setDeleteListener(holder, item, eventBus);
        setCheckedEvent(holder, item, eventBus);
    }

    private void setOpenItemDialogListener(ItemAdapter.ShoppingItemHolder holder, final ShoppingItem item, final EventBus eventBus) {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventBus.post(new ItemDialogOpenRequestEvent(itemAdapter, item));
            }
        };

        holder.itemName.setOnClickListener(clickListener);
        holder.price.setOnClickListener(clickListener);
    }

    private void setDeleteListener(ItemAdapter.ShoppingItemHolder holder, final ShoppingItem item, final EventBus eventBus) {
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventBus.post(new ItemDeleteEvent(item, itemAdapter));
            }
        });
    }

    private void setCheckedEvent(ItemAdapter.ShoppingItemHolder holder, final ShoppingItem item, final EventBus eventBus) {
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                eventBus.post(new ItemCheckedChangedEvent(itemAdapter, item, b));
            }
        });
    }
}