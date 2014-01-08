package net.sledzdev.shoppinglist.adapter;

import com.google.common.base.Optional;

/**
 * Created by Mariusz on 08.12.13.
 */
public interface DataModel<T extends ElementWithId> extends Iterable<T> {
    public Optional<T> getAtPosition(int position);
    public Optional<T> getAtId(long id);
    public void addElement(T element);
    public void removeElement(T element);
    public int size();
}
