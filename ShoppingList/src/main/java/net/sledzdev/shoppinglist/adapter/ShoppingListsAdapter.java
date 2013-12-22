package net.sledzdev.shoppinglist.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        //TODO: replace mock element with true ShoppingListView and add event listeners
        TextView shoppingListView = getShoppingListView(convertView);
        shoppingListView.setText(model.getAtPosition(position).get().name);
        return shoppingListView;
    }

    private TextView getShoppingListView(View convertView) {
        TextView shoppingListView;
        if (convertView == null) {
            shoppingListView = new TextView(context);
        } else {
            shoppingListView = (TextView) convertView;
        }
        return shoppingListView;
    }
}