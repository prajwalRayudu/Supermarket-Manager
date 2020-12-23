package ui;

/**
 * THIS IS THE UI BASED CONSOLE CLASS, This is not included in the UML Diagram.
 * IT IS NOT BEING USED ANYMORE
 */

import exceptions.StockCannotBeEditedException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// processes the user interface of supermarket
public class SuperMarketUi {
    // Fields
    Scanner infoIntake = new Scanner(System.in);
    private SuperMarket superMarket;

    // JSON Data saving related Fields
    private static final String JSON_STORE = "./data/supermarket.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Constructor
    // EFFECTS: Constructs a SuperMarketUi, makes a supermarket object and initiates by calling the mainmenu()
    public SuperMarketUi(String superMarketName) {
        superMarket = new SuperMarket(superMarketName);
        superMarket.setBillingInProcess(false);
        System.out.println("Please add at least one Cashier, Stocking Staff and a Supervisor before proceeding.");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        mainMenu();
    }

    // Methods

    // METHODS FOR ALL MENUS

    // EFFECTS: prints out the access menus based on the access
    public void printAccessMenu() {
        switch (superMarket.getAccess()) {
            case 1:
                printLevel1Ins();
                break;

            case 2:
                printLevel2Ins();
                break;

            case 3:
                printLevel3Ins();
                break;

            case 4:
                printLevel4Ins();
                break;

            case 5:
                printLevel5Ins();
                break;
        }

    }

    // EFFECTS: prints out the level 1 options
    private void printLevel1Ins() {
        System.out.println("Press 1 to look at Inventory");
        System.out.println("Press 2 to add items to inventory");
        System.out.println("Press 3 to add a new item to inventory");
        System.out.println("Press 4 to look at the menu");
        System.out.println("Press 5 to Loguot");
    }

    // EFFECTS: prints out the level 2 options
    private void printLevel2Ins() {
        System.out.println("Press 1 start billing a customer");
        System.out.println("Press 2 to look at the menu");
        System.out.println("Press 3 to Loguot");
    }

    // EFFECTS: prints out the level 3 options
    private void printLevel3Ins() {
        System.out.println("Press 1 add item to billing");
        System.out.println("Press 2 remove item from billing");
        System.out.println("Press 3 to add a coupon");
        System.out.println("Press 4 remove coupon from billing");
        System.out.println("Press 5 print a cashier transaction Record");
        System.out.println("Press 6 to look at the menu");
        System.out.println("Press 7 to Loguot");
    }

    // EFFECTS: prints out the level 4 options
    private void printLevel4Ins() {
        System.out.println("Press 1 to add an employee");
        System.out.println("Press 2 to view the Transaction Records");
        System.out.println("Press 3 to look at Inventory");
        System.out.println("Press 4 to add items to inventory");
        System.out.println("Press 5 to remove an employee");
        System.out.println("Press 6 to look at the menu");
        System.out.println("Press 7 make a coupon");
        System.out.println("Press 8 to Loguot");
    }

    // EFFECTS: prints out the level 5 options
    private void printLevel5Ins() {
        System.out.println("Press 1 to add an employee");
        System.out.println("Press 2 to remove an employee");
        System.out.println("Press 3 to view the Transaction Records");
        System.out.println("Press 4 to look at Inventory");
        System.out.println("Press 5 to add items to inventory");
        System.out.println("Press 6 to change access of an employee");
        System.out.println("Press 7 to add new Coupon");
        System.out.println("Press 8 to look at the menu");
        System.out.println("Press 9 to logout");
    }

    // EFFECTS: prints out the billing menu options
    private void printBillingMenuOptions() {
        System.out.println("Choose out of the following options");
        System.out.println("Press 1 to add next item");
        System.out.println("Press 2 to add a coupon");
        System.out.println("Press 3 to call the senior cashier");
        System.out.println("Press 4 to look at the options again");
        System.out.println("Press 5 to finish the customer billing");
    }

    // EFFECTS: prints out the options available in the mainMenu
    private void printMainMenuOptions() {
        System.out.println("Choose form the options");
        System.out.println("Press 1 to go to the login page");
        System.out.println("Press 2 to quit the program");
    }


    // METHODS FOR LOGIN

