package model;

import exceptions.StockCannotBeEditedException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ItemTest {
    // Fields
    private Item testItem;

    @BeforeEach
    public void setUp() {
        testItem = new Item("test", 123, 10, 50);
    }

    @Test
    public void testConstructor() {
        assertEquals("test", testItem.getName());
        assertEquals(123, testItem.getItemCode());
        assertEquals(10, testItem.getNumber());
        assertEquals(50, testItem.getPriceOfOne());

    }

    @Test
    public void testToJsonConverter() {
        JSONObject object = testItem.toJsonConverter();

        assertEquals("test", object.getString("name"));
        assertEquals(123, object.getInt("itemCode"));
        assertEquals(10, object.getInt("number"));
        assertEquals(50, object.getInt("priceOfOne"));
    }

    @Test
    public void testAddStockP() {
        try {
            testItem.addStock(5);
            assertEquals(15, testItem.getNumber());
            // pass this is expected
        } catch (StockCannotBeEditedException e) {
            fail("Should not be throwing an exception");
        }
    }

    @Test
    public void testAddStockNExceptionThrown() {
        try {
            testItem.addStock(-5);
            fail("Should not be coming here");
            assertEquals(10, testItem.getNumber());
        } catch (StockCannotBeEditedException e) {
            // exception excepted because negative stock was added
        }
    }

    @Test
    public void testRemoveStockP() {
        try {
            testItem.removeStock(5);
            assertEquals(5, testItem.getNumber());
            // expected to pass
        } catch (StockCannotBeEditedException e) {
            fail("should not be throwing an exception");
        }
    }

    @Test
    public void testRemoveStockPGre() {
        try {
            testItem.removeStock(15);
            fail("should be throwing an exception because more is removed");
            assertEquals(10, testItem.getNumber());
        } catch (StockCannotBeEditedException e) {
            // pass exception is exception because more than available is removed
        }
    }

    @Test
    public void testRemoveStockPEq() {
        try {
            testItem.removeStock(10);
            assertEquals(0, testItem.getNumber());
            // expected to pass
        } catch (StockCannotBeEditedException e) {
            fail("should not be throwing an exception");
        }
    }

    @Test
    public void testRemoveStockNExceptionCase1() {
        try {
            testItem.removeStock(-5);
            assertEquals(10, testItem.getNumber());
        } catch (StockCannotBeEditedException e) {
            // exception excepted because negative stock was removed
        }
    }

    @Test
    public void testRemoveStockNExceptionCase2() {
        try {
            testItem.removeStock(0);
            assertEquals(10, testItem.getNumber());
        } catch (StockCannotBeEditedException e) {
            // exception excepted because zero stock was removed
        }
    }

    @Test
    public void testSetName() {
        testItem.setName("Test2");

        assertEquals("Test2", testItem.getName());
    }

    @Test
    public void testSetItemCode() {
        testItem.setItemCode(5);

        assertEquals(5, testItem.getItemCode());
    }

    @Test
    public void testSetNumber() {
        testItem.setNumber(3);

        assertEquals(3, testItem.getNumber());
    }

    @Test
    public void testSetPriceOfOne() {
        testItem.setPriceOfOne(10);

        assertEquals(10, testItem.getPriceOfOne());
    }
}
