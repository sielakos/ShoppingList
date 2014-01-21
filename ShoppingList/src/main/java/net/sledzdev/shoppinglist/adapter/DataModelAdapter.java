package net.sledzdev.shoppinglist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.google.common.base.Optional;

/**
 * Created by Mariusz on 22.12.13.
 */
public abstract class DataModelAdapter<T extends ElementWithId> extends BaseAdapter {
    protected DataModel<T> model;
    protected Context context;

    public DataModelAdapter(Context context, DataModel<T> model) {
        this.context = context;
        this.model = model;
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
        Optional<T> maybeItem = model.getAtPosition(position);
        if (maybeItem.isPresent()) {
            return maybeItem.get().getId();
        }
        return -1;
    }

    protected LayoutInflater getInflater() {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
