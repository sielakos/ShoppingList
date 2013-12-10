package net.sledzdev.shoppinglist.adapter;

import junit.framework.TestCase;

/**
 * Created by Mariusz on 10.12.13.
 */
public class ListMapDataModelTest extends TestCase {
    private ListMapDataModel<Element> model;

    @Override
    public void setUp() throws Exception {
        Element[] elements = new Element[] {
            new Element(1),
            new Element(2),
            new Element(3)
        };

        model = new ListMapDataModel<Element>();
    }

    public void testGetAtPosition() throws Exception {

    }

    public void testGetAtId() throws Exception {

    }

    public void testAddElement() throws Exception {

    }

    public void testRemoveElement() throws Exception {

    }

    public void testSize() throws Exception {

    }

    private class Element implements ElementWithId {
        private long id;

        private Element(long id) {
            this.id = id;
        }

        @Override
        public long getId() {
            return id;
        }
    }
}