    // EFFECTS: Does the login process by access and verifying
    //          the login credentials with teh ones in the employee list
    public void login() {
        boolean validLogin = false;
        validLogin = collectAndVerifyLogin();

        if (!validLogin) {
            superMarket.setAccess(-1);
            System.out.println("Employee credentials wrong");
        }


        if ((superMarket.getEmployeeList().size() == 1) && (superMarket.getNumLogins() == 0) && validLogin) {
            processInitialPasswordReset();
        }

        superMarket.setNumLogins(superMarket.getNumLogins() + 1);

        if (superMarket.getAccess() > 0) {
            System.out.println("You have been authorised upto level " + superMarket.getAccess());
        }

        processLogin();

    }

    // EFFECTS: processes the login process based on the access
    private void processLogin() {
        switch (superMarket.getAccess()) {
            case 1:
                stockingStaffAccessGranted();
                break;

            case 2:
                cashierAccessGranted();
                break;

            case 3:
                seniorCashierAccessGranted();
                break;

            case 4:
                supervisorAccessGranted();
                break;

            case 5:
                adminAccessGranted();
                break;
            default:
                break;

        }
    }

    // MODIFIES: this
    // EFFECTS: if billing is in process, then it logs back in into the cashier and sets billing in process to false
    private void logBackIn() {
        if (superMarket.isBillingInProcess()) {
            superMarket.setAccess(2);
            System.out.println("you are logged back in " + superMarket.getEmployeeLoggedIn());
            superMarket.setBillingInProcess(false);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes the initial password of the admin
    private void processInitialPasswordReset() {
        System.out.println("Initial Admin Password Reset");
        System.out.println("Please enter your new password");
        String newPassword = infoIntake.nextLine();
        superMarket.getEmployeeList().get(0).setPasscode(newPassword);
        System.out.println("Thank you, your password has been changed successfully");
    }

    // EFFECTS: verifies the login input given by the user with the employee list
    private boolean collectAndVerifyLogin() {
        System.out.println("Let us get you logged in");
        System.out.println("Please enter your given employee name");
        String name = infoIntake.nextLine();
        System.out.println("Please enter your password");
        String password = infoIntake.nextLine();

        boolean validLogin = superMarket.verifyEmployee(name, password);

        if (validLogin && !superMarket.isBillingInProcess()) {
            superMarket.setEmployeeLoggedIn(name);
        } else if (validLogin) {
            if (superMarket.searchEmployee(name).getAccessLevel() != 3) {
                System.out.println("Not Allowed");
                return false;
            }
        }

        return validLogin;
    }


    // METHODS FOR ALL MENU PROCESSORS

    // EFFECTS: processes the main menu which takes user input and functions according to the mainMenu options
    public void mainMenu() {
        boolean quit = false;

        loadSuperMarket();
        int choice;
        while (!quit) {
            if (superMarket.isBillingInProcess()) {
                login();
            }
            printMainMenuOptions();
            try {
                choice = Integer.parseInt(infoIntake.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
                continue;
            }

            quit = processMainMenuChoice(choice);
        }
    }

    // EFFECTS: processes the choice given in main menu
    private boolean processMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                login();
                break;

            case 2:
                saveSuperMarket();
                return true;


        }
        return false;
    }


    // EFFECTS: processes the admin level
    private void adminAccessGranted() {
        boolean quit = false;

        int choice;
        while (!quit) {
            printAccessMenu();
            try {
                choice = Integer.parseInt(infoIntake.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
                continue;
            }

            quit = processAdminChoice(choice);
        }
    }

    // EFFECTS: processes the admin choices
    private boolean processAdminChoice(int choice) {
        if (choice % 2 == 0) {
            return processEvenAdminChoice(choice);
        } else {
            return processOddAdminChoice(choice);
        }
    }

    // EFFECTS: processes the even admin choices
    private boolean processEvenAdminChoice(int choice) {
        switch (choice) {
            case 2:
                removeAnEmployee();
                break;

            case 4:
                printAnInventory();
                break;

            case 6:
                changeAccessLevel();
                break;

            case 8:
                printAccessMenu();
                break;
        }
        return false;
    }

    // EFFECTS: processes the odd admin choices
    private boolean processOddAdminChoice(int choice) {
        switch (choice) {
            case 1:
                addNewEmployee();
                break;

            case 3:
                printTransactionRecords();
                break;

            case 5:
                addItemsToInventory();
                break;

            case 7:
                makeACoupon();
                break;

            case 9:
                superMarket.setAccess(-1);
                return true;
        }
        return false;
    }


    // EFFECTS: gives access to the options available to the supervisor and processes them
    private void supervisorAccessGranted() {
        boolean quit = false;

        int choice;
        while (!quit) {
            printAccessMenu();
            try {
                choice = Integer.parseInt(infoIntake.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
                continue;
            }

            quit = processSupervisorChoice(choice);
        }
    }

    // EFFECTS: processes the senior cashier level
    private void seniorCashierAccessGranted() {
        boolean quit = false;

        int choice;
        while (!quit) {
            printAccessMenu();
            try {
                choice = Integer.parseInt(infoIntake.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
                continue;
            }

            quit = processSeniorCashierChoice(choice);
        }
    }

    //EFFECTS: processes the senior cashier choices
    private boolean processSeniorCashierChoice(int choice) {
        if (superMarket.isBillingInProcess()) {
            return processSeniorCashierChoiceWhileBilling(choice);
        } else {
            return processSeniorCashierChoiceWhileNotBilling(choice);
        }
    }

    // EFFECTS: processes the secior cashier choices when billing is true
    private boolean processSeniorCashierChoiceWhileBilling(int choice) {
        switch (choice) {
            case 1:
                processAddingItem();
                break;

            case 2:
                processRemovingItem();
                break;

            case 3:
                processAddingCoupon();
                break;

            case 4:
                processRemovingCoupon();
                break;

            case 6:
                printAccessMenu();
                break;

            case 7:
                return true;

            default:
                System.out.println("Operation not allowed");
                break;
        }
        return false;
    }

    // EFFECTS: processes the secior cashier choices when billing is false
    private boolean processSeniorCashierChoiceWhileNotBilling(int choice) {
        switch (choice) {
            case 5:
                if (!superMarket.isBillingInProcess()) {
                    printOneCashierRecord();
                } else {
                    System.out.println("Operation not allowed during a billing");
                }
                break;

            case 6:
                printAccessMenu();
                break;

            case 7:
                return true;

            default:
                System.out.println("Billing is not going on");
                break;
        }
        return false;
    }


    // EFFECTS: gives access to the options available to the Cashier and processes them
    private void cashierAccessGranted() {
        boolean quit = false;

        int choice;
        while (!quit) {
            printAccessMenu();
            try {
                choice = Integer.parseInt(infoIntake.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
                continue;
            }

            switch (choice) {
                case 1:
                    // start billing
                    billing();
                    continue;

                case 2:
                    printAccessMenu();
                    continue;

                case 3:
                    superMarket.setAccess(-1);
                    quit = true;
                    continue;
            }
        }
    }


    // EFFECTS: gives access to the options available to the stocking staff and processes them
    private void stockingStaffAccessGranted() throws NumberFormatException {
        boolean quit = false;

        int choice;
        while (!quit) {
            printAccessMenu();
            try {
                choice = Integer.parseInt(infoIntake.nextLine());
            } catch (NumberFormatException e) {
                continue;
            }

            quit = processStockingStaffChoice(choice);
        }
    }

    // EFFECTS: processes the choice of the Stocking staff employee
    private boolean processStockingStaffChoice(int choice) {
        switch (choice) {
            case 1:
                printAnInventory();
                break;

            case 2:
                addItemsToInventory();
                break;

            case 3:
                addNewItem();
                break;

            case 4:
                printAccessMenu();
                break;

            case 5:
                superMarket.setAccess(-1);
                return true;
        }
        return false;
    }


    // METHODS FOR ALL CHOICE PROCESSORS
    // EFFECTS: processes the function according to the choice given by the supervisor
    private boolean processSupervisorChoice(int choice) {
        if (choice % 2 == 0) {
            return processEvenSupervisorChoice(choice);
        } else {
            return processOddSupervisorChoice(choice);
        }
    }

    // EFFECTS: processes the even choices of the supervisor
    private boolean processEvenSupervisorChoice(int choice) {
        switch (choice) {
            case 2:
                printTransactionRecords();
                break;

            case 4:
                addItemsToInventory();
                break;

            case 6:
                printAccessMenu();
                break;

            case 8:
                superMarket.setAccess(-1);
                return true;
        }
        return false;
    }
//
    // EFFECTS: processes the odd choices of the supervisor

    private boolean processOddSupervisorChoice(int choice) {
        switch (choice) {
            case 1:
                addNewEmployee();
                break;

            case 3:
                printAnInventory();
                break;

            case 5:
                removeAnEmployee();
                break;

            case 7:
                makeACoupon();
                break;
        }
        return false;
    }

    // EFFECTS: Does the billing process of the cashier by taking in the required information
    public void billing() {
        boolean quit = false;
        int choice;
        superMarket.commonReceipt = new Receipt();


        while (!quit) {
            printBillingMenuOptions();
            try {
                choice = Integer.parseInt(infoIntake.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
                continue;
            }

            quit = processBillingChoice(choice);
        }

        finishCustomer();
    }

    // EFFECTS: processes the choice for billing
    private boolean processBillingChoice(int choice) {
        switch (choice) {
            case 1:
                processAddingItem();
                break;

            case 2:
                processAddingCoupon();
                break;

            case 3:
                superMarket.setBillingInProcess(true);
                login();
                logBackIn();
                superMarket.setAccess(2);
                break;

            case 4:
                printBillingMenuOptions();
                break;

            case 5:
                return true;
        }

        return false;
    }


    // METHODS BASED ON EMPLOYEES

    // EFFECTS: Checks if the user has level 3 access, and makes and adds a new employee
    //          to the employee list by taking the collecting the user input
    private void addNewEmployee() {
        if (superMarket.getAccess() > 3) {
            System.out.println("Please enter the name of the employee:");
            String name = infoIntake.nextLine();

            System.out.println("Enter the designation given:");
            String designation = infoIntake.nextLine();

            System.out.println("Please enter the access level:");
            int level = Integer.parseInt(infoIntake.nextLine());

            System.out.println("Please enter the new password:");
            String password = infoIntake.nextLine();

            superMarket.getEmployeeList().add(new Employee(name, designation, level, password));
            System.out.println("Added New Employee");
        } else {
            System.out.println("You are not allowed to take this action");
        }
    }

    // EFFECTS: finishes up the customer by printing the receipt and
    //          then adding a new record with the information to the list
    public void finishCustomer() {
        printReceipt(superMarket.commonReceipt);
        TransactionRecord t = new TransactionRecord(superMarket.commonReceipt, superMarket.getEmployeeLoggedIn());
        superMarket.getTransactionsList().add(t);
    }

    // MODIFIES: this
    // EFFECTS: procsses the removal of an employee
    private void removeAnEmployee() {
        System.out.println("Please enter the name of the employee you want to remove:");
        String name = infoIntake.nextLine();

        if (superMarket.searchEmployee(name) != null) {
            System.out.println("Are you sure you want to remove this employee?");
            System.out.println("Press Yes or No");
            String ans = infoIntake.nextLine().toLowerCase();
            if (ans.equals("yes")) {
                superMarket.removeEmployee(name);
            }
        } else {
            System.out.println("Employee does not exist");
        }
    }

    // METHODS DEALING WITH ITEMS AND INVENTORIES

    // EFFECTS: searches for the inventory with th einputted name, and prints out the inventory if present
    private void printAnInventory() {
        System.out.println("Please enter the name of the inventory you want");
        String inventoryName = infoIntake.nextLine();

        Inventory inventory = superMarket.getNeededInventory(inventoryName);

        if (inventory.getInventoryName().equals("not present")) {
            System.out.println("Inventory not present");
        } else {
            System.out.println(inventory.getInventoryName() + ":");
            toStringInventory(inventory);
        }
    }

    // EFFECTS: adds the stock to the already existing item by taking in required information
    private void addItemsToInventory() {
        System.out.println("Please enter the name of the inventory you want add the item to:");
        String inventoryName = infoIntake.nextLine();

        Inventory inventory = superMarket.getNeededInventory(inventoryName);

        if (inventory.getInventoryName().equals("not present")) {
            System.out.println("Inventory not present");
        } else {
            System.out.println("Please enter the item code:");
            int itemCode = Integer.parseInt(infoIntake.nextLine());

            System.out.println("Please enter the quantity you want to add to the item:");
            int quantity = Integer.parseInt(infoIntake.nextLine());

            Item item = superMarket.findItemByCode(itemCode, inventory);

            if (item.getName().equals("not present")) {
                System.out.println("Item not present");
            } else {
                try {
                    item.addStock(quantity);
                } catch (StockCannotBeEditedException e) {
                    System.out.println("Quantity entered has to be greater than 0");
                }

                System.out.println("Stock added!!!");
            }
        }
    }

    // EFFECTS: takes in required formation and adds a new item to the inputted inventory name after making an item
    private void addNewItem() {
        System.out.println("Please enter the name of the inventory you want add the item to:");
        String inventoryName = infoIntake.nextLine();

        Inventory inventory = superMarket.getNeededInventory(inventoryName);

        if (inventory.getInventoryName().equals("not present")) {
            System.out.println("Inventory not present");
        } else {
            System.out.println("Please enter the name of the item you want to add:");
            String itemName = infoIntake.nextLine();

            System.out.println("Please enter the item code of the item you want to add:");
            int itemCode = Integer.parseInt(infoIntake.nextLine());

            if (superMarket.findItemByCode(itemCode, inventory).getName().equals("not present")) {
                System.out.println("Please enter the quantity of the item you want to add:");
                int number = Integer.parseInt(infoIntake.nextLine());

                System.out.println("Please enter the price of one item you want to add:");
                int price = Integer.parseInt(infoIntake.nextLine());

                inventory.addItems(new Item(itemName, itemCode, number, price));
            } else {
                System.out.println("Item with the same code already present");
            }
        }
    }

    // EFFECTS: searches for teh item inputted and adds it to the receipt, also reduces the stock by given
    //          quantity if the item is present
    private void processAddingItem() {
        int code;
        System.out.println("Please enter the item code:");
        if (infoIntake.hasNextInt()) {
            code = infoIntake.nextInt();
            infoIntake.nextLine();
            Item itemByCode = superMarket.giveItemByCode(code);

            if (itemByCode.getName() != null) {
                System.out.println("Please enter the quantity:");
                int num = Integer.parseInt(infoIntake.nextLine());

                addItem(itemByCode, num);
            } else {
                System.out.println("Item not present");
            }
        }
    }

    // EFFECTS: adds the item to the receipt
    private void addItem(Item itemByCode, int num) {
        if (itemByCode.getNumber() >= num) {
            int price = itemByCode.getPriceOfOne();
            Item newOne = new Item(itemByCode.getName(), itemByCode.getItemCode(), num, price);
            superMarket.commonReceipt.getItemsBought().add(newOne);

            try {
                itemByCode.removeStock(num);
            } catch (StockCannotBeEditedException e) {
                System.out.println("Invalid quantity");
            }
        } else {
            System.out.println("Not enough stock available");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes the removal of an item using its code
    private void processRemovingItem() {
        int code;
        System.out.println("Please enter the item code:");
        if (infoIntake.hasNextInt()) {
            code = infoIntake.nextInt();
            infoIntake.nextLine();
            Item itemToReturn = superMarket.giveItemByCode(code);
            Item itemToEdit = superMarket.commonReceipt.giveItemFromReceipt(code);

            if (!itemToEdit.getName().equals("not present")) {
                try {
                    itemToReturn.addStock(itemToEdit.getNumber());
                } catch (StockCannotBeEditedException e) {
                    System.out.println("Quantity entered has to be greater than 0");
                }
                superMarket.commonReceipt.getItemsBought().remove(itemToEdit);
            } else {
                System.out.println("Item not present");
            }
        }
    }

    // METHODS DEALING WITH Coupons

    // MODIFIES: this
    // EFFECTS: processes the making of a coupon and adding it
    private void makeACoupon() {
        System.out.println("Please enter the coupon code");
        String couponCode = infoIntake.nextLine();

        if ((superMarket.giveCoupon(couponCode) == null)) {
            System.out.println("Please enter the value of the coupon being made");
            int value = Integer.parseInt(infoIntake.nextLine());
            superMarket.getCouponsList().add(new Coupon(couponCode, value));
            System.out.println("Coupon Added!");
        } else {
            System.out.println("Coupon with that code already exists");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes the adding of a coupon and to receipt
    private void processAddingCoupon() {
        System.out.println("Please enter the coupon code:");
        String code = infoIntake.nextLine();
        Coupon coupon = superMarket.giveCoupon(code);
        if (coupon != null) {
            superMarket.commonReceipt.getCouponsUsed().add(coupon);
            superMarket.getCouponsList().remove(coupon);
            System.out.println("Coupon applied");
        } else {
            System.out.println("Coupon not present");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes the removal of a coupon from receipt
    private void processRemovingCoupon() {
        System.out.println("Please enter the coupon code:");
        String code = infoIntake.nextLine();

        System.out.println(superMarket.commonReceipt.removeCoupon(code));
    }

    // METHODS USED TO CHANGE ACCESS LEVEL

    // MODIFIES: this
    // EFFECTS: processes the changing of the access level and the designation of an employee
    private void changeAccessLevel() {
        System.out.println("Please enter the name of employee you want to change the access level of");
        String name = infoIntake.nextLine();

        System.out.println("Please enter the new access level of the employee");
        int newAccessLevelToAssign = Integer.parseInt(infoIntake.nextLine());

        System.out.println("please enter the new designation of the employee");
        String newDesignation = infoIntake.nextLine();

        boolean done = superMarket.setAccessLevelOfEmployee(name, newAccessLevelToAssign, newDesignation);
        if (done) {
            System.out.println("Successfully changed Access level and designation of the employee");
        } else {
            System.out.println("Error: Could not complete the process.");
        }
    }


    // METHODS USED TO PRINT THINGS

    // EFFECTS: prints the list of items present in the given inventory
    public void toStringInventory(Inventory inventory) {
        for (Item i : inventory.getItems()) {
            System.out.println(i.getItemCode() + "    " + i.getName() + "    " + i.getNumber());
        }
    }

    // EFFECTS: prints the list of all the transactions in the list
    public void printTransactionRecords() {
        int count = 0;
        for (TransactionRecord i : superMarket.getTransactionsList()) {
            System.out.println("Transaction " + count + 1 + ":");
            printRecord(i);
            count++;
        }
    }

    // EFFECTS: prints the details of the record given
    public void printRecord(TransactionRecord tr) {
        printReceipt(tr.getReceipt());

        System.out.println("Billing done by " + tr.getCashierName());
        System.out.println("------");
    }

    // EFFECTS: prints out the list of items in the receipt
    public void printReceipt(Receipt receipt) {
        System.out.println("Items Purchased:");
        int total = 0;
        System.out.println("Items bought:");
        for (Item i : receipt.getItemsBought()) {
            System.out.format("%1s%15s%10s", i.getName() + "(" + i.getItemCode() + ")", i.getNumber(),
                    i.getPriceOfOne());
            System.out.println();
            total += (i.getPriceOfOne() * i.getNumber());
        }

        System.out.println();

        System.out.println("Coupons applied:");
        for (Coupon i : receipt.getCouponsUsed()) {
            System.out.format("%1s%15s", i.getCouponCode(), i.getValue());
            System.out.println();
            total -= i.getValue();
        }
        System.out.println();
        System.out.println("Total = " + total);
    }

    // EFFECTS: prints out the transaction records of a specific cashier
    private void printOneCashierRecord() {
        System.out.println("Please enter the cashier name:");
        String name = infoIntake.nextLine();
        List<TransactionRecord> records = superMarket.getCashierRecords(name);

        if (records == null) {
            System.out.println("Cashier does not exist");
        } else {
            for (TransactionRecord r : records) {
                printRecord(r);
            }
        }
    }


    // JSON Methods
    // EFFECTS: saves the workroom to file
    private void saveSuperMarket() {
        try {
            jsonWriter.openFileToWrite();
            jsonWriter.writeOnFile(superMarket);
            jsonWriter.closeFile();
            System.out.println("Saved " + superMarket.getSuperMarketName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadSuperMarket() {
        try {
            superMarket = jsonReader.read();
            System.out.println("Loaded " + superMarket.getSuperMarketName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}