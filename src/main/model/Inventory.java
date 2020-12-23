package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Converter;

import java.util.ArrayList;

// Inventory, consists of important things needed for an inventory, used by the superMarket class
public class Inventory implements Converter {
    // Fields
    private String inventoryName;
    private ArrayList<Item> items;

    // Constructor
    // EFFECTS: Constructs an inventory with the given name and a empty list of items
    public Inventory(String inventoryName) {
        this.inventoryName = inventoryName;
        this.items = new ArrayList<>();
    }

    // Methods
//
    // DOCUMENTATION
    // Used the toJson() method in Thingy class from the repository named JsonSerializationDemo to design this method
    // Repository link https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJsonConverter() {
        JSONObject object = new JSONObject();
        object.put("inventoryName", inventoryName);
        object.put("items", convertItemsToJson());
        return object;
    }

    // EFFECTS: converts the list of items to JSONObject and adds it to JSONArray, and returns JSONArray
    private JSONArray convertItemsToJson() {
        JSONArray itemsArray = new JSONArray();

        for (Item i : items) {
            itemsArray.put(i.toJsonConverter());
        }

        return itemsArray;
    }

    // MODIFIES: this
    // EFFECTS: adds the given item to the list in the inventory
    public void addItems(Item item) {
        boolean itemClash = false;
        for (Item i : items) {
            if (i.getItemCode() == item.getItemCode()) {
                itemClash = true;
                break;
            }
        }

        if (!itemClash) {
            items.add(item);
        }
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
