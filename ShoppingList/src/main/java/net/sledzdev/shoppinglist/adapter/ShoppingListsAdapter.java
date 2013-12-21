package net.sledzdev.shoppinglist.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.base.Optional;

/**
 * Created by Mariusz on 08.12.13.
 */
public class ShoppingListsAdapter extends BaseAdapter {

    private DataModel<ShoppingList> model;
    private Context context;

    public ShoppingListsAdapter(Context context, DataModel<ShoppingList> model) {
        this.model = model;
        this.context = context;
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public Object getItem(int position) {
        return model.getAtPosition(position).get();
    }

    @Override
    public long getItemId(int position) {
        Optional<ShoppingList> maybeItem = model.getAtPosition(position);
        if (maybeItem.isPresent()) {
            return maybeItem.get().getId();
        }
        return -1;
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