package net.sledzdev.shoppinglist.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.common.base.Optional;
import com.google.common.eventbus.EventBus;

import net.sledzdev.shoppinglist.R;
import net.sledzdev.shoppinglist.event.EventBusFactory;
import net.sledzdev.shoppinglist.event.ListDeleteEvent;
import net.sledzdev.shoppinglist.model.ShoppingList;

/**
 * Created by Mariusz on 08.12.13.
 */
public class ShoppingListsAdapter extends DataModelAdapter<ShoppingList> {

    private final EventBus eventBus;
    private long selectedId = -10020;

    public ShoppingListsAdapter(Context context, DataModel<ShoppingList> model) {
        super(context, model);

        eventBus = EventBusFactory.getEventBus();
        eventBus.register(new ShoppingListAdapterEventsHandler(this));
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShoppingListHolder holder = getShoppingListHolder(convertView, parent);

        ShoppingList list = (ShoppingList) getItem(position);

        setListeners(holder, list);

        holder.listName.setText(list.name);

        if (list.getId() == selectedId) {
            holder.listName.setTypeface(null, Typeface.BOLD);
        } else {
            holder.listName.setTypeface(null, Typeface.NORMAL);
        }

        return holder.main;
    }

    private void setListeners(ShoppingListHolder holder, final ShoppingList list) {
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusFactory.getEventBus().post(new ListDeleteEvent(list.getId(), ShoppingListsAdapter.this));
            }
        });
    }

    private ShoppingListHolder getShoppingListHolder(View convertView, ViewGroup parent) {
        ShoppingListHolder holder;
        if (convertView != null) {
            holder = (ShoppingListHolder) convertView.getTag();
        } else {
            View main = getInflater().inflate(R.layout.shopping_list_element, parent, false);
            holder = new ShoppingListHolder();
            holder.main = main;
            holder.delete = (Button) main.findViewById(R.id.delete_list);
            holder.listName = (TextView) main.findViewById(R.id.list_name);
            main.setTag(holder);
        }
        return holder;
    }

    public static class ShoppingListHolder {
        public TextView listName;
        public Button delete;
        public View main;
    }

    public long getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(long selectedId) {
        this.selectedId = selectedId;
    }
}