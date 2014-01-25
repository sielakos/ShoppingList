package net.sledzdev.shoppinglist.manager;

import android.content.ContentResolver;
import android.test.ProviderTestCase2;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.ListenableFuture;

import net.sledzdev.shoppinglist.adapter.DataModel;
import net.sledzdev.shoppinglist.model.ShoppingItem;
import net.sledzdev.shoppinglist.model.ShoppingItemBuilder;
import net.sledzdev.shoppinglist.model.ShoppingList;
import net.sledzdev.shoppinglist.content.ShoppingListProvider;
import net.sledzdev.shoppinglist.content.ShoppingProviderContract;
import net.sledzdev.shoppinglist.model.ShoppingListFactory;

/**
 * Created by Mariusz on 21.12.13.
 */
public class ContentManagerTests extends ProviderTestCase2<ShoppingListProvider> {

    private ContentResolver mockContentResolver;
    private ContentManager manager;

    public ContentManagerTests() {
        super(ShoppingListProvider.class, ShoppingProviderContract.AUTHORITY);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mockContentResolver = getMockContentResolver();
        manager = new MockContentManager(mockContentResolver);
        ShoppingListFactory.clearLists();
    }

    public void testContentManagerShoppingListsMethods() throws Exception {
        for (int i = 0; i < 10; i++) {
            manager.save(new ShoppingList("list number " + i)).get();
        }

        ListenableFuture<DataModel<ShoppingList>> futureLists = manager.loadShoppingListsModel();
        DataModel<ShoppingList> dataModel = futureLists.get();

        assertEquals(10, dataModel.size());

        for (int i = 0; i < 10; i++) {
            ShoppingList shoppingList = dataModel.getAtPosition(i).get();
            assertEquals("list number " + i, shoppingList.name);
        }

        ShoppingList shoppingList = dataModel.getAtPosition(4).get();
        manager.remove(shoppingList);

        futureLists = manager.loadShoppingListsModel();
        dataModel = futureLists.get();

        assertEquals(9, dataModel.size());
        assertFalse("list should not be present after removal", dataModel.getAtId(shoppingList.getId()).isPresent());

        shoppingList = dataModel.getAtPosition(2).get();
        shoppingList.name = "tartak";
        manager.save(shoppingList);

        futureLists = manager.loadShoppingListsModel();
        dataModel = futureLists.get();
        shoppingList = dataModel.getAtPosition(2).get();

        assertEquals("tartak", shoppingList.name);
    }

    public void testGetList() throws Exception {
        ShoppingList list = new ShoppingList("ala");
        assertEquals(-1, list.getId());
        manager.save(list).get();
        assertTrue("list id >= 0", list.getId() >= 0);

        ListenableFuture<Optional<ShoppingList>> futureOptionalList = manager.getList(list.getId() + 20);
        Optional<ShoppingList> optionalList = futureOptionalList.get();

        assertFalse(optionalList.isPresent());

        optionalList = manager.getList(list.getId()).get();
        assertTrue(optionalList.isPresent());
    }

    public void testItemMethods() throws Exception {
        ShoppingList list = new ShoppingList("tesco");
        manager.save(list).get();

        for (int i = 0; i < 10; i++) {
            ShoppingItemBuilder builder = new ShoppingItemBuilder();
            builder.setName("item " + i);
            builder.setPrice(i * 2 + 1);
            builder.setList(list);
            ShoppingItem item = builder.createShoppingItem();
            manager.save(item);
        }

        DataModel<ShoppingItem> itemDataModel = manager.loadItems(list).get();

        for (ShoppingItem item : itemDataModel) {
            assertEquals(list, item.list);
            assertTrue("price more equal than 1 and less equal than 21", 1 <= item.price && item.price <= 21);
        }
        assertEquals(10, itemDataModel.size());

        ShoppingItem item = itemDataModel.getAtPosition(1).get();
        manager.remove(item).get();

        itemDataModel = manager.loadItems(list).get();
        assertEquals(9, itemDataModel.size());
        assertFalse("item should not be present after removal", itemDataModel.getAtId(item.getId()).isPresent());

        ShoppingItemBuilder builder = new ShoppingItemBuilder();
        builder.setList_id(1);
        builder.setName("kartofel");
        builder.setPrice(1.8);
        ShoppingItem item1 = builder.createShoppingItem();

        manager.save(item1).get();
        assertTrue("id is set and is greater equal to 0", item1.getId() >= 0);

        ShoppingList list2 = new ShoppingList("biedronka");
        manager.save(list2).get();

        for (int i = 0; i < 6; i++) {
            ShoppingItemBuilder builder2 = new ShoppingItemBuilder();
            builder2.setList(list2);
            builder2.setPrice(3.4);
            builder2.setName("kartofle");
            ShoppingItem item2 = builder2.createShoppingItem();
            manager.save(item2).get();
        }

        DataModel<ShoppingItem> items = manager.loadItems(list2).get();
        assertEquals(6, items.size());

        item = items.getAtPosition(0).get();
        assertEquals(3.4, item.price);
        assertEquals("kartofle", item.name);

        manager.remove(item);

        items = manager.loadItems(list2).get();
        assertEquals(5, items.size());
        assertExists(false, item, items);
    }

    protected void assertExists(boolean expected, ShoppingItem item, DataModel<ShoppingItem> items) {
        boolean exists = false;
        ShoppingItem found = null;
        for (ShoppingItem otherItem : items) {
            if (otherItem.equals(item)) {
                exists = true;
                found = otherItem;
            }
        }
        assertEquals("item: " + item + ", found: " + found + ", exists: " + exists, expected, exists);
    }

    public void testGetManager() throws Exception {
        assertTrue("manager exists", ContentManager.getExistingManager().isPresent());
    }
}
