package net.sledzdev.shoppinglist.adapter;

import junit.framework.TestCase;

import java.util.Arrays;

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

        model = new ListMapDataModel<Element>(Arrays.asList(elements));
    }

    public void testGetAtPosition() throws Exception {
        for (int i = 0; i < model.size(); i++) {
            assertEquals(new Element(i + 1), model.getAtPosition(i).orNull());
        }
    }

    public void testGetAtId() throws Exception {
        for (int i = 1; i <= model.size(); i++) {
            assertEquals(new Element(i), model.getAtId(i).orNull());
        }
    }

    public void testAddElement() throws Exception {
        for (int i = 10; i < 20; i++) {
            model.addElement(new Element(i));
        }

        for (int i = 10; i < 20; i++) {
            assertEquals(new Element(i), model.getAtId(i).orNull());
        }

        boolean thrown = false;
        try {
            model.addElement(new Element(13));
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("Exception must be thrown!", thrown);
    }

    public void testRemoveElement() throws Exception {
        assertTrue("Element must be present before removal!", model.getAtId(1).isPresent());

        model.removeElement(new Element(1));

        assertFalse("Element shouldn't have id 1", model.getAtPosition(0).get().getId() == 1);
        assertFalse("Element should be removed!", model.getAtId(1).isPresent());

        assertEquals(2, model.size());
    }

    public void testSize() throws Exception {
        assertEquals(3, model.size());
    }

    static public class Element implements ElementWithId {
        private long id;

        private Element(long id) {
            this.id = id;
        }

        @Override
        public long getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Element) {
                return ((Element) o).getId() == id;
            }
            return false;
        }
    }
}
