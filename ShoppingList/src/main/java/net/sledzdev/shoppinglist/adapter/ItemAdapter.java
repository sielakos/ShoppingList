package net.sledzdev.shoppinglist.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        //TODO: replace mock element with true ShoppingListView and add event listeners
        TextView shoppingListView = getShoppingItemView(convertView);
        shoppingListView.setText(model.getAtPosition(position).get().name);
        return shoppingListView;
    }

    private TextView getShoppingItemView(View convertView) {
        TextView shoppingListView;
        if (convertView == null) {
            shoppingListView = new TextView(context);
        } else {
            shoppingListView = (TextView) convertView;
        }
        return shoppingListView;
    }
}
