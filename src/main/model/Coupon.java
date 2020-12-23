package model;

import org.json.JSONObject;
import persistence.Converter;

// Coupon, consists of all the important elements of a Coupon, used by Receipt and superMarket class
public class Coupon implements Converter {
    // Fields
    private String couponCode;
    private int value;

    // Constructor
    // EFFECTS: Constructs a Coupon with the given couponCode and coupon value
    public Coupon(String couponCode, int value) {
        this.couponCode = couponCode;
        this.value = value;
    }

    // Methods
//
    // DOCUMENTATION
    // Used the toJson() method in Thingy class from the repository named JsonSerializationDemo to design this method
    // Repository link https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJsonConverter() {
        JSONObject object = new JSONObject();
        object.put("couponCode", couponCode);
        object.put("value", value);
        return object;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public int getValue() {
        return value;
    }
}
