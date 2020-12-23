package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReceiptTest {
    private Receipt testReceipt;

    @BeforeEach
    public void setUp() {
        testReceipt = new Receipt();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testReceipt.getItemsBought().size());
        assertEquals(0, testReceipt.getCouponsUsed().size());
    }

    // TODO need to do this
    @Test
    public void testToJsonConverter() {
        testReceipt.getItemsBought().add(new Item("test1", 12, 10, 5));
        testReceipt.getItemsBought().add(new Item("test2", 19, 150, 12));
        testReceipt.getCouponsUsed().add(new Coupon("TEST1", 50));
        testReceipt.getCouponsUsed().add(new Coupon("TEST2", 50));

        JSONObject object = testReceipt.toJsonConverter();

        assertTrue(checkReceipt(object));
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
    public void testGiveItemFromReceiptP() {
        testReceipt.getItemsBought().add(new Item("test2", 13, 10, 5));
        testReceipt.getItemsBought().add(new Item("test", 12, 10, 5));

        Item itemReceived = testReceipt.giveItemFromReceipt(12);
        assertEquals("test", itemReceived.getName());
        assertEquals(12, itemReceived.getItemCode());
        assertEquals(10, itemReceived.getNumber());
        assertEquals(5, itemReceived.getPriceOfOne());
    }

    @Test
    public void testGiveItemFromReceiptNP() {
        Item itemReceived = testReceipt.giveItemFromReceipt(12);
        assertEquals("not present", itemReceived.getName());
        assertEquals(0, itemReceived.getItemCode());
        assertEquals(0, itemReceived.getNumber());
        assertEquals(0, itemReceived.getPriceOfOne());
    }

    @Test
    public void testRemoveCouponP() {
        testReceipt.getCouponsUsed().add(new Coupon("TEST", 50));

        assertEquals("TEST coupon removed", testReceipt.removeCoupon("TEST"));
        assertEquals(0, testReceipt.getCouponsUsed().size());
    }

    @Test
    public void testRemoveCouponNP() {
        testReceipt.getCouponsUsed().add(new Coupon("TEST", 50));

        assertEquals("coupon not present", testReceipt.removeCoupon("TES"));
        assertEquals(1, testReceipt.getCouponsUsed().size());
    }
}
