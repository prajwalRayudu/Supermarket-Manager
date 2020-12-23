package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// DOCUMENTATION
// Used the JsonReader class from the repository named JsonSerializationDemo to design this class
// Repository link https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// JSON Reader, converts the supermatket in Json format in the file to type superMarket
public class JsonReader {
    // Fields
    private String fileLocation;

    // Constructor
    // EFFECTS: Constructs a JsonReader with the given file location
    public JsonReader(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    // Methods
    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public SuperMarket read() throws IOException {
        String jsonData = readFile(fileLocation);
        JSONObject jsonObject = new JSONObject(jsonData);
        return convertToSuperMarket(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: converts the given JSONObject into a supermarket object, and returns the supermarket
    private SuperMarket convertToSuperMarket(JSONObject object) {
        SuperMarket market = new SuperMarket(object.getString("superMarketName"));
        addEmployees(market, object);
        addOldEmployees(market, object);
        addTransactions(market, object);
        addInventories(market, object);
        addCoupons(market, object);
        loadCommonReceipt(market, object);
        loadAccess(market, object);
        loadNumLogins(market, object);
        loadBillingInProcess(market, object);

        return market;
    }

    // EFFECTS: clears out the initial employees in the supermarket object and add the employees form the file
    private void addEmployees(SuperMarket market, JSONObject object) {
        market.getEmployeeList().clear();
        JSONArray employeeArray = object.getJSONArray("employeeList");

        for (Object o : employeeArray) {
            market.getEmployeeList().add(addEmployee((JSONObject) o));
        }

    }

    // EFFECTS: clears out the old employees in the supermarket object and add the old employees form the file
    private void addOldEmployees(SuperMarket market, JSONObject object) {
        market.getOldEmployees().clear();
        JSONArray employeeArray = object.getJSONArray("oldEmployees");

        for (Object o : employeeArray) {
            market.getOldEmployees().add(addEmployee((JSONObject) o));
        }

    }

    // EFFECTS: converts the transactions in JSONObject to Transactionrecords object and adds it
    //          to the transactions list in supermarket
    private void addTransactions(SuperMarket market, JSONObject object) {
        JSONArray transactionArray = object.getJSONArray("transactionsList");

        for (Object o : transactionArray) {
            addTransaction(market, (JSONObject) o);
        }
    }
//
    // EFFECTS: adds each Inventory to the supermarket by converting the inventory and items from JSONObject
    //          to their respective object types

    private void addInventories(SuperMarket market, JSONObject object) {
        market.getInventoriesList().clear();
        JSONArray inventoryArray = object.getJSONArray("inventoriesList");

        for (Object o : inventoryArray) {
            JSONObject inventory = (JSONObject) o;

            Inventory i = new Inventory(inventory.getString("inventoryName"));

            JSONArray itemsArray = inventory.getJSONArray("items");

            for (Object x : itemsArray) {
                i.getItems().add(addItem((JSONObject) x));
            }

            market.getInventoriesList().add(i);
        }
    }

    // EFFECTS: converts the coupons in JSONObject to coupon object and adds it
    //          to the coupons list in supermarket
    private void addCoupons(SuperMarket market, JSONObject object) {
        JSONArray couponsArray = object.getJSONArray("couponsList");

        for (Object o : couponsArray) {
            market.getCouponsList().add(addCoupon((JSONObject) o));
        }
    }

    // EFFECTS: converts the commonReceipt in JSONObject to receipt object and adds it
    //          to the commonReceipt field in supermarket

    private void loadCommonReceipt(SuperMarket market, JSONObject object) {
        JSONObject receipt = object.getJSONObject("commonReceipt");

        market.commonReceipt = addReceipt(receipt);
    }

    // EFFECTS: gets the access from the object and sets the access of the supermarket
    private void loadAccess(SuperMarket market, JSONObject object) {
        market.setAccess(object.getInt("access"));
    }

    // EFFECTS: gets the num logins from the object and sets the num logins of the supermarket
    private void loadNumLogins(SuperMarket market, JSONObject object) {
        market.setNumLogins(object.getInt("numLogins"));
    }

    // EFFECTS: gets the billingInProcess from the object and sets the billingInProcess of the supermarket
    private void loadBillingInProcess(SuperMarket market, JSONObject object) {
        market.setBillingInProcess(object.getBoolean("billingInProcess"));
    }

    // EFFECTS: converts the object to transaction record object
    private void addTransaction(SuperMarket market, JSONObject object) {
        String cashierName = object.getString("cashierName");
        Receipt receipt = addReceipt((JSONObject) object.get("receipt"));
        TransactionRecord trans = new TransactionRecord(receipt, cashierName);
        market.getTransactionsList().add(trans);
    }

    // EFFECTS: converts the object to receipt object
    private Receipt addReceipt(JSONObject object) {
        JSONArray itemsArray = object.getJSONArray("itemsBought");
        JSONArray couponsArray = object.getJSONArray("couponsUsed");

        Receipt r = new Receipt();

        for (Object o : itemsArray) {
            r.getItemsBought().add(addItem((JSONObject) o));
        }

        for (Object o : couponsArray) {
            r.getCouponsUsed().add(addCoupon((JSONObject) o));
        }

        return r;
    }

    // EFFECTS: converts the object to item object
    private Item addItem(JSONObject object) {
        String name = object.getString("name");
        int code = object.getInt("itemCode");
        int num = object.getInt("number");
        int price = object.getInt("priceOfOne");

        return new Item(name, code, num, price);
    }

    // EFFECTS: converts the object to coupon object
    private Coupon addCoupon(JSONObject object) {
        Coupon coupon = new Coupon(object.getString("couponCode"), object.getInt("value"));

        return coupon;
    }

    // EFFECTS: converts the object to employee object
    private Employee addEmployee(JSONObject object) {
        String name = object.getString("name");
        String designation = object.getString("designation");
        int accessLevel = object.getInt("accessLevel");
        String password = object.getString("password");
        Employee e = new Employee(name, designation, accessLevel, password);

        return e;
    }
}
