package net.sledzdev.shoppinglist.model;

import junit.framework.TestCase;

/**
 * Created by Mariusz on 22.12.13.
 */
public class ShoppingListFactoryTest extends TestCase {
    public void testCreateShoppingList() throws Exception {
        ShoppingList list1 = ShoppingListFactory.createShoppingList(1, "ala");
        ShoppingList list2 = ShoppingListFactory.createShoppingList(1, "ala");

        assertSame(list1, list2);

        ShoppingList list3 = ShoppingListFactory.createShoppingList(1, "mala");

        assertSame(list1, list3);
        assertEquals("mala", list1.name);
    }

    public void testPutList() throws Exception {
        ShoppingList list1 = ShoppingListFactory.createShoppingList(1, "ala");
        ShoppingListFactory.putList(list1);

        ShoppingList list2 = new ShoppingList("mala");
        list2.setId(1);
        boolean thrown = false;
        try {
            ShoppingListFactory.putList(list2);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }
}
