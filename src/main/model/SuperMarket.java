package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Converter;

import java.util.ArrayList;
import java.util.List;

// superMarket, combines all the other elements od the model package to make a superMarket
public class SuperMarket implements Converter {
    // Fields
    private String superMarketName;
    private List<Employee> employeeList;
    private List<TransactionRecord> transactionsList;
    private List<Inventory> inventoriesList;
    private List<Coupon> couponsList;
    private List<Employee> oldEmployees;
    public Receipt commonReceipt = new Receipt();
    private int access;
    private String employeeLoggedIn;
    private boolean billingInProcess = false;
    private int numLogins = 0;

    // Constructor
    // MODIFIES: this
    // EFFECTS: constructs the supermarket with empty
    //          lists and adds an admin to the employee
    //          list for initial login
    public SuperMarket(String superMarketName) {
        this.superMarketName = superMarketName;
        this.employeeList = new ArrayList<>();
        this.transactionsList = new ArrayList<>();
        this.inventoriesList = new ArrayList<>();
        this.couponsList = new ArrayList<>();
        this.oldEmployees = new ArrayList<>();

        employeeList.add(new Employee("admin", "admin", 5, "admin"));
        addDepartments();

    }

    // Methods

    // JSON Methods

    // DOCUMENTATION
    // Used the toJson() method in Thingy class from the repository named JsonSerializationDemo to design this method
    // Repository link https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJsonConverter() {
        JSONObject object = new JSONObject();
        object.put("superMarketName", superMarketName);
        object.put("employeeList", convertEmployeesToJson());
        object.put("oldEmployees", convertOldEmployeesToJson());
        object.put("transactionsList", convertTransactionsToJson());
        object.put("inventoriesList", convertInventoriesToJson());
        object.put("couponsList", convertCouponsToJson());
        object.put("commonReceipt", convertCommonReceipt());
        object.put("access", access);
        object.put("billingInProcess", billingInProcess);
        object.put("numLogins", numLogins);
        return object;
    }

    // EFFECTS: converts all the employees in the list to JSONObject and adds them to JSONArray and returns it
    private JSONArray convertEmployeesToJson() {
        JSONArray employeeArray = new JSONArray();

        for (Employee e : employeeList) {
            employeeArray.put(e.toJsonConverter());
        }

        return employeeArray;
    }

    // EFFECTS: converts all the old employees in the list to JSONObject and adds them to JSONArray and returns it
    private JSONArray convertOldEmployeesToJson() {
        JSONArray employeeArray = new JSONArray();

        for (Employee e : oldEmployees) {
            employeeArray.put(e.toJsonConverter());
        }

        return employeeArray;
    }

    // EFFECTS: converts all the transaction records in the list to JSONObject and adds them
    //          to JSONArray and returns it
    private JSONArray convertTransactionsToJson() {
        JSONArray transactionsArray = new JSONArray();

        for (TransactionRecord t : transactionsList) {
            transactionsArray.put(t.toJsonConverter());
        }

        return transactionsArray;
    }

    // EFFECTS: converts all the Inentories in the list to JSONObject and adds them to JSONArray and returns it
    private JSONArray convertInventoriesToJson() {
        JSONArray inventoriesArray = new JSONArray();

        for (Inventory i : inventoriesList) {
            inventoriesArray.put(i.toJsonConverter());
        }

        return inventoriesArray;
    }

    // EFFECTS: converts all the coupons in the list to JSONObject and adds them to JSONArray and returns it
    private JSONArray convertCouponsToJson() {
        JSONArray couponsArray = new JSONArray();

        for (Coupon c : couponsList) {
            couponsArray.put(c.toJsonConverter());
        }

        return couponsArray;
    }

    // EFFECTS: converts all the commonReceipt to JSONObject returns it
    private JSONObject convertCommonReceipt() {
        return commonReceipt.toJsonConverter();
    }

    // METHODS BASED ON ITEM AND INVENTORY

    // MODIFIES: this
    // EFFECTS: adds the default inventories to the list
    private void addDepartments() {
        Inventory baking = new Inventory("Baking Items");
        Inventory meat = new Inventory("Meat");
        Inventory healthAndBeauty = new Inventory("Health and Beauty");
        Inventory frozen = new Inventory("Frozen Food");
        Inventory snacks = new Inventory("Snacks");
        Inventory dairyProducts = new Inventory("Dairy Products");
        Inventory alcohol = new Inventory("Alcohol");
        Inventory vegetables = new Inventory("Vegetables");
        inventoriesList.add(baking);
        inventoriesList.add(meat);
        inventoriesList.add(healthAndBeauty);
        inventoriesList.add(frozen);
        inventoriesList.add(snacks);
        inventoriesList.add(dairyProducts);
        inventoriesList.add(alcohol);
        inventoriesList.add(vegetables);
    }


    // EFFECTS: returns the item with the given code,
    //          else returns an Item with the name "not present"

    public Item giveItemByCode(int code) {
        for (Inventory i : inventoriesList) {
            for (Item j : i.getItems()) {
                if (j.getItemCode() == code) {
                    return j;
                }
            }
        }

        return new Item("not present", 0, 0, 0);
    }


    // EFFECTS: returns the inventory in the list with the given name
    //          if present, else returns an inventory with name "not present"
    public Inventory getNeededInventory(String name) {
        for (Inventory i : inventoriesList) {
            if (i.getInventoryName().equals(name)) {
                return i;
            }
        }

        return new Inventory("not present");
    }

    // EFFECTS: returns the item with the given code in the inventory present,
    //          if not found returns an Item with the name "not present"
    public Item findItemByCode(int code, Inventory inventory) {
        for (Item i : inventory.getItems()) {
            if (i.getItemCode() == code) {
                return i;
            }
        }

        return new Item("not present", 0, 0, 0);
    }


    // METHODS BASED ON EMPLOYEE

    // EFFECTS: searches for the employee with the given name and returns the employee if found, else returns null
    public Employee searchEmployee(String name) {
        for (Employee e : employeeList) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS : returns true if the employee with the given name is successfully found and the
    //           employee's access and designation are changed to the given values, else returns false
    public boolean setAccessLevelOfEmployee(String name, int chosenAccess, String newDesignation) {
        Employee returned = searchEmployee(name);

        if (returned != null) {
            if (access == 5) {
                returned.setAccessLevel(chosenAccess);
                returned.setDesignation(newDesignation);
                return true;
            }
        }
        return false;
    }


    // EFFECTS: check if the name and the password of the employee match
    //          to any employee present in the list. Also sets the access
    //          to the level of the employee it the credentials match.
    public boolean verifyEmployee(String name, String password) {
        for (Employee i : employeeList) {
            if ((i.getName().equals(name)) && (i.getPassword().equals(password))) {
                access = i.getAccessLevel();
                return true;
            }
        }

        return false;
    }


    // MODIFIES: this
    // EFFECTS: searches for the employee with the given name and removes it form the list if the employee was found
    public void removeEmployee(String name) {
        int size = employeeList.size();
        Employee removed = null;

        for (Employee e : employeeList) {
            if (e.getName().equals(name)) {
                removed = e;
            }
        }

        employeeList.removeIf(e -> e.getName().equals(name));

        if (employeeList.size() < size) {
            oldEmployees.add(removed);
        }
    }

    // EFFECTS: returns a list of employees with access level below the given integer
    public List<Employee> getEmployeesBelowAccess(int a) {
        List<Employee> accessibleE = new ArrayList<>();

        for (Employee e : employeeList) {
            if (e.getAccessLevel() < a) {
                accessibleE.add(e);
            }
        }
        return accessibleE;
    }
    // METHODS BASED ON COUPONS

    // EFFECTS: returns the coupon with the given code if found in the list, else returns null
    public Coupon giveCoupon(String code) {
        for (Coupon c : couponsList) {
            if (c.getCouponCode().equals(code)) {
                return c;
            }
        }
        return null;
    }

    // METHODS BASED ON RECORDS


    // EFFECTS: returns a list of records with the cashier name as the given name,
    //          if the employee with the given name does not exist, then returns null
    public List<TransactionRecord> getCashierRecords(String name) {
        if (searchEmployee(name) == null) {
            return null;
        }

        List<TransactionRecord> records = new ArrayList<>();

        for (TransactionRecord r : transactionsList) {
            if (r.getCashierName().equals(name)) {
                records.add(r);
            }
        }
        return records;
    }


    // GETTERS AND SETTERS

    public void setBillingInProcess(boolean billingInProcess) {
        this.billingInProcess = billingInProcess;
    }


    public void setEmployeeLoggedIn(String employeeLoggedIn) {
        this.employeeLoggedIn = employeeLoggedIn;
    }


    public void setAccess(int access) {
        this.access = access;
    }


    public List<Employee> getEmployeeList() {
        return employeeList;
    }


    public List<TransactionRecord> getTransactionsList() {
        return transactionsList;
    }


    public List<Inventory> getInventoriesList() {
        return inventoriesList;
    }

    public List<Employee> getOldEmployees() {
        return oldEmployees;
    }

    public int getAccess() {
        return access;
    }


    public String getEmployeeLoggedIn() {
        return employeeLoggedIn;
    }

    public boolean isBillingInProcess() {
        return billingInProcess;
    }

    public List<Coupon> getCouponsList() {
        return couponsList;
    }

    public void setNumLogins(int numLogins) {
        this.numLogins = numLogins;
    }

    public int getNumLogins() {
        return numLogins;
    }

    public String getSuperMarketName() {
        return superMarketName;
    }
}
