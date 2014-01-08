package net.sledzdev.shoppinglist.model;

import junit.framework.TestCase;

/**
 * Created by Mariusz on 26.12.13.
 */
public class ShoppingItemBuilderTest extends TestCase {
    public void testCreateShoppingItem() throws Exception {
        ShoppingListFactory.createShoppingList(1, "ala");

        ShoppingItemBuilder builder = new ShoppingItemBuilder();
        builder.setId(23);
        builder.setList_id(1);
        builder.setName("makaron");
        builder.setPrice(2.34);

        ShoppingItem item = builder.createShoppingItem();

        assertEquals(23, item.getId());
        assertEquals("ala", item.list.name);
        assertEquals("makaron", item.name);
        assertEquals(2.34, item.price);
        assertFalse("item shouldn't be marked as new", item.newItem);

        builder = new ShoppingItemBuilder();
        builder.setName("dter");

        assertExceptionThrownByBuilder(builder);


        builder = new ShoppingItemBuilder();
        builder.setList_id(1);

        assertExceptionThrownByBuilder(builder);

        assertExceptionThrownByBuilder(new ShoppingItemBuilder());

        builder = new ShoppingItemBuilder();
        builder.setName("ala");
        builder.setPrice(2.35);
        builder.setList_id(1);

        item = builder.createShoppingItem();

        assertEquals("ala", item.name);
        assertEquals("ala", item.list.name);
        assertEquals(2.35, item.price);
        assertTrue("item should be marked as new", item.newItem);
    }

    protected void assertExceptionThrownByBuilder(ShoppingItemBuilder builder) {
        boolean thrown = false;
        try {
            builder.createShoppingItem();
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("exception shoould be thrown!", thrown);
    }
}
