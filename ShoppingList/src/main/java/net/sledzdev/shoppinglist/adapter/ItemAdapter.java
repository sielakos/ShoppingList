package net.sledzdev.shoppinglist.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import net.sledzdev.shoppinglist.R;
import net.sledzdev.shoppinglist.event.EventBusFactory;
import net.sledzdev.shoppinglist.event.ItemDeleteEvent;
import net.sledzdev.shoppinglist.model.ShoppingItem;

/**
 * Created by Mariusz on 22.12.13.
 */
public class ItemAdapter extends DataModelAdapter<ShoppingItem> {

    public ItemAdapter(Context context, DataModel<ShoppingItem> model) {
        super(context, model);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShoppingItemHolder holder = getShoppingItemHolder(convertView, parent);

        final ShoppingItem item = (ShoppingItem) getItem(position);

        addEvents(holder, item);

        setValues(holder, item);

        return holder.main;
    }

    private void addEvents(ShoppingItemHolder holder, final ShoppingItem item) {
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusFactory.getEventBus().post(new ItemDeleteEvent(item, ItemAdapter.this));
            }
        });
    }

    private void setValues(ShoppingItemHolder holder, ShoppingItem item) {
        holder.itemName.setText(item.name);
        holder.price.setText(item.price + "");
        holder.check.setChecked(item.checked);
    }

    private ShoppingItemHolder getShoppingItemHolder(View convertView, ViewGroup parent) {
        ShoppingItemHolder holder;
        if (convertView != null) {
            holder = (ShoppingItemHolder) convertView.getTag();
        } else {
            holder = new ShoppingItemHolder();
            View main = getInflater().inflate(R.layout.shoping_item_element, parent, false);

            holder.main = main;
            holder.itemName = (EditText) main.findViewById(R.id.item_name);
            holder.price = (EditText) main.findViewById(R.id.item_price);
            holder.delete = (Button) main.findViewById(R.id.delete_item);
            holder.check = (CheckBox) main.findViewById(R.id.check_item);

            main.setTag(holder);
        }
        return holder;
    }


    public static class ShoppingItemHolder {
        public View main;
        public EditText itemName;
        public Button delete;
        public CheckBox check;
        public EditText price;
    }
}
