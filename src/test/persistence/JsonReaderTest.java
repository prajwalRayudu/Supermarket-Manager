package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

// DOCUMENTATION
// Used the JsonReaderTest class from the repository named JsonSerializationDemo to design this class
// Repository link https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonReaderTest extends JsonTest {

    @Test
    public void testInvalidFieLocation() {
        JsonReader testReader = new JsonReader("./data/NonExistingFile.json");

        try {
            SuperMarket testMarket = testReader.read();
            fail("Actual File does not exist, this is not expected");

        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderInitialSuperMarket() {
        JsonReader testReader = new JsonReader("./data/testReaderFileInitialSuperMarket.json");

        try {
            SuperMarket testMarket = testReader.read();
            SuperMarket test = new SuperMarket("Test Market");

            checkSuperMarket(test, testMarket);

        } catch (IOException e) {
            fail("Couldn't read file, this is not expected");
        }
    }

    @Test
    public void testReaderEditedSuperMarket() {
        JsonReader testReader = new JsonReader("./data/testReaderFileEditedSuperMarket.json");

        try {
            SuperMarket testMarket = testReader.read();

            // Things need to add to test
            Employee e1 = new Employee("test1", "Cashier", 2, "test");
            Employee e2 = new Employee("test2", "Stocking Staff", 1, "test");
            Coupon c1 = new Coupon("TEST1", 50);
            Coupon c2 = new Coupon("TEST2", 50);
            TransactionRecord t1 = new TransactionRecord(new Receipt(), "test1");
            TransactionRecord t2 = new TransactionRecord(new Receipt(), "test2");
            Item i1 = new Item("Eggs", 12, 10, 11);
            Item i2 = new Item("Beef", 12, 10, 11);
            Item i3 = new Item("Chicken", 12, 10, 11);
            Employee e3 = new Employee("test3", "Cashier", 2, "test");
            Employee e4 = new Employee("test4", "Stocking Staff", 1, "test");
            Item i4 = new Item("Beef", 12, 5, 11);
            Item i5 = new Item("Chicken", 12, 5, 11);
            Coupon c3 = new Coupon("TEST3", 50);
            Coupon c4 = new Coupon("TEST4", 50);


            SuperMarket test = new SuperMarket("Test Market");
            test.setNumLogins(2);
            test.getEmployeeList().add(e1);
            test.getEmployeeList().add(e2);
            test.getCouponsList().add(c1);
            test.getCouponsList().add(c2);
            test.getTransactionsList().add(t1);
            test.getTransactionsList().add(t2);
            test.getInventoriesList().get(1).addItems(i2);
            test.getInventoriesList().get(1).addItems(i3);
            test.getInventoriesList().get(5).addItems(i1);
            t1.getReceipt().getItemsBought().add(i1);
            t1.getReceipt().getItemsBought().add(i2);
            t1.getReceipt().getCouponsUsed().add(new Coupon("TEST3", 50));
            t1.getReceipt().getCouponsUsed().add(new Coupon("TEST4", 50));
            test.commonReceipt.getItemsBought().add(i4);
            test.commonReceipt.getItemsBought().add(i5);
            test.commonReceipt.getCouponsUsed().add(c3);
            test.commonReceipt.getCouponsUsed().add(c4);

            checkSuperMarket(test, testMarket);

        } catch (IOException e) {
            fail("Couldn't read file, this is not expected");
        }
    }
}
