package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventoryTest {
    // Fields
    private Inventory testInventory;
    private Item item = new Item("testItem", 122, 30, 50);

    @BeforeEach
    public void setUp() {
        testInventory = new Inventory("test");
    }

    @Test
    public void testConstructor() {
        assertEquals("test", testInventory.getInventoryName());
        assertEquals(0, testInventory.getItems().size());
    }

    @Test
    public void testToJsonConverter() {
        Item item1 = new Item("test", 12, 10, 11);
        testInventory.getItems().add(item);
        testInventory.getItems().add(item1);
        JSONObject object = testInventory.toJsonConverter();

        assertEquals("test", object.getString("inventoryName"));
        assertTrue(checkItem(item, (JSONObject) object.getJSONArray("items").get(0)));
        assertTrue(checkItem(item1, (JSONObject) object.getJSONArray("items").get(1)));
    }

    private boolean checkItem(Item i, JSONObject object) {
        boolean name = i.getName().equals(object.getString("name"));
        boolean itemCode = (i.getItemCode() == object.getInt("itemCode"));
        boolean number = (i.getNumber() == object.getInt("number"));
        boolean priceOfOne = (i.getPriceOfOne() == object.getInt("priceOfOne"));

        return (name && itemCode && number && priceOfOne);
    }

    @Test
    public void testAddItemsCan() {
        testInventory.getItems().add(item);
        testInventory.addItems(new Item(item.getName(), 123, item.getNumber(), item.getPriceOfOne()));

        assertEquals(2, testInventory.getItems().size());
    }

    @Test
    public void testAddItemsCannot() {
        testInventory.getItems().add(item);
        testInventory.addItems(item);
        assertEquals(1, testInventory.getItems().size());
    }
}
