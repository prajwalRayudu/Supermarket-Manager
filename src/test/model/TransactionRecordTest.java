package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionRecordTest {
    // Fields
    private TransactionRecord testRecord;
    private Receipt testReceipt;

    @BeforeEach
    public void setUp() {
        testReceipt = new Receipt();
        testRecord = new TransactionRecord(testReceipt, "test");
    }

    @Test
    public void testConstructorListE() {
        testRecord = new TransactionRecord(new Receipt(), "test");
        assertEquals(0, testRecord.getReceipt().getItemsBought().size());
        assertEquals("test", testRecord.getCashierName());
    }

    @Test
    public void testToJsonConverter() {
        testReceipt.getItemsBought().add(new Item("test1", 12, 10, 5));
        testReceipt.getItemsBought().add(new Item("test2", 19, 150, 12));
        testReceipt.getCouponsUsed().add(new Coupon("TEST1", 50));
        testReceipt.getCouponsUsed().add(new Coupon("TEST2", 50));

        JSONObject object = testRecord.toJsonConverter();

        assertEquals("test", object.getString("cashierName"));
        assertTrue(checkReceipt((JSONObject) object.get("receipt")));
    }

    private boolean checkReceipt(JSONObject object) {
        JSONArray itemsArray = object.getJSONArray("itemsBought");
        JSONArray couponsArray = object.getJSONArray("couponsUsed");

        JSONObject item1 = itemsArray.getJSONObject(0);
        JSONObject item2 = itemsArray.getJSONObject(1);
        JSONObject coupon1 = couponsArray.getJSONObject(0);
        JSONObject coupon2 = couponsArray.getJSONObject(1);

        boolean nameItem1 = item1.getString("name").equals("test1");
        boolean itemCodeItem1 = (12 == item1.getInt("itemCode"));
        boolean numberItem1 = (10 == item1.getInt("number"));
        boolean priceOfOneItem1 = (5 == item1.getInt("priceOfOne"));

        boolean item1R = (nameItem1 && itemCodeItem1 && numberItem1 && priceOfOneItem1);


        boolean nameItem2 = item2.getString("name").equals("test2");
        boolean itemCodeItem2 = (19 == item2.getInt("itemCode"));
        boolean numberItem2 = (150 == item2.getInt("number"));
        boolean priceOfOneItem2 = (12 == item2.getInt("priceOfOne"));

        boolean item2R = (nameItem2 && itemCodeItem2 && numberItem2 && priceOfOneItem2);

        boolean codeCoupon1 = coupon1.getString("couponCode").equals("TEST1");
        boolean valueCoupon1 = (50 == coupon1.getInt("value"));
        boolean coupon1R = (codeCoupon1 && valueCoupon1);

        boolean codeCoupon2 = coupon2.getString("couponCode").equals("TEST2");
        boolean valueCoupon2 = (50 == coupon2.getInt("value"));
        boolean coupon2R = (codeCoupon2 && valueCoupon2);

        return (item1R && item2R && coupon1R && coupon2R);
    }

    @Test
    public void testConstructorListO() {
        testReceipt = new Receipt();
        testReceipt.getItemsBought().add(new Item("testItem 1", 123, 10, 50));
        testReceipt.getItemsBought().add(new Item("testItem 2", 124, 20, 55));

        testRecord = new TransactionRecord(testReceipt, "test");

        assertEquals(2, testRecord.getReceipt().getItemsBought().size());
        assertEquals("test", testRecord.getCashierName());
    }
}
