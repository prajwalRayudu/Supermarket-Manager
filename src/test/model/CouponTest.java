package model;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CouponTest {
    @Test
    public void testConstructor() {
        Coupon testCoupon = new Coupon("TEST", 50);

        assertEquals("TEST", testCoupon.getCouponCode());
        assertEquals(50, testCoupon.getValue());
    }

    @Test
    public void testToJsonConverter() {
        Coupon c = new Coupon("Test", 50);

        JSONObject object = c.toJsonConverter();

        assertEquals("Test", object.getString("couponCode"));
        assertEquals(50, object.getInt("value"));
    }
}
