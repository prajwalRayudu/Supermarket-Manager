package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Converter;

import java.util.ArrayList;
import java.util.List;

// Receipt, consists of the items bought and the coupons used, used by the supermarket class and the transaction
// record class
public class Receipt implements Converter {
    // Fields
    private List<Item> itemsBought;
    private List<Coupon> couponsUsed;

    // Constructor
    // EFFECTS: Constructs a receipt with an empty list of coupons and items bought
    public Receipt() {
        this.itemsBought = new ArrayList<>();
        this.couponsUsed = new ArrayList<>();
    }

    // Methods

    // DOCUMENTATION
    // Used the toJson() method in Thingy class from the repository named JsonSerializationDemo to design this method
    // Repository link https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJsonConverter() {
        JSONObject object = new JSONObject();
        object.put("itemsBought", convertItemsToJson());
        object.put("couponsUsed", convertCouponsToJson());
        return object;
    }
//
    // EFFECTS: converts the list of items to JSONObject and adds it to JSONArray, and returns JSONArray

    private JSONArray convertItemsToJson() {
        JSONArray itemsArray = new JSONArray();

        for (Item i : itemsBought) {
            itemsArray.put(i.toJsonConverter());
        }

        return itemsArray;
    }

    // EFFECTS: converts the list of coupons to JSONObject and adds it to JSONArray, and returns JSONArray
    private JSONArray convertCouponsToJson() {
        JSONArray couponsArray = new JSONArray();

        for (Coupon c : couponsUsed) {
            couponsArray.put(c.toJsonConverter());
        }

        return couponsArray;
    }

    // EFFECTS: searches for the item in the list and returns the item if found,
    //          else returns a new item with the name "not present" and other fields set to 0
    public Item giveItemFromReceipt(int code) {
        for (Item i : itemsBought) {
            if (i.getItemCode() == code) {
                return i;
            }
        }
        return new Item("not present", 0, 0, 0);
    }

    // MODIFIES: this
    // EFFECTS: if coupon with the given code is present in the list, then it removes it from the list and returns
    //          a coupon removed message, else returns a not present message
    public String removeCoupon(String code) {
        for (Coupon c : couponsUsed) {
            if (c.getCouponCode().equals(code)) {
                couponsUsed.remove(c);
                return code + " coupon removed";
            }
        }
        return "coupon not present";
    }

    public List<Item> getItemsBought() {
        return itemsBought;
    }

    public List<Coupon> getCouponsUsed() {
        return couponsUsed;
    }
}
