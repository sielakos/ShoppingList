package net.sledzdev.shoppinglist.adapter;

import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Mariusz on 08.12.13.
 */
public class ListMapDataModel<T extends ElementWithId> implements DataModel<T> {

    private Map<Long, T> map;
    private List<T> list;

    public ListMapDataModel() {
        this(new ArrayList<T>());
    }

    public ListMapDataModel(List<T> list) {
        map = new HashMap<Long, T>();
        for(T item : list) {
            map.put(item.getId(), item);
        }
        this.list = new ArrayList<T>(list);
    }

    @Override
    public Optional<T> getAtPosition(int position) {
        try {
            return Optional.of(list.get(position));
        } catch (IndexOutOfBoundsException e) {
            return Optional.absent();
        }
    }

    @Override
    public Optional<T> getAtId(long id) {
        return Optional.fromNullable(map.get(id));
    }

    @Override
    public void addElement(T element) {
        if (getAtId(element.getId()).isPresent()) {
            throw new IllegalArgumentException("Element with that id already exists! " +
                    "Remove that element before adding other with the same id!");
        }

        list.add(element);
        map.put(element.getId(), element);
    }

    @Override
    public void removeElement(T element) {
        list.remove(map.remove(element.getId()));
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Iterator<T> iterator() {
        final Iterator<T> helpIterator = list.iterator();

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return helpIterator.hasNext();
            }

            @Override
            public T next() {
                return helpIterator.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public String toString() {
        return "ListMapDataModel{" +
                "list=" + list +
                '}';
    }
}
