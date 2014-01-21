package net.sledzdev.shoppinglist.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.sledzdev.shoppinglist.R;
import net.sledzdev.shoppinglist.model.ShoppingList;

/**
 * Created by Mariusz on 08.12.13.
 */
public class ShoppingListsAdapter extends DataModelAdapter<ShoppingList> {

    public ShoppingListsAdapter(Context context, DataModel<ShoppingList> model) {
        super(context, model);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //TODO: add event listeners
        ShoppingListHolder holder = getShoppingListHolder(convertView, parent);

        ShoppingList list = (ShoppingList) getItem(position);

        holder.listName.setText(list.name);

        return holder.main;
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
            holder.edit = (Button) main.findViewById(R.id.edlit_list);
            holder.listName = (TextView) main.findViewById(R.id.list_name);
            main.setTag(holder);
        }
        return holder;
    }

    public static class ShoppingListHolder {
        public TextView listName;
        public Button delete;
        public Button edit;
        public View main;
    }

}