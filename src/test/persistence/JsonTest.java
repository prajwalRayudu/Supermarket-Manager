package persistence;

import model.*;

import java.util.List;

// DOCUMENTATION
// Used the JsonTest class from the repository named JsonSerializationDemo to design this class
// Repository link https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonTest {
    public boolean checkSuperMarket(SuperMarket s1, SuperMarket s2) {
        boolean name = s1.getSuperMarketName().equals(s2.getSuperMarketName());
        boolean access = (s1.getAccess() == s2.getAccess());
        boolean numLogins = (s1.getNumLogins() == s2.getNumLogins());

        boolean employeeList = checkEList(s1.getEmployeeList(), s2.getEmployeeList());
        boolean transactions = checkTList(s1.getTransactionsList(), s2.getTransactionsList());
        boolean inventories = checkIList(s1.getInventoriesList(), s2.getInventoriesList());
        boolean coupons = checkCList(s1.getCouponsList(), s2.getCouponsList());

        boolean oldEmployees = checkEList(s1.getOldEmployees(), s2.getOldEmployees());
        boolean commonR = checkReceipt(s1.commonReceipt, s2.commonReceipt);
        boolean billingInProcess = (Boolean.compare(s1.isBillingInProcess(), s2.isBillingInProcess()) == 0);

        boolean result = (name && access && numLogins && employeeList && transactions && inventories && coupons &&
                oldEmployees && commonR && billingInProcess);

        return result;
    }

    public boolean checkEList(List<Employee> l1, List<Employee> l2) {
        if (l1.size() != l2.size()) {
            return false;
        }

        for (int i = 0; i < l1.size(); i++) {
            if (!(checkEmployees(l1.get(i), l2.get(i)))) {
                return false;
            }
        }
        return true;
    }

    public boolean checkTList(List<TransactionRecord> l1, List<TransactionRecord> l2) {
        if (l1.size() != l2.size()) {
            return false;
        }

        for (int i = 0; i < l1.size(); i++) {
            if (!(checkTransactions(l1.get(i), l2.get(i)))) {
                return false;
            }
        }
        return true;
    }

    public boolean checkIList(List<Inventory> l1, List<Inventory> l2) {
        if (l1.size() != l2.size()) {
            return false;
        }

        for (int i = 0; i < l1.size(); i++) {
            if (!(checkInventory(l1.get(i), l2.get(i)))) {
                return false;
            }
        }
        return true;
    }

    public boolean checkCList(List<Coupon> l1, List<Coupon> l2) {
        if (l1.size() != l2.size()) {
            return false;
        }

        for (int i = 0; i < l1.size(); i++) {
            if (!(checkCoupon(l1.get(i), l2.get(i)))) {
                return false;
            }
        }
        return true;
    }

    public boolean checkEmployees(Employee e1, Employee e2) {
        boolean name = e1.getName().equals(e2.getName());
        boolean designation = e1.getDesignation().equals(e2.getDesignation());
        boolean accessLevel = (e1.getAccessLevel() == e2.getAccessLevel());
        boolean password = e1.getPassword().equals(e2.getPassword());

        return (name && designation && accessLevel && password);
    }

    public boolean checkTransactions(TransactionRecord t1, TransactionRecord t2) {
        if (!(t1.getCashierName().equals(t2.getCashierName()))) {
            return false;
        }

        Receipt r1 = t1.getReceipt();
        Receipt r2 = t2.getReceipt();

        return checkReceipt(r1, r2);
    }

    private boolean checkReceipt(Receipt r1, Receipt r2) {
        if (!(r1.getCouponsUsed().size() == r2.getCouponsUsed().size())) {
            return false;
        }

        if (!(r1.getItemsBought().size() == r2.getItemsBought().size())) {
            return false;
        }

        for (int i = 0; i < r1.getItemsBought().size(); i++) {
            if (!(checkItems(r1.getItemsBought().get(i), r2.getItemsBought().get(i)))) {
                return false;
            }
        }

        for (int i = 0; i < r1.getCouponsUsed().size(); i++) {
            if (!(checkCoupon(r1.getCouponsUsed().get(i), r2.getCouponsUsed().get(i)))) {
                return false;
            }
        }

        return true;
    }


    public boolean checkInventory(Inventory i1, Inventory i2) {
        boolean name = i1.getInventoryName().equals(i2.getInventoryName());
        boolean size = (i1.getItems().size() == i2.getItems().size());

        if (!name) {
            return false;
        }

        if (!size) {
            return false;
        }

        for (int i = 0; i < i1.getItems().size(); i++) {
            if (!(checkItems(i1.getItems().get(i), i2.getItems().get(i)))) {
                return false;
            }
        }

        return true;
    }

    public boolean checkCoupon(Coupon c1, Coupon c2) {
        boolean couponCode = c1.getCouponCode().equals(c2.getCouponCode());
        boolean value = (c1.getValue() == c2.getValue());

        return (couponCode && value);
    }

    public boolean checkItems(Item i1, Item i2) {
        boolean name = i1.getName().equals(i2.getName());
        boolean itemCode = (i1.getItemCode() == i2.getItemCode());
        boolean number = (i1.getNumber() == i2.getNumber());
        boolean priceOfOne = (i1.getPriceOfOne() == i2.getPriceOfOne());

        return (name && itemCode && number && priceOfOne);
    }
}