package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SuperMarketTest {
    // Fields
    private SuperMarket testSuperMarket;

    @BeforeEach
    public void setUp() {
        testSuperMarket = new SuperMarket("Test Market");
    }

    // Tests for Constructor

    @Test
    public void testConstructor() {
        assertEquals("Test Market", testSuperMarket.getSuperMarketName());
        assertEquals(1, testSuperMarket.getEmployeeList().size());
        assertEquals(0, testSuperMarket.getTransactionsList().size());
        assertEquals(8, testSuperMarket.getInventoriesList().size());

    }

    @Test
    public void testToJsonConverter() {
        // Things need to add to test
        Employee e1 = new Employee("test1", "Cashier", 2, "test");
        Employee e2 = new Employee("test2", "Stocking Staff", 1, "test");
        Coupon c1 = new Coupon("TEST1", 50);
        Coupon c2 = new Coupon("TEST2", 50);
        TransactionRecord t1 = new TransactionRecord(new Receipt(), "test1");
        TransactionRecord t2 = new TransactionRecord(new Receipt(), "test2");
        Item i1 = new Item("Eggs", 12, 10, 11);
        Item i2 = new Item("Beef", 13, 10, 11);
        Item i3 = new Item("Chicken", 14, 10, 11);
        Employee e3 = new Employee("test3", "Cashier", 2, "test");
        Employee e4 = new Employee("test4", "Stocking Staff", 1, "test");
        Item i4 = new Item("Beef", 12, 5, 11);
        Item i5 = new Item("Chicken", 12, 5, 11);
        Coupon c3 = new Coupon("TEST5", 50);
        Coupon c4 = new Coupon("TEST6", 50);


        testSuperMarket.setNumLogins(2);
        testSuperMarket.getEmployeeList().add(e1);
        testSuperMarket.getEmployeeList().add(e2);
        testSuperMarket.getCouponsList().add(c1);
        testSuperMarket.getCouponsList().add(c2);
        testSuperMarket.getTransactionsList().add(t1);
        testSuperMarket.getTransactionsList().add(t2);
        testSuperMarket.getInventoriesList().get(1).addItems(i2);
        testSuperMarket.getInventoriesList().get(1).addItems(i3);
        testSuperMarket.getInventoriesList().get(5).addItems(i1);
        t1.getReceipt().getItemsBought().add(i1);
        t1.getReceipt().getItemsBought().add(i2);
        t1.getReceipt().getCouponsUsed().add(new Coupon("TEST3", 50));
        t1.getReceipt().getCouponsUsed().add(new Coupon("TEST4", 50));
        testSuperMarket.getOldEmployees().add(e3);
        testSuperMarket.getOldEmployees().add(e4);
        testSuperMarket.commonReceipt.getItemsBought().add(i4);
        testSuperMarket.commonReceipt.getItemsBought().add(i5);
        testSuperMarket.commonReceipt.getCouponsUsed().add(c3);
        testSuperMarket.commonReceipt.getCouponsUsed().add(c4);

        JSONObject object = testSuperMarket.toJsonConverter();

        assertEquals("Test Market", object.getString("superMarketName"));
        assertEquals(2, object.getInt("numLogins"));
        assertEquals(0, object.getInt("access"));
        assertTrue(checkEmployees(object.getJSONArray("employeeList")));
        assertTrue(checkInventories(object.getJSONArray("inventoriesList")));
        assertTrue(checkTransactions(object.getJSONArray("transactionsList")));
        assertTrue(checkCoupons(object.getJSONArray("couponsList")));

        assertTrue(checkOldEmployees(object.getJSONArray("oldEmployees")));
        assertTrue(checkCommonReceipt(object.getJSONObject("commonReceipt")));
        assertFalse(object.getBoolean("billingInProcess"));
    }

    private boolean checkOldEmployees(JSONArray array) {
        JSONObject e1 = array.getJSONObject(0);
        JSONObject e2 = array.getJSONObject(1);

        // e1
        assertEquals("test3", e1.getString("name"));
        assertEquals("Cashier", e1.getString("designation"));
        assertEquals(2, e1.getInt("accessLevel"));
        assertEquals("test", e1.getString("password"));

        // e2
        assertEquals("test4", e2.getString("name"));
        assertEquals("Stocking Staff", e2.getString("designation"));
        assertEquals(1, e2.getInt("accessLevel"));
        assertEquals("test", e2.getString("password"));

        return true;
    }

    private boolean checkCommonReceipt(JSONObject object) {
        JSONArray array = object.getJSONArray("itemsBought");
        JSONObject object1 = array.getJSONObject(0);
        JSONObject object2 = array.getJSONObject(1);


        assertEquals("Beef", object1.getString("name"));
        assertEquals(12, object1.getInt("itemCode"));
        assertEquals(5, object1.getInt("number"));
        assertEquals(11, object1.getInt("priceOfOne"));

        assertEquals("Chicken", object2.getString("name"));
        assertEquals(12, object2.getInt("itemCode"));
        assertEquals(5, object2.getInt("number"));
        assertEquals(11, object2.getInt("priceOfOne"));

        JSONArray array1 = object.getJSONArray("couponsUsed");
        JSONObject object3 = array1.getJSONObject(0);
        JSONObject object4 = array1.getJSONObject(1);

        assertEquals("TEST5", object3.getString("couponCode"));
        assertEquals(50, object3.getInt("value"));

        assertEquals("TEST6", object4.getString("couponCode"));
        assertEquals(50, object4.getInt("value"));

        return true;
    }

    private boolean checkEmployees(JSONArray array) {
        JSONObject e1 = array.getJSONObject(0);
        JSONObject e2 = array.getJSONObject(1);
        JSONObject e3 = array.getJSONObject(2);

        // e1
        assertEquals("admin", e1.getString("name"));
        assertEquals("admin", e1.getString("designation"));
        assertEquals(5, e1.getInt("accessLevel"));
        assertEquals("admin", e1.getString("password"));

        // e2
        assertEquals("test1", e2.getString("name"));
        assertEquals("Cashier", e2.getString("designation"));
        assertEquals(2, e2.getInt("accessLevel"));
        assertEquals("test", e2.getString("password"));

        // e3
        assertEquals("test2", e3.getString("name"));
        assertEquals("Stocking Staff", e3.getString("designation"));
        assertEquals(1, e3.getInt("accessLevel"));
        assertEquals("test", e3.getString("password"));

        return true;
    }

    private boolean checkInventories(JSONArray array) {
        JSONObject object1 = array.getJSONObject(0);
        JSONObject object2 = array.getJSONObject(1);
        JSONObject object3 = array.getJSONObject(2);
        JSONObject object4 = array.getJSONObject(3);
        JSONObject object5 = array.getJSONObject(4);
        JSONObject object6 = array.getJSONObject(5);
        JSONObject object7 = array.getJSONObject(6);
        JSONObject object8 = array.getJSONObject(7);

        assertEquals("Baking Items", object1.getString("inventoryName"));
        assertEquals("Meat", object2.getString("inventoryName"));
        assertEquals("Health and Beauty", object3.getString("inventoryName"));
        assertEquals("Frozen Food", object4.getString("inventoryName"));
        assertEquals("Snacks", object5.getString("inventoryName"));
        assertEquals("Dairy Products", object6.getString("inventoryName"));
        assertEquals("Alcohol", object7.getString("inventoryName"));
        assertEquals("Vegetables", object8.getString("inventoryName"));

        assertTrue(object1.getJSONArray("items").isEmpty());
        assertFalse(object2.getJSONArray("items").isEmpty());
        assertTrue(object3.getJSONArray("items").isEmpty());
        assertTrue(object4.getJSONArray("items").isEmpty());
        assertTrue(object5.getJSONArray("items").isEmpty());
        assertFalse(object6.getJSONArray("items").isEmpty());
        assertTrue(object7.getJSONArray("items").isEmpty());
        assertTrue(object8.getJSONArray("items").isEmpty());

        JSONObject item1I2 = object2.getJSONArray("items").getJSONObject(0);
        JSONObject item2I2 = object2.getJSONArray("items").getJSONObject(1);
        JSONObject item1I6 = object6.getJSONArray("items").getJSONObject(0);


        assertEquals("Beef", item1I2.getString("name"));
        assertEquals(13, item1I2.getInt("itemCode"));
        assertEquals(10, item1I2.getInt("number"));
        assertEquals(11, item1I2.getInt("priceOfOne"));

        assertEquals("Chicken", item2I2.getString("name"));
        assertEquals(14, item2I2.getInt("itemCode"));
        assertEquals(10, item2I2.getInt("number"));
        assertEquals(11, item2I2.getInt("priceOfOne"));

        assertEquals("Eggs", item1I6.getString("name"));
        assertEquals(12, item1I6.getInt("itemCode"));
        assertEquals(10, item1I6.getInt("number"));
        assertEquals(11, item1I6.getInt("priceOfOne"));
        return true;
    }

    private boolean checkTransactions(JSONArray array) {
        JSONObject object1 = array.getJSONObject(0);
        JSONObject object2 = array.getJSONObject(1);

        boolean name1 = object1.getString("cashierName").equals("test1");
        boolean receipt1 = checkReceipt(object1.getJSONObject("receipt"));

        boolean name2 = object2.getString("cashierName").equals("test2");
        boolean iList2 = object2.getJSONObject("receipt").getJSONArray("itemsBought").isEmpty();
        boolean cList2 = object2.getJSONObject("receipt").getJSONArray("couponsUsed").isEmpty();
        boolean receipt2 = (cList2 && iList2);

        return (name1 && receipt1 && name2 && receipt2);
    }

    private boolean checkCoupons(JSONArray array) {
        JSONObject object1 = array.getJSONObject(0);
        JSONObject object2 = array.getJSONObject(1);

        boolean code1 = object1.getString("couponCode").equals("TEST1");
        boolean value1 = (object1.getInt("value") == 50);

        boolean code2 = object2.getString("couponCode").equals("TEST2");
        boolean value2 = (object2.getInt("value") == 50);

        return (code1 && value1 && code2 && value2);
    }

    private boolean checkReceipt(JSONObject object) {
        JSONArray itemsArray = object.getJSONArray("itemsBought");
        JSONArray couponsArray = object.getJSONArray("couponsUsed");

        JSONObject item1 = itemsArray.getJSONObject(0);
        JSONObject item2 = itemsArray.getJSONObject(1);
        JSONObject coupon1 = couponsArray.getJSONObject(0);
        JSONObject coupon2 = couponsArray.getJSONObject(1);

        boolean nameItem1 = item1.getString("name").equals("Eggs");
        boolean itemCodeItem1 = (12 == item1.getInt("itemCode"));
        boolean numberItem1 = (10 == item1.getInt("number"));
        boolean priceOfOneItem1 = (11 == item1.getInt("priceOfOne"));

        boolean item1R = (nameItem1 && itemCodeItem1 && numberItem1 && priceOfOneItem1);


        boolean nameItem2 = item2.getString("name").equals("Beef");
        boolean itemCodeItem2 = (13 == item2.getInt("itemCode"));
        boolean numberItem2 = (10 == item2.getInt("number"));
        boolean priceOfOneItem2 = (11 == item2.getInt("priceOfOne"));

        boolean item2R = (nameItem2 && itemCodeItem2 && numberItem2 && priceOfOneItem2);

        boolean codeCoupon1 = coupon1.getString("couponCode").equals("TEST3");
        boolean valueCoupon1 = (50 == coupon1.getInt("value"));
        boolean coupon1R = (codeCoupon1 && valueCoupon1);

        boolean codeCoupon2 = coupon2.getString("couponCode").equals("TEST4");
        boolean valueCoupon2 = (50 == coupon2.getInt("value"));
        boolean coupon2R = (codeCoupon2 && valueCoupon2);

        return (item1R && item2R && coupon1R && coupon2R);
    }

    // Tests for addDepartments

    @Test
    public void testAddDepartments() {
        assertEquals(8, testSuperMarket.getInventoriesList().size());
    }

    // Tests for verifyEmployee

    @Test
    public void testVerifyEmployeeNotP() {
        Employee testE = new Employee("testE", "Supervisor", 3, "test");
        testSuperMarket.getEmployeeList().add(testE);
        assertFalse(testSuperMarket.verifyEmployee("testA", "t"));
        assertEquals(0, testSuperMarket.getAccess());
    }

    @Test
    public void testVerifyEmployeeP() {
        Employee testE = new Employee("testE", "Supervisor", 3, "test");
        testSuperMarket.getEmployeeList().add(testE);
        assertTrue(testSuperMarket.verifyEmployee("testE", "test"));
        assertEquals(3, testSuperMarket.getAccess());
    }

    @Test
    public void testVerifyEmployeeNotPassword() {
        Employee testE = new Employee("testE", "Supervisor", 3, "test");
        testSuperMarket.getEmployeeList().add(testE);
        assertFalse(testSuperMarket.verifyEmployee("testE", "t"));
        assertEquals(0, testSuperMarket.getAccess());
    }

    @Test
    public void testVerifyEmployeeName() {
        Employee testE = new Employee("testE", "Supervisor", 3, "test");
        testSuperMarket.getEmployeeList().add(testE);
        assertFalse(testSuperMarket.verifyEmployee("testA", "test"));
        assertEquals(0, testSuperMarket.getAccess());
    }

    // Tests for giveItemByCode

    @Test
    public void testGiveItemByCodeNP() {
        Item testItem = new Item("Choclate", 123, 10, 10);
        testSuperMarket.getInventoriesList().get(0).addItems(testItem);

        Item returnedItem = testSuperMarket.giveItemByCode(125);
        assertEquals("not present", returnedItem.getName());
        assertEquals(0, returnedItem.getItemCode());
        assertEquals(0, returnedItem.getNumber());
        assertEquals(0, returnedItem.getPriceOfOne());
    }

    @Test
    public void testGiveItemByCodeP() {
        Item testItem1 = new Item("Choclate", 123, 10, 10);
        Item testItem2 = new Item("Chicken", 124, 10, 10);
        testSuperMarket.getInventoriesList().get(0).addItems(testItem1);
        testSuperMarket.getInventoriesList().get(1).addItems(testItem2);

        Item returnedItem = testSuperMarket.giveItemByCode(124);
        assertEquals("Chicken", returnedItem.getName());
        assertEquals(124, returnedItem.getItemCode());
        assertEquals(10, returnedItem.getNumber());
        assertEquals(10, returnedItem.getPriceOfOne());
    }

    // Tests for getNeedInventory

    @Test
    public void testGetNeededInventoryNP() {
        Inventory returnedInventory = testSuperMarket.getNeededInventory("Eggs");

        assertEquals("not present", returnedInventory.getInventoryName());
        assertEquals(0, returnedInventory.getItems().size());
    }

    @Test
    public void testGetNeededInventoryP() {
        Item testItem = new Item("Chicken", 123, 10, 10);
        testSuperMarket.getInventoriesList().get(1).addItems(testItem);
        Inventory returnedInventory = testSuperMarket.getNeededInventory("Meat");

        assertEquals("Meat", returnedInventory.getInventoryName());
        assertEquals(1, returnedInventory.getItems().size());
    }

    // Tests for findItemByCode

    @Test
    public void testFindItemByCodeNP() {
        Item testItem = new Item("Choclate", 123, 10, 10);
        testSuperMarket.getInventoriesList().get(0).addItems(testItem);


        Item returnedItem = testSuperMarket.findItemByCode(124, testSuperMarket.getInventoriesList().get(0));
        assertEquals("not present", returnedItem.getName());
        assertEquals(0, returnedItem.getItemCode());
        assertEquals(0, returnedItem.getNumber());
        assertEquals(0, returnedItem.getPriceOfOne());
    }

    @Test
    public void testFindItemByCodeP() {
        Item testItem = new Item("Chicken", 124, 10, 10);
        testSuperMarket.getInventoriesList().get(1).addItems(testItem);


        Item returnedItem = testSuperMarket.findItemByCode(124, testSuperMarket.getInventoriesList().get(1));
        assertEquals("Chicken", returnedItem.getName());
        assertEquals(124, returnedItem.getItemCode());
        assertEquals(10, returnedItem.getNumber());
        assertEquals(10, returnedItem.getPriceOfOne());
    }

    // Tests for setAccess

    @Test
    public void testSetAccess() {
        testSuperMarket.setAccess(1);

        assertEquals(1, testSuperMarket.getAccess());
    }

    // Tests for employeeLoggedIn

    @Test
    public void testSetEmployeeLoggedIn() {
        testSuperMarket.setEmployeeLoggedIn("Bat");

        assertEquals("Bat", testSuperMarket.getEmployeeLoggedIn());
    }

    // Tests for getCouponsList

    @Test
    public void testGetCouponsList() {
        assertEquals(0, testSuperMarket.getCouponsList().size());
    }

    // Tests for getNumLogins

    @Test
    public void testGetNumLogins() {
        assertEquals(0, testSuperMarket.getNumLogins());
    }

    // Tests for setBillingInProcess

    @Test
    public void testSetBillingInProcess() {
        testSuperMarket.setBillingInProcess(true);
        assertTrue(testSuperMarket.isBillingInProcess());
    }

    // Tests for searchEmployee

    @Test
    public void testSearchEmployeeP() {
        Employee e1 = new Employee("Derek", "Cashier", 2, "No");
        Employee e2 = new Employee("Scott", "Supervisor", 4, "Nope");
        testSuperMarket.getEmployeeList().add(e1);
        testSuperMarket.getEmployeeList().add(e2);

        Employee returnedE = testSuperMarket.searchEmployee("Scott");

        assertEquals("Scott", returnedE.getName());
        assertEquals("Supervisor", returnedE.getDesignation());
        assertEquals(4, returnedE.getAccessLevel());
        assertEquals("Nope", returnedE.getPassword());

    }

    @Test
    public void testSearchEmployeeNP() {
        Employee returnedE = testSuperMarket.searchEmployee("Scott");

        assertNull(returnedE);
    }

    // Tests for removeEmployee

    @Test
    public void testRemoveEmployeeP() {
        Employee e1 = new Employee("Derek", "Cashier", 2, "No");
        Employee e2 = new Employee("Scott", "Supervisor", 4, "Nope");
        testSuperMarket.getEmployeeList().add(e1);
        testSuperMarket.getEmployeeList().add(e2);

        testSuperMarket.removeEmployee("Scott");

        assertEquals(2, testSuperMarket.getEmployeeList().size());

    }

    @Test
    public void testRemoveEmployeeNP() {
        Employee e1 = new Employee("Derek", "Cashier", 2, "No");
        Employee e2 = new Employee("Scott", "Supervisor", 4, "Nope");
        testSuperMarket.getEmployeeList().add(e1);
        testSuperMarket.getEmployeeList().add(e2);

        testSuperMarket.removeEmployee("Clara");

        assertEquals(3, testSuperMarket.getEmployeeList().size());
    }

    // Tests for getCashierRecords

    @Test
    public void testGetCashierRecordsENP() {
        assertNull(testSuperMarket.getCashierRecords("Max"));
    }

    @Test
    public void testGetCashierRecordsEP1() {
        Employee e1 = new Employee("Max", "Cashier", 2, "No");
        testSuperMarket.getEmployeeList().add(e1);

        List<TransactionRecord> returned = testSuperMarket.getCashierRecords("Max");
        assertEquals(0, returned.size());
    }

    @Test
    public void testGetCashierRecordsEP2() {
        Employee e1 = new Employee("Max", "Cashier", 2, "No");
        testSuperMarket.getEmployeeList().add(e1);

        Receipt r1 = new Receipt();
        Receipt r2 = new Receipt();
        testSuperMarket.getTransactionsList().add(new TransactionRecord(r1, "Max"));
        testSuperMarket.getTransactionsList().add(new TransactionRecord(r2, "Max"));
        testSuperMarket.getTransactionsList().add(new TransactionRecord(r2, "Baxter"));

        List<TransactionRecord> returned = testSuperMarket.getCashierRecords("Max");
        assertEquals(2, returned.size());
    }

    // Tests for giveCoupon

    @Test
    public void testGiveCouponP() {
        testSuperMarket.getCouponsList().add(new Coupon("TEST1", 50));
        testSuperMarket.getCouponsList().add(new Coupon("TEST2", 50));

        Coupon c = testSuperMarket.giveCoupon("TEST2");

        assertEquals("TEST2", c.getCouponCode());
        assertEquals(50, c.getValue());
    }

    @Test
    public void testGiveCouponNP() {
        testSuperMarket.getCouponsList().add(new Coupon("TEST1", 50));
        testSuperMarket.getCouponsList().add(new Coupon("TEST2", 50));

        assertNull(testSuperMarket.giveCoupon("TEST3"));
    }

    // Tests for setAccessLevelOfEmployee

    @Test
    public void testSetAccessLevelOfEmployeeNotPresent() {
        testSuperMarket.setAccess(5);

        boolean result = testSuperMarket.setAccessLevelOfEmployee("Barry", 3, "Cashier");

        assertFalse(result);
    }

    @Test
    public void testSetAccessLevelOfEmployeeAccessA() {
        testSuperMarket.setAccess(5);
        Employee e1 = new Employee("Barry", "Supervisor", 4, "No");
        testSuperMarket.getEmployeeList().add(e1);

        boolean result = testSuperMarket.setAccessLevelOfEmployee("Barry", 3, "Cashier");

        assertTrue(result);
    }

    @Test
    public void testSetAccessLevelOfEmployeeAccessNA() {
        testSuperMarket.setAccess(3);
        Employee e1 = new Employee("Barry", "Supervisor", 4, "No");
        testSuperMarket.getEmployeeList().add(e1);

        boolean result = testSuperMarket.setAccessLevelOfEmployee("Barry", 3, "Cashier");

        assertFalse(result);
    }

    @Test
    public void testGetEmployeesBelowAccess2() {
        testSuperMarket.getEmployeeList().add(new Employee("Test1", "Cashier",
                2, "test"));
        testSuperMarket.getEmployeeList().add(new Employee("Test2", "Cashier",
                2, "test"));
        testSuperMarket.getEmployeeList().add(new Employee("Test3", "Senior Cashier",
                3, "test"));
        testSuperMarket.getEmployeeList().add(new Employee("Test4", "Supervisor",
                4, "test"));
        testSuperMarket.getEmployeeList().add(new Employee("Test5", "Supervisor",
                4, "test"));

        List<Employee> changedList = testSuperMarket.getEmployeesBelowAccess(3);

        assertTrue(changedList.size() == 2);

        Employee e1 = changedList.get(0);
        assertEquals("Test1", e1.getName());
        assertEquals("Cashier", e1.getDesignation());
        assertEquals(2, e1.getAccessLevel());
        assertEquals("test", e1.getPassword());

        Employee e2 = changedList.get(1);
        assertEquals("Test2", e2.getName());
        assertEquals("Cashier", e2.getDesignation());
        assertEquals(2, e2.getAccessLevel());
        assertEquals("test", e2.getPassword());

    }

    @Test
    public void testGetEmployeesBelowAccessNone() {
        testSuperMarket.getEmployeeList().add(new Employee("Test1", "Cashier",
                2, "test"));
        testSuperMarket.getEmployeeList().add(new Employee("Test1", "Cashier",
                1, "test"));
        testSuperMarket.getEmployeeList().add(new Employee("Test1", "Cashier",
                3, "test"));

        List<Employee> changedList = testSuperMarket.getEmployeesBelowAccess(1);

        assertTrue(changedList.size() == 0);
    }

}
