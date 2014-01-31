package net.sledzdev.shoppinglist.adapter;

import android.text.Editable;
import android.view.View;
import android.widget.CompoundButton;

import com.google.common.eventbus.EventBus;

import net.sledzdev.shoppinglist.event.EventBusFactory;
import net.sledzdev.shoppinglist.event.ItemCheckedChangedEvent;
import net.sledzdev.shoppinglist.event.ItemDeleteEvent;
import net.sledzdev.shoppinglist.event.ItemNameChangedEvent;
import net.sledzdev.shoppinglist.event.ItemPriceChangedEvent;
import net.sledzdev.shoppinglist.event.TextWatcherAdapter;
import net.sledzdev.shoppinglist.model.ShoppingItem;

public class ItemAdapterEventController {
    private final ItemAdapter itemAdapter;

    private TextWatcherAdapter itemNameTextWatcher;
    private TextWatcherAdapter itemPriceTextWatcher;

    public ItemAdapterEventController(ItemAdapter itemAdapter) {
        this.itemAdapter = itemAdapter;
    }

    void addEvents(ItemAdapter.ShoppingItemHolder holder, final ShoppingItem item) {
        final EventBus eventBus = EventBusFactory.getEventBus();

        setDeleteListener(holder, item, eventBus);
        setNameListener(holder, item, eventBus);
        setPriceListener(holder, item, eventBus);
        setCheckedEvent(holder, item, eventBus);
    }

    private void setDeleteListener(ItemAdapter.ShoppingItemHolder holder, final ShoppingItem item, final EventBus eventBus) {
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventBus.post(new ItemDeleteEvent(item, itemAdapter));
            }
        });
    }

    private void setNameListener(ItemAdapter.ShoppingItemHolder holder, final ShoppingItem item, final EventBus eventBus) {
        if (itemNameTextWatcher != null) {
            holder.itemName.removeTextChangedListener(itemNameTextWatcher);
        }
        itemNameTextWatcher = new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                eventBus.post(new ItemNameChangedEvent(item, itemAdapter, s.toString()));
            }
        };
        holder.itemName.addTextChangedListener(itemNameTextWatcher);
    }

    private void setPriceListener(ItemAdapter.ShoppingItemHolder holder, final ShoppingItem item, final EventBus eventBus) {
        if (itemPriceTextWatcher != null) {
            holder.price.removeTextChangedListener(itemPriceTextWatcher);
        }
        itemPriceTextWatcher = new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                double price = getNewItemPrice(s);
                eventBus.post(new ItemPriceChangedEvent(price, item, itemAdapter));
            }
        };
        holder.price.addTextChangedListener(itemPriceTextWatcher);
    }

    private double getNewItemPrice(Editable s) {
        double price  = 0;
        try {
            price = Double.parseDouble(s.toString());
        } catch (NumberFormatException e) {}
        return price;
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