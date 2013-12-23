package net.sledzdev.shoppinglist.loader;

import android.content.ContentResolver;
import android.content.Context;
import android.test.ProviderTestCase2;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.ListenableFuture;

import net.sledzdev.shoppinglist.adapter.DataModel;
import net.sledzdev.shoppinglist.model.ShoppingList;
import net.sledzdev.shoppinglist.content.ShoppingListProvider;
import net.sledzdev.shoppinglist.content.ShoppingProviderContract;

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
        manager = new ContentManager(getContext()) {
            @Override
            protected ContentResolver initResolver(Context context) {
                return mockContentResolver;
            }
        };
    }

    public void testContentManagerShoppingListsMethods() throws Exception {
        for (int i = 0; i < 10; i++) {
            manager.save(new ShoppingList("list number " + i));
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
        manager.save(list);

        ListenableFuture<Optional<ShoppingList>> futureOptionalList = manager.getList(list.getId() + 20);
        Optional<ShoppingList> optionalList = futureOptionalList.get();

        assertFalse(optionalList.isPresent());

        optionalList = manager.getList(list.getId()).get();
        assertTrue(optionalList.isPresent());
    }
}
