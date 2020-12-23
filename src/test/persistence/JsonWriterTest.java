package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

// DOCUMENTATION
// Used the JsonWriterTest class from the repository named JsonSerializationDemo to design this class
// Repository link https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonWriterTest extends JsonTest {
    @Test
    public void testFileInvalid() {
        try {
            SuperMarket test = new SuperMarket("Test Market");
            JsonWriter testWriter = new JsonWriter("./data/my\0illegal:nonExistingFile.json");
            testWriter.openFileToWrite();
            fail("File not existing, so this is not expected");

        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testFileValidInitialSuperMarket() {
        try {
            SuperMarket test = new SuperMarket("Test Market");
            JsonWriter testWriter = new JsonWriter("./data/testWriterFileInitialSuperMarket.json");
            testWriter.openFileToWrite();
            testWriter.writeOnFile(test);
            testWriter.closeFile();

            JsonReader testReader = new JsonReader("./data/testWriterFileInitialSuperMarket.json");
            SuperMarket readSM = testReader.read();

            assertTrue(checkSuperMarket(test, readSM));

        } catch (IOException e) {
            fail("Exception should not Occur");
        }
    }

    @Test
    public void testFileValidEditedSuperMarket() {
        try {
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
            test.getInventoriesList().get(1).getItems().add(i2);
            test.getInventoriesList().get(1).getItems().add(i3);
            test.getInventoriesList().get(5).getItems().add(i1);
            test.getOldEmployees().add(e3);
            test.getOldEmployees().add(e4);
            test.commonReceipt.getItemsBought().add(i4);
            test.commonReceipt.getItemsBought().add(i5);
            test.commonReceipt.getCouponsUsed().add(c3);
            test.commonReceipt.getCouponsUsed().add(c4);

            JsonWriter testWriter = new JsonWriter("./data/testWriterFileEditedSuperMarket.json");
            testWriter.openFileToWrite();
            testWriter.writeOnFile(test);
            testWriter.closeFile();

            JsonReader testReader = new JsonReader("./data/testWriterFileEditedSuperMarket.json");
            SuperMarket readSM = testReader.read();

            assertTrue(checkSuperMarket(test, readSM));

        } catch (IOException e) {
            fail("Exception should not Occur");
        }
    }
}
