package model;

import exceptions.StockCannotBeEditedException;
import org.json.JSONObject;
import persistence.Converter;

// Item, consists of all the important elements of an Item, used by Receipt and the Inventory class
public class Item implements Converter {
    // Fields
    private String name;
    private int itemCode;
    private int number;
    private int priceOfOne;

    // Constructor
    // EFFECTS: Constructs an item with the given name, itemCode, number
    public Item(String name, int itemCode, int number, int priceOfOne) {
        this.name = name;
        this.itemCode = itemCode;
        this.number = number;
        this.priceOfOne = priceOfOne;
    }

    // Methods
//
    // DOCUMENTATION
    // Used the toJson() method in Thingy class from the repository named JsonSerializationDemo to design this method
    // Repository link https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJsonConverter() {
        JSONObject object = new JSONObject();
        object.put("name", name);
        object.put("itemCode", itemCode);
        object.put("number", number);
        object.put("priceOfOne", priceOfOne);
        return object;
    }

    // MODIFIES: this
    // EFFECTS: increases the number of items by the number given,
    //          throws a StockCannotBeEditedException when a num less than or equal to zero is given
    public void addStock(int num) throws StockCannotBeEditedException {
        if (num > 0) {
            number += num;
        } else {
            throw new StockCannotBeEditedException("Minimum stock should be more than 0");
        }
    }

    // MODIFIES: this
    // EFFECTS: decreases the number of items present by the number given,
    //          throws a StockCannotBeEditedException when a num less than or equal to zero is given
    public void removeStock(int num) throws StockCannotBeEditedException {
        if ((num > 0) && (num <= number)) {
            number -= num;
        } else {
            throw new StockCannotBeEditedException("Stock could not be removed");
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPriceOfOne(int priceOfOne) {
        this.priceOfOne = priceOfOne;
    }

    public String getName() {
        return name;
    }

    public int getItemCode() {
        return itemCode;
    }

    public int getNumber() {
        return number;
    }

    public int getPriceOfOne() {
        return priceOfOne;
    }
}
