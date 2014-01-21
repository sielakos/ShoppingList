package net.sledzdev.shoppinglist.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import net.sledzdev.shoppinglist.R;
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
        //TODO: add event listeners
        ShoppingItemHolder holder = getShoppingItemHolder(convertView, parent);

        ShoppingItem item = (ShoppingItem) getItem(position);

        holder.itemName.setText(item.name);
        holder.price.setText(item.price + "");

        return holder.main;
    }

    private ShoppingItemHolder getShoppingItemHolder(View convertView, ViewGroup parent) {
        ShoppingItemHolder holder;
        if (convertView != null) {
            holder = (ShoppingItemHolder) convertView.getTag();
        } else {
            holder = new ShoppingItemHolder();
            View main = getInflater().inflate(R.layout.shoping_item_element, parent, false);

            holder.main = main;
            holder.itemName = (TextView) main.findViewById(R.id.item_name);
            holder.delete = (Button) main.findViewById(R.id.delete_item);
            holder.check = (CheckBox) main.findViewById(R.id.check_item);
            holder.price = (TextView) main.findViewById(R.id.item_price);
            main.setTag(holder);
        }
        return holder;
    }


    public static class ShoppingItemHolder {
        public View main;
        public TextView itemName;
        public Button delete;
        public CheckBox check;
        public TextView price;
    }
}
