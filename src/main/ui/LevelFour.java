package ui;

import model.*;
import ui.commonpages.EmployeeMPage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// this class represents the supervisor page of the supermarket
public class LevelFour extends JFrame {
    // Fields
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 750;
    private SuperMarket superMarket;
    private SuperMarketGUI superMarketGUI;
    private List<Employee> accessibleEmployees;
    private JPanel options;

    // Constructor
    // EFFECTS: constructs the level four page and assigns the respective fields
    public LevelFour(SuperMarket givenSuperMarket, SuperMarketGUI superMarketGUI1) {
        superMarket = givenSuperMarket;
        superMarketGUI = superMarketGUI1;
        accessibleEmployees = superMarket.getEmployeesBelowAccess(4);
        levelFourAccessPage();
    }

    // EFFECTS: Constructs the frame and initializes the level four page
    public void levelFourAccessPage() {
        setLayout(new BorderLayout());
        setSize(new Dimension(WIDTH, HEIGHT));
        createOptions();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: creates the main page with the required buttons
    private void createOptions() {
        options = new JPanel();
        add(options, BorderLayout.CENTER);
        options.setLayout(new GridLayout(5, 1));
        options.setBorder(new EmptyBorder(new Insets(100, 100, 100, 100)));

        addEmployeesButton();

        addInventoryButton();

        JButton transactionsButton = addTransactionsButton();

        addViewCouponsButton(transactionsButton);

        addPasswordChangeButton();

        // Logut Button
        JPanel returnB = new JPanel();
        returnB.setLayout(new GridLayout(1, 1));
        returnB.setBorder(new EmptyBorder(new Insets(50, 800, 50, 50)));
        add(returnB, BorderLayout.PAGE_END);

        addLogoutButton(returnB);
    }

    // EFFECTS: add logout button to the main page
    private void addLogoutButton(JPanel returnB) {
        JButton logout = new JButton("Logout");
        logout.setBackground(new Color(0xFD0303));
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                superMarketGUI.setVisible(true);
                superMarket.setAccess(-1);
                superMarketGUI.saveSuperMarket();
                superMarketGUI.loadSuperMarket();
            }
        });
        returnB.add(logout);
    }

    // EFFECTS: add employeed button to the main page
    private void addEmployeesButton() {
        JButton goToEmployeesButton = new JButton("Employees");
        goToEmployeesButton.setToolTipText("view Employees Working");
        goToEmployeesButton.addActionListener(e -> {
            setVisible(false);
            new EmployeeMPage(superMarket, accessibleEmployees, this);
        });
        options.add(goToEmployeesButton);
    }

    // EFFECTS: add inventory button to the main page
    private void addInventoryButton() {
        JButton goToInventoryButton = new JButton("Inventory");
        goToInventoryButton.setToolTipText("edit an inventory");
        goToInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                openInventoryManagementPage();
            }
        });
        options.add(goToInventoryButton);
    }

    // EFFECTS: add transactions button to the main page
    private JButton addTransactionsButton() {
        JButton transactionsButton = new JButton("Transaction Records");
        transactionsButton.setToolTipText("view all transactions");
        transactionsButton.addActionListener(e -> {
            setVisible(false);
            openTransactionsPage();
        });
        options.add(transactionsButton);
        return transactionsButton;
    }

    // EFFECTS: add view coupons button to the main page
    private void addViewCouponsButton(JButton transactionsButton) {
        JButton viewCouponsButton = new JButton("View Coupons");
        transactionsButton.setToolTipText("View Active Coupons");
        transactionsButton.addActionListener(e -> {
            setVisible(false);
            viewCoupons();
        });
        options.add(viewCouponsButton);
    }

    // EFFECTS:  add password change button to the main page
    private void addPasswordChangeButton() {
        JButton passwordChangeButton = new JButton("Change Password");
        passwordChangeButton.setToolTipText("Change Password");
        passwordChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPasswordChangePage();
            }
        });
        options.add(passwordChangeButton);
    }

    /**
     * ALL OF EMPLOYEE MANAGEMENT PAGE BELOW THIS
     */

    // EFFECTS:  open the employee window and add all buttons
    private void openEmployeeManagementPage() {
        JFrame frame = new JFrame();
        initializeExitWindows(frame);


        JPanel upperRow = new JPanel();
        upperRow.setLayout(new GridLayout(1, 4));
        upperRow.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(upperRow, BorderLayout.NORTH);

        JPanel empOptions = new JPanel();
        empOptions.setLayout(new GridLayout(9, 0));
        empOptions.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(empOptions, BorderLayout.CENTER);

        JPanel lowerRow = new JPanel();
        empOptions.setLayout(new GridLayout(1, 4));
        empOptions.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(lowerRow, BorderLayout.SOUTH);

        // Buttons/Fields/Boxes initializations
        JButton addButton = new JButton("Add new Employee");
        JButton editButton = new JButton("Edit Employee");

        JLabel selectEmployee = new JLabel("Select Employee");

        JComboBox employeesJComboBox = new JComboBox();
        empOptions.add(new JScrollPane(employeesJComboBox));

        // Input Fields
        addTextFieldsToEmployeePage(frame, upperRow, empOptions, lowerRow, addButton, editButton,
                selectEmployee, employeesJComboBox);
    }

    // EFFECTS: add text fields tot he employee page
    private void addTextFieldsToEmployeePage(JFrame frame, JPanel upperRow, JPanel empOptions, JPanel lowerRow,
                                             JButton addButton, JButton editButton, JLabel selectEmployee,
                                             JComboBox employeesJComboBox) {
        JLabel nameL = new JLabel("Name:");
        JTextField name = new JTextField();
        JLabel designationL = new JLabel("Designation:");
        JTextField designation = new JTextField();
        JLabel accessLevelL = new JLabel("Access Level:");
        JTextField accessLevel = new JTextField();

        JButton add = new JButton("Add");
        JButton save = new JButton("Save");


        addButtonsAndInitializeTextEmployeePage(frame, upperRow, empOptions, lowerRow, addButton, editButton,
                selectEmployee, employeesJComboBox, nameL, name, designationL, designation, accessLevelL,
                accessLevel, add, save);
    }

    // EFFECTS: add buttons and text fields to the employee page
    private void addButtonsAndInitializeTextEmployeePage(JFrame frame, JPanel upperRow, JPanel empOptions,
                                                         JPanel lowerRow, JButton addButton, JButton editButton,
                                                         JLabel selectEmployee, JComboBox employeesJComboBox,
                                                         JLabel nameL, JTextField name, JLabel designationL,
                                                         JTextField designation, JLabel accessLevelL,
                                                         JTextField accessLevel, JButton add, JButton save) {
        // Button positions
        addButton.setToolTipText("Add Employee");
        editButton.setToolTipText("Edit Employee");


        for (int e = 0; e < accessibleEmployees.size(); e++) {
            employeesJComboBox.addItem(accessibleEmployees.get(e).getName());
        }
        Employee selected = superMarket.searchEmployee((String) employeesJComboBox.getSelectedItem());

        if (selected != null) {
            name.setText(selected.getName());
            designation.setText(selected.getDesignation());
            accessLevel.setText(Integer.toString(selected.getAccessLevel()));
        }

        add.setToolTipText("Add Employee");
        save.setToolTipText("Save Changes");
        save.setToolTipText("Remove");

        addButtonsAndALToEmployeePage(frame, upperRow, empOptions, lowerRow, addButton, editButton, selectEmployee,
                employeesJComboBox, nameL, name, designationL, designation, accessLevelL, accessLevel, add, save);

        addLowerRowToEmployeePage(frame);
    }

    // EFFECTS: add the page end buttons to the employee page
    private void addLowerRowToEmployeePage(JFrame frame) {
        JPanel returnB = new JPanel();
        returnB.setLayout(new GridLayout(1, 1));
        returnB.setBorder(new EmptyBorder(new Insets(50, 800, 50, 50)));
        frame.add(returnB, BorderLayout.PAGE_END);

        JButton finish = new JButton("Return To Menu");
        finish.setBackground(new Color(0xFD0303));
        finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                setVisible(true);

            }
        });
        returnB.add(finish);
    }

    // EFFECTS: add all the buttons to the employee page
    private void addButtonsAndALToEmployeePage(JFrame frame, JPanel upperRow, JPanel empOptions, JPanel lowerRow,
                                               JButton addButton, JButton editButton, JLabel selectEmployee,
                                               JComboBox employeesJComboBox, JLabel nameL, JTextField name,
                                               JLabel designationL, JTextField designation, JLabel accessLevelL,
                                               JTextField accessLevel, JButton add, JButton save) {
        // Button additions to panel
        addAddButtonsToEmployeeManagement(upperRow, empOptions, lowerRow, addButton, editButton,
                selectEmployee, employeesJComboBox, nameL, name, designationL, designation,
                accessLevelL, accessLevel, add, save);

        setToDefaultEmployeePage(editButton, addButton, employeesJComboBox,
                add, save, name, designation, accessLevel);

        // Action Listeners
        setALAddEmployeeButton(addButton, editButton, employeesJComboBox, name, designation, accessLevel, add, save);

        setALEditButton(addButton, editButton, employeesJComboBox, name, designation, accessLevel, add, save);

        setALComboBox(employeesJComboBox, name, designation, accessLevel);

        setALAddButton(frame, addButton, editButton, employeesJComboBox, name, designation, accessLevel, add, save);

        setALSaveButton(frame, addButton, editButton, employeesJComboBox, name, designation, accessLevel, add, save);
    }

    // EFFECTS: set the action listener for the add employee button
    private void setALAddEmployeeButton(JButton addButton, JButton editButton, JComboBox employeesJComboBox,
                                        JTextField name, JTextField designation, JTextField accessLevel,
                                        JButton add, JButton save) {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setToDefaultEmployeePage(editButton, addButton, employeesJComboBox,
                        add, save, name, designation, accessLevel);
                addButton.setBackground(new Color(0xF8F805));
                add.setEnabled(true);
                name.setEnabled(true);
                designation.setEnabled(true);
                accessLevel.setEnabled(true);
            }
        });
    }

    // EFFECTS: set the action listener for the edit button
    private void setALEditButton(JButton addButton, JButton editButton, JComboBox employeesJComboBox, JTextField name,
                                 JTextField designation, JTextField accessLevel, JButton add, JButton save) {
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setToDefaultEmployeePage(editButton, addButton, employeesJComboBox,
                        add, save, name, designation, accessLevel);
                editButton.setBackground(new Color(0xF8F805));
                employeesJComboBox.setEnabled(true);
                save.setEnabled(true);
                name.setEnabled(true);
                designation.setEnabled(true);
                accessLevel.setEnabled(true);
            }
        });
    }

    // EFFECTS: set the action listener to the employee comboBox
    private void setALComboBox(JComboBox employeesJComboBox, JTextField name, JTextField designation,
                               JTextField accessLevel) {
        employeesJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee e1 = superMarket.searchEmployee((String) employeesJComboBox.getSelectedItem());
                if (e1 != null) {
                    name.setText(e1.getName());
                    designation.setText(e1.getDesignation());
                    accessLevel.setText(Integer.toString(e1.getAccessLevel()));
                }
            }
        });
    }

    // EFFECTS: set action listeners to add button
    private void setALAddButton(JFrame frame, JButton addButton, JButton editButton, JComboBox employeesJComboBox,
                                JTextField name, JTextField designation, JTextField accessLevel,
                                JButton add, JButton save) {
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Employee em = new Employee(name.getText(), designation.getText(),
                            Integer.parseInt(accessLevel.getText()), name.getText());
                    superMarket.getEmployeeList().add(em);
                    JOptionPane.showMessageDialog(frame, "Employee Added!");
                    setToDefaultEmployeePage(editButton, addButton, employeesJComboBox,
                            add, save, name, designation, accessLevel);
                    updateEmployees(employeesJComboBox);
                    JOptionPane.showMessageDialog(frame,"The default password of the employee is their name");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid Entries");
                }
            }
        });
    }

    // EFFECTS: set action listeners to the save button
    private void setALSaveButton(JFrame frame, JButton addButton, JButton editButton, JComboBox employeesJComboBox,
                                 JTextField name, JTextField designation, JTextField accessLevel, JButton add,
                                 JButton save) {
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee returned = superMarket.searchEmployee(((String) employeesJComboBox.getSelectedItem()));

                try {
                    returned.setName(name.getText());
                    returned.setDesignation(designation.getText());
                    returned.setAccessLevel(Integer.parseInt(accessLevel.getText()));
                    JOptionPane.showMessageDialog(frame, "Changes Saved");
                    setToDefaultEmployeePage(editButton, addButton, employeesJComboBox,
                            add, save, name, designation, accessLevel);
                    updateEmployees(employeesJComboBox);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid entries");
                }
            }
        });
    }

    // EFFECTS: add the upper row buttons to the employee page
    private void addAddButtonsToEmployeeManagement(JPanel upperRow, JPanel empOptions, JPanel lowerRow,
                                                   JButton addButton, JButton editButton, JLabel selectEmployee,
                                                   JComboBox employeesJComboBox, JLabel nameL, JTextField name,
                                                   JLabel designationL, JTextField designation, JLabel accessLevelL,
                                                   JTextField accessLevel, JButton add, JButton save) {
        upperRow.add(addButton);
        upperRow.add(editButton);

        empOptions.add(selectEmployee);
        empOptions.add(employeesJComboBox);

        empOptions.add(nameL);
        empOptions.add(name);
        empOptions.add(designationL);
        empOptions.add(designation);
        empOptions.add(accessLevelL);
        empOptions.add(accessLevel);

        lowerRow.add(add);
        lowerRow.add(save);
    }

    // EFFECTS: set the employee page to default
    private void setToDefaultEmployeePage(JButton editButton, JButton addButton, JComboBox employeesJComboBox,
                                          JButton add, JButton save, JTextField name, JTextField designation,
                                          JTextField accessLevel) {
        editButton.setBackground(null);
        addButton.setBackground(null);
        employeesJComboBox.setEnabled(false);
        add.setEnabled(false);
        save.setEnabled(false);
        name.setText("");
        designation.setText("");
        accessLevel.setText("");
        name.setEnabled(false);
        designation.setEnabled(false);
        accessLevel.setEnabled(false);
    }

    // EFFECTS: update the employees in the combobox
    private void updateEmployees(JComboBox box) {
        box.removeAllItems();
        for (int e = 0; e < accessibleEmployees.size(); e++) {
            box.addItem(accessibleEmployees.get(e).getName());
        }
    }

    /**
     * // ALL OF INVENTORY MANAGEMENT PAGE BELOW THIS
     * // Open Inventory Management page
     */

    // EFFECTS: make the inventory page
    private void openInventoryManagementPage() {
        JFrame frame = new JFrame();
        initializeExitWindows(frame);

        JPanel inventoryOptions = new JPanel();
        inventoryOptions.setLayout(new GridLayout(12, 1));
        inventoryOptions.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(inventoryOptions, BorderLayout.CENTER);

        JPanel upperOptions = new JPanel();
        upperOptions.setLayout(new GridLayout(1, 4));
        upperOptions.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(upperOptions, BorderLayout.NORTH);

        JPanel lowerOptions = new JPanel();
        lowerOptions.setLayout(new GridLayout(1, 4));
        lowerOptions.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(lowerOptions, BorderLayout.SOUTH);

        // Buttons, Fields and Boxes
        addAllButtonsInventory(frame, inventoryOptions, upperOptions, lowerOptions);
    }

    // EFFECTS: add all the required fields and buttons to the inventory page
    private void addAllButtonsInventory(JFrame frame, JPanel inventoryOptions,
                                        JPanel upperOptions, JPanel lowerOptions) {
        JButton viewInventoryButton = new JButton("View Inventory");
        JButton addItemButton = new JButton("Add New Item");
        JButton removeItemButton = new JButton("Remove Item");
        JButton editItemButton = new JButton("Edit Item");

        JLabel inventoryComboBoxL = new JLabel("Select Inventory");
        JComboBox inventoryComboBox = new JComboBox();
        updateInventoryComboBox(inventoryComboBox);
        JLabel itemsComboBoxL = new JLabel("Select Item");
        JComboBox itemsComboBox = new JComboBox();

        JLabel itemNameL = new JLabel("Item Name:");
        JTextField itemName = new JTextField();
        JLabel itemCodeL = new JLabel("Item Code:");
        JTextField itemCode = new JTextField();
        JLabel numberL = new JLabel("Number In Stock:");
        JTextField number = new JTextField();
        JLabel priceOfOneL = new JLabel("Price Of One:");
        JTextField priceOfOne = new JTextField();


        lowerRowAndInitialSetupOfButtonsInventory(frame, inventoryOptions, upperOptions,
                lowerOptions, viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                inventoryComboBoxL, inventoryComboBox, itemsComboBoxL, itemsComboBox, itemNameL,
                itemName, itemCodeL, itemCode, numberL, number, priceOfOneL, priceOfOne);
    }

    // EFFECTS: setup the lower row of the inventory page
    private void lowerRowAndInitialSetupOfButtonsInventory(JFrame frame, JPanel inventoryOptions,
                                                           JPanel upperOptions, JPanel lowerOptions,
                                                           JButton viewInventoryButton, JButton addItemButton,
                                                           JButton removeItemButton, JButton editItemButton,
                                                           JLabel inventoryComboBoxL, JComboBox inventoryComboBox,
                                                           JLabel itemsComboBoxL, JComboBox itemsComboBox,
                                                           JLabel itemNameL, JTextField itemName, JLabel itemCodeL,
                                                           JTextField itemCode, JLabel numberL, JTextField number,
                                                           JLabel priceOfOneL, JTextField priceOfOne) {
        JButton viewButton = new JButton("View");
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        JButton saveButton = new JButton("Save");


        addAllButtonsToInventoryPage(inventoryOptions, upperOptions, lowerOptions, viewInventoryButton,
                addItemButton, removeItemButton, editItemButton, inventoryComboBoxL, inventoryComboBox,
                itemsComboBoxL, itemsComboBox, itemNameL, itemName, itemCodeL, itemCode, numberL, number,
                priceOfOneL, priceOfOne, viewButton, addButton, removeButton, saveButton);

        setToDefaultInventoryPage(viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton,
                removeButton, saveButton);

        setActionListenersForInventoryPage(frame, lowerOptions, viewInventoryButton, addItemButton, removeItemButton,
                editItemButton, inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton,
                addButton, removeButton, saveButton);
    }

    // EFFECTS: set the inventory page to default
    private void addAllButtonsToInventoryPage(JPanel inventoryOptions, JPanel upperOptions, JPanel lowerOptions,
                                              JButton viewInventoryButton, JButton addItemButton,
                                              JButton removeItemButton, JButton editItemButton,
                                              JLabel inventoryComboBoxL, JComboBox inventoryComboBox,
                                              JLabel itemsComboBoxL, JComboBox itemsComboBox, JLabel itemNameL,
                                              JTextField itemName, JLabel itemCodeL, JTextField itemCode,
                                              JLabel numberL, JTextField number, JLabel priceOfOneL,
                                              JTextField priceOfOne, JButton viewButton, JButton addButton,
                                              JButton removeButton, JButton saveButton) {
        upperOptions.add(viewInventoryButton);
        upperOptions.add(addItemButton);
        upperOptions.add(removeItemButton);
        upperOptions.add(editItemButton);
        inventoryOptions.add(inventoryComboBoxL);
        inventoryOptions.add(inventoryComboBox);
        inventoryOptions.add(itemsComboBoxL);
        inventoryOptions.add(itemsComboBox);
        inventoryOptions.add(itemNameL);
        inventoryOptions.add(itemName);
        inventoryOptions.add(itemCodeL);
        inventoryOptions.add(itemCode);
        inventoryOptions.add(numberL);
        inventoryOptions.add(number);
        inventoryOptions.add(priceOfOneL);
        inventoryOptions.add(priceOfOne);
        lowerOptions.add(viewButton);
        lowerOptions.add(addButton);
        lowerOptions.add(removeButton);
        lowerOptions.add(saveButton);
    }

    // EFFECTS: set action listeners for inventory page
    private void setActionListenersForInventoryPage(JFrame frame, JPanel lowerOptions, JButton viewInventoryButton,
                                                    JButton addItemButton, JButton removeItemButton,
                                                    JButton editItemButton, JComboBox inventoryComboBox,
                                                    JComboBox itemsComboBox, JTextField itemName, JTextField itemCode,
                                                    JTextField number, JTextField priceOfOne, JButton viewButton,
                                                    JButton addButton, JButton removeButton, JButton saveButton) {
        // ActionListeners for the  buttons
        setALForViewInventoryButton(viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton,
                addButton, removeButton, saveButton);

        setALForAddItemButton(viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton,
                addButton, removeButton, saveButton);

        setALForRemoveItemButton(viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton,
                addButton, removeButton, saveButton);

        setALForEditItemButton(viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton,
                addButton, removeButton, saveButton);

        setALForInventoryComboBox(inventoryComboBox, itemsComboBox);

        setALForItemsComboBox(inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne);

        setALForLowerRowInventory(frame, viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton,
                removeButton, saveButton);

        setUpFinishButtonForInventoryPage(frame, lowerOptions);
    }

    // EFFECTS: set action listener for lower page buttons
    private void setALForLowerRowInventory(JFrame frame, JButton viewInventoryButton, JButton addItemButton,
                                           JButton removeItemButton, JButton editItemButton,
                                           JComboBox inventoryComboBox, JComboBox itemsComboBox,
                                           JTextField itemName, JTextField itemCode, JTextField number,
                                           JTextField priceOfOne, JButton viewButton, JButton addButton,
                                           JButton removeButton, JButton saveButton) {
        setALForViewButton(viewInventoryButton, addItemButton, removeItemButton, editItemButton, inventoryComboBox,
                itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton, removeButton, saveButton);

        setALForAddButton(frame, viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton,
                addButton, removeButton, saveButton);

        setALForRemoveButtonInventoryPage(viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton,
                removeButton, saveButton);

        setUpALForSaveButton(frame, viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton,
                addButton, removeButton, saveButton);
    }

    // EFFECTS: set action listener for remove button
    private void setALForRemoveButtonInventoryPage(JButton viewInventoryButton, JButton addItemButton,
                                                   JButton removeItemButton, JButton editItemButton,
                                                   JComboBox inventoryComboBox, JComboBox itemsComboBox,
                                                   JTextField itemName, JTextField itemCode, JTextField number,
                                                   JTextField priceOfOne, JButton viewButton, JButton addButton,
                                                   JButton removeButton, JButton saveButton) {
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inventoryName = (String) inventoryComboBox.getSelectedItem();
                Inventory inventory = superMarket.getNeededInventory(inventoryName);
                inventory.getItems().remove(itemsComboBox.getSelectedIndex());
                setToDefaultInventoryPage(viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                        inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton,
                        removeButton, saveButton);
            }
        });
    }

    // EFFECTS: set action listener for add button
    private void setALForAddButton(JFrame frame, JButton viewInventoryButton, JButton addItemButton,
                                   JButton removeItemButton, JButton editItemButton, JComboBox inventoryComboBox,
                                   JComboBox itemsComboBox, JTextField itemName, JTextField itemCode, JTextField number,
                                   JTextField priceOfOne, JButton viewButton, JButton addButton, JButton removeButton,
                                   JButton saveButton) {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String inventoryName = (String) inventoryComboBox.getSelectedItem();
                    Inventory inventory = superMarket.getNeededInventory(inventoryName);
                    int code = Integer.parseInt(itemCode.getText());
                    int num = Integer.parseInt(number.getText());
                    int price = Integer.parseInt(priceOfOne.getText());

                    if (superMarket.giveItemByCode(code) != null) {
                        inventory.getItems().add(new Item(itemName.getText(), code, num, price));
                    } else {
                        JOptionPane.showMessageDialog(frame, "Item with that code already exists");
                    }
                    setToDefaultInventoryPage(viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                            inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton,
                            addButton, removeButton, saveButton);
                    JOptionPane.showMessageDialog(frame, "Item Added!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid Entries");
                }
            }
        });
    }

    // EFFECTS: set action listener for view button
    private void setALForViewButton(JButton viewInventoryButton, JButton addItemButton, JButton removeItemButton,
                                    JButton editItemButton, JComboBox inventoryComboBox, JComboBox itemsComboBox,
                                    JTextField itemName, JTextField itemCode, JTextField number, JTextField priceOfOne,
                                    JButton viewButton, JButton addButton, JButton removeButton, JButton saveButton) {
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inventoryName = (String) inventoryComboBox.getSelectedItem();
                Inventory inventory = superMarket.getNeededInventory(inventoryName);
                viewInventory(inventory);
                setToDefaultInventoryPage(viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                        inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton,
                        removeButton, saveButton);
            }
        });
    }

    // EFFECTS: set action listener for items comboBox
    private void setALForItemsComboBox(JComboBox inventoryComboBox, JComboBox itemsComboBox, JTextField itemName,
                                       JTextField itemCode, JTextField number, JTextField priceOfOne) {
        itemsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inventoryName = (String) inventoryComboBox.getSelectedItem();
                Inventory inventory = superMarket.getNeededInventory(inventoryName);
                Item selectedItem = null;
                if (itemsComboBox.getSelectedIndex() >= 0) {
                    selectedItem = inventory.getItems().get(itemsComboBox.getSelectedIndex());
                }
                if (selectedItem != null) {
                    itemName.setText(selectedItem.getName());
                    itemCode.setText(Integer.toString(selectedItem.getItemCode()));
                    number.setText(Integer.toString(selectedItem.getNumber()));
                    priceOfOne.setText(Integer.toString(selectedItem.getPriceOfOne()));
                }
            }
        });
    }

    // EFFECTS: set action listener for inventory comboBox
    private void setALForInventoryComboBox(JComboBox inventoryComboBox, JComboBox itemsComboBox) {
        inventoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inventoryName = (String) inventoryComboBox.getSelectedItem();
                Inventory inventory = superMarket.getNeededInventory(inventoryName);
                updateItemsComboBox(itemsComboBox, inventory);
            }
        });
    }

    // EFFECTS: set action listener for edit item button
    private void setALForEditItemButton(JButton viewInventoryButton, JButton addItemButton, JButton removeItemButton,
                                        JButton editItemButton, JComboBox inventoryComboBox, JComboBox itemsComboBox,
                                        JTextField itemName, JTextField itemCode, JTextField number,
                                        JTextField priceOfOne, JButton viewButton, JButton addButton,
                                        JButton removeButton, JButton saveButton) {
        editItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setToDefaultInventoryPage(viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                        inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton,
                        removeButton, saveButton);
                editItemButton.setBackground(new Color(0xF8F805));
                inventoryComboBox.setEnabled(true);
                itemsComboBox.setEnabled(true);
                itemName.setEnabled(true);
                itemCode.setEnabled(true);
                number.setEnabled(true);
                priceOfOne.setEnabled(true);
                saveButton.setEnabled(true);
            }
        });
    }

    // EFFECTS: set action listener for remove item button
    private void setALForRemoveItemButton(JButton viewInventoryButton, JButton addItemButton, JButton removeItemButton,
                                          JButton editItemButton, JComboBox inventoryComboBox, JComboBox itemsComboBox,
                                          JTextField itemName, JTextField itemCode, JTextField number,
                                          JTextField priceOfOne, JButton viewButton, JButton addButton,
                                          JButton removeButton, JButton saveButton) {
        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setToDefaultInventoryPage(viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                        inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton,
                        removeButton, saveButton);
                removeItemButton.setBackground(new Color(0xF8F805));
                inventoryComboBox.setEnabled(true);
                itemsComboBox.setEnabled(true);
                removeButton.setEnabled(true);
            }
        });
    }

    // EFFECTS: set aaction listener for add item button
    private void setALForAddItemButton(JButton viewInventoryButton, JButton addItemButton, JButton removeItemButton,
                                       JButton editItemButton, JComboBox inventoryComboBox, JComboBox itemsComboBox,
                                       JTextField itemName, JTextField itemCode, JTextField number,
                                       JTextField priceOfOne, JButton viewButton, JButton addButton,
                                       JButton removeButton, JButton saveButton) {
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setToDefaultInventoryPage(viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                        inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton,
                        removeButton, saveButton);
                addItemButton.setBackground(new Color(0xF8F805));
                inventoryComboBox.setEnabled(true);
                itemName.setEnabled(true);
                itemCode.setEnabled(true);
                number.setEnabled(true);
                priceOfOne.setEnabled(true);
                addButton.setEnabled(true);
            }
        });
    }

    // EFFECTS: set action listener for view button
    private void setALForViewInventoryButton(JButton viewInventoryButton, JButton addItemButton,
                                             JButton removeItemButton, JButton editItemButton,
                                             JComboBox inventoryComboBox, JComboBox itemsComboBox,
                                             JTextField itemName, JTextField itemCode, JTextField number,
                                             JTextField priceOfOne, JButton viewButton, JButton addButton,
                                             JButton removeButton, JButton saveButton) {
        viewInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setToDefaultInventoryPage(viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                        inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton,
                        removeButton, saveButton);
                viewInventoryButton.setBackground(new Color(0xF8F805));
                inventoryComboBox.setEnabled(true);
                viewButton.setEnabled(true);
            }
        });
    }

    // EFFECTS: sets action listener for save button
    private void setUpALForSaveButton(JFrame frame, JButton viewInventoryButton,
                                      JButton addItemButton, JButton removeItemButton, JButton editItemButton,
                                      JComboBox inventoryComboBox, JComboBox itemsComboBox, JTextField itemName,
                                      JTextField itemCode, JTextField number, JTextField priceOfOne,
                                      JButton viewButton, JButton addButton, JButton removeButton,
                                      JButton saveButton) {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String inventoryName = (String) inventoryComboBox.getSelectedItem();
                    Inventory inventory = superMarket.getNeededInventory(inventoryName);
                    Item selectedItem = inventory.getItems().get(itemsComboBox.getSelectedIndex());
                    int code = Integer.parseInt(itemCode.getText());
                    int num = Integer.parseInt(number.getText());
                    int price = Integer.parseInt(priceOfOne.getText());

                    if (superMarket.giveItemByCode(code) != null) {
                        JOptionPane.showMessageDialog(frame, "Item with the same code already exists");
                    } else {
                        makeItem(selectedItem, itemName.getText(), code, num, price, viewInventoryButton,
                                addItemButton, removeItemButton, editItemButton, inventoryComboBox,
                                itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton,
                                addButton, removeButton, saveButton, frame);
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid Entries");
                }
            }
        });
    }

    // EFFECTS: makes an item using the given information in the text fields
    private void makeItem(Item selectedItem, String name, int code, int num, int price,
                          JButton viewInventoryButton, JButton addItemButton, JButton removeItemButton,
                          JButton editItemButton, JComboBox inventoryComboBox, JComboBox itemsComboBox,
                          JTextField itemName, JTextField itemCode, JTextField number, JTextField priceOfOne,
                          JButton viewButton, JButton addButton, JButton removeButton,
                          JButton saveButton, JFrame frame) {
        selectedItem.setName(name);
        selectedItem.setItemCode(code);
        selectedItem.setNumber(num);
        selectedItem.setPriceOfOne(price);
        setToDefaultInventoryPage(viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton,
                addButton, removeButton, saveButton);
        JOptionPane.showMessageDialog(frame, "Item Added!");
    }

    // EFFECTS: sets the finish button for the inventory page
    private void setUpFinishButtonForInventoryPage(JFrame frame, JPanel lowerOptions) {
        // Return to main menu page
        JButton finish = new JButton("Return To Menu");
        finish.setBackground(new Color(0xFD0303));
        lowerOptions.add(finish);
        finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                setVisible(true);

            }
        });

        frame.setVisible(true);
        frame.revalidate();
    }

    // EFFECTS: sets the inventory page to default
    private void setToDefaultInventoryPage(JButton viewInventoryButton, JButton addItemButton,
                                           JButton removeItemButton, JButton editItemButton,
                                           JComboBox inventoryComboBox, JComboBox itemsComboBox, JTextField itemName,
                                           JTextField itemCode, JTextField number, JTextField priceOfOne,
                                           JButton viewButton, JButton addButton, JButton removeButton,
                                           JButton saveButton) {
        viewInventoryButton.setBackground(null);
        addItemButton.setBackground(null);
        removeItemButton.setBackground(null);
        editItemButton.setBackground(null);
        inventoryComboBox.setEnabled(false);
        itemsComboBox.setEnabled(false);
        itemsComboBox.removeAllItems();
        itemName.setEnabled(false);
        itemName.setText("");
        itemCode.setEnabled(false);
        itemCode.setText("");
        number.setEnabled(false);
        number.setText("");
        priceOfOne.setEnabled(false);
        priceOfOne.setText("");
        viewButton.setEnabled(false);
        addButton.setEnabled(false);
        removeButton.setEnabled(false);
        saveButton.setEnabled(false);
    }

    // EFFECTS: updates the inventory comboBox
    private void updateInventoryComboBox(JComboBox inventoryComboBox) {
        inventoryComboBox.removeAll();
        for (Inventory i : superMarket.getInventoriesList()) {
            inventoryComboBox.addItem(i.getInventoryName());
        }
    }

    // EFFECTS: updates the items in the comboBox
    private void updateItemsComboBox(JComboBox itemsComboBox, Inventory inventory) {
        itemsComboBox.removeAllItems();
        for (Item i : inventory.getItems()) {
            itemsComboBox.addItem(i.getName());
        }
    }

    // EFFECTS: displays the inventory page
    private void viewInventory(Inventory i) {
        JFrame frame = new JFrame();
        initializeDisposableWindow(frame);

        JPanel viewOptions = new JPanel();
        viewOptions.setLayout(new GridLayout(0, 1));
        viewOptions.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(viewOptions, BorderLayout.CENTER);

        JPanel upperRow = new JPanel();
        upperRow.setLayout(new GridLayout(0, 1));
        upperRow.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(upperRow, BorderLayout.NORTH);

        makeViewInventoryPage(i, viewOptions, upperRow);


        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new GridLayout(0, 1));
        bottomRow.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(bottomRow, BorderLayout.SOUTH);

        // Close Button
        JButton close = new JButton("Close");
        setCloseButtonForJustDisposePages(frame, close);

        bottomRow.add(close);

        frame.setVisible(true);
        frame.revalidate();
    }

    // EFFECTS: adds action listener to the close button to dispose the given frame
    private void setCloseButtonForJustDisposePages(JFrame frame, JButton close) {
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    // EFFECTS: makes the table for view inventory page
    private void makeViewInventoryPage(Inventory i, JPanel viewOptions, JPanel upperRow) {
        JLabel inventoryName = new JLabel(i.getInventoryName());
        upperRow.add(inventoryName);


        JTable table = new JTable(i.getItems().size() + 1, 4);
        JScrollPane scrollpane = new JScrollPane(table);


        table.setRowHeight(30);

        table.setValueAt("ITEM NAME", 0, 0);
        table.setValueAt("ITEM CODE", 0, 1);
        table.setValueAt("NUMBER IN STOCK", 0, 2);
        table.setValueAt("PRICE PER UNIT", 0, 3);
        int count = 1;
        for (Item c : i.getItems()) {
            table.setValueAt(c.getName(), count, 0);
            table.setValueAt(c.getItemCode(), count, 1);
            table.setValueAt(c.getNumber(), count, 2);
            table.setValueAt(c.getPriceOfOne(), count, 3);

            count++;
        }

        viewOptions.add(table);
    }

    /**
     * // ALL OF TRANSACTIONS PAGE BELOW THIS
     * // Open Transaction page
     */

    // EFFECTS: creates the transactions page
    private void openTransactionsPage() {
        JFrame frame = new JFrame();
        initializeExitWindows(frame);

        JPanel start = new JPanel();
        start.setLayout(new GridLayout(0, 1));
        start.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(start, BorderLayout.NORTH);

        JLabel transactionsL = new JLabel("Transactions:");
        start.add(transactionsL);

        setUpTransactionsTableMid(frame);

        // Loguot
        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new GridLayout(0, 1));
        bottomRow.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(bottomRow, BorderLayout.PAGE_END);

        // Close Button
        JButton close = new JButton("Close");
        addCloseButtonTransactions(frame, close);
        bottomRow.add(close);

        frame.setVisible(true);
        frame.revalidate();
    }

    // EFFECTS: adds close button to the transactions page
    private void addCloseButtonTransactions(JFrame frame, JButton close) {
        close.setBounds(new Rectangle(40, 30));
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                setVisible(true);
            }
        });
    }

    // EFFECTS: sets the middle area of the transactions page
    private void setUpTransactionsTableMid(JFrame frame) {
        List<TransactionRecord> records = superMarket.getTransactionsList();
        JPanel oldEmp = new JPanel();
        oldEmp.setLayout(new GridLayout(0, 1));
        oldEmp.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));

        JTable table = new JTable(records.size() + 1, 3);

        oldEmp.add(new JScrollPane(table));
        frame.add(oldEmp, BorderLayout.CENTER);
        table.setRowHeight(30);

        makeTransactionsTable(records, table);
    }

    // EFFECTS: makes the transactions table
    private void makeTransactionsTable(List<TransactionRecord> records, JTable table) {
        table.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                System.out.println(table.getValueAt(row, col).toString());
                viewReceipt(superMarket.getTransactionsList().get(row - 1).getReceipt());
            }

        });

        int count = 1;
        table.setValueAt("SNo.", 0, 0);
        table.setValueAt("Billed By", 0, 1);
        table.setValueAt("Receipt", 0, 2);

        for (TransactionRecord t : records) {
            table.setValueAt(count, count, 0);
            table.setValueAt(t.getCashierName(), count, 1);
            table.setValueAt("Click to view Receipt", count, 2);
        }
    }

    // VIEW RECEIPT PAGE, Used in multiple classes
    // EFFECTS: displays the receipt given in terms of tables and labels
    private void viewReceipt(Receipt r) {
        JFrame frame = new JFrame();
        initializeDisposableWindow(frame);

        List<Item> items = r.getItemsBought();
        List<Coupon> coupons = r.getCouponsUsed();

        JPanel start = new JPanel();
        start.setLayout(new GridLayout(0, 1));
        start.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(start, BorderLayout.NORTH);

        JLabel receiptL = new JLabel("RECEIPT");
        start.add(receiptL);
        receiptL.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel receipt1 = new JPanel();
        receipt1.setLayout(new GridLayout(0, 1));
        receipt1.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));

        int total = setUpReceiptGetTotal(frame, items, coupons, receipt1);

        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new GridLayout(2, 2));
        bottomRow.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(bottomRow, BorderLayout.PAGE_END);

        addTotalLabelToReceipt(total, bottomRow);

        // Close Button
        addCloseButton(frame, bottomRow);

        frame.setVisible(true);
        frame.revalidate();
    }

    // EFFECTS: adds close button to the receipt window
    private void addCloseButton(JFrame frame, JPanel bottomRow) {
        JButton close = new JButton("Close");
        setUpCloseButton(frame, close);
        bottomRow.add(close);
    }

    // EFFECTS: sets up the receipt
    private int setUpReceiptGetTotal(JFrame frame, List<Item> items, List<Coupon> coupons, JPanel receipt1) {
        JLabel itemsL = new JLabel("Items Bought:");
        receipt1.add(itemsL);
        itemsL.setHorizontalAlignment(SwingConstants.LEFT);

        JTable itemsTable = new JTable(items.size() + 1, 3);
        frame.add(new JScrollPane(itemsTable));
        receipt1.add(itemsTable);
        itemsTable.setRowHeight(30);

        JLabel couponsL = new JLabel("Coupons Used:");
        receipt1.add(itemsL);
        couponsL.setHorizontalAlignment(SwingConstants.LEFT);

        JTable couponsTable = new JTable(coupons.size() + 1, 2);
        frame.add(new JScrollPane(couponsTable));
        receipt1.add(couponsTable);

        couponsTable.setRowHeight(30);

        frame.add(receipt1, BorderLayout.CENTER);

        int total = makeReceiptTables(items, coupons, itemsTable, couponsTable);
        return total;
    }

    // EFFECTS: makes the total lable for the receipt
    private void addTotalLabelToReceipt(int total, JPanel bottomRow) {
        JLabel totalL = new JLabel("TOTAL:");
        bottomRow.add(totalL);
        JLabel totalN = new JLabel();
        bottomRow.add(totalN);

        if (total < 0) {
            totalN.setText("0");
        } else {
            totalN.setText(Integer.toString(total));
        }
    }

    // EFFECTS: makes the coupons and items table
    private int makeReceiptTables(List<Item> items, List<Coupon> coupons, JTable itemsTable, JTable couponsTable) {
        int total = 0;

        itemsTable.setValueAt("ITEM NAME", 0, 0);
        itemsTable.setValueAt("QUANTITY", 0, 1);
        itemsTable.setValueAt("PRICE", 0, 2);
        int count = 1;
        for (Item i : items) {
            itemsTable.setValueAt(i.getName() + "(" + i.getItemCode() + ")", count, 0);
            itemsTable.setValueAt(i.getNumber(), count, 1);
            itemsTable.setValueAt(i.getNumber() * i.getPriceOfOne(), count, 2);

            total += i.getNumber() * i.getPriceOfOne();
            count++;
        }

        itemsTable.setValueAt("COUPON CODE", 0, 0);
        itemsTable.setValueAt("COUPON VALUE", 0, 1);
        int num = 1;
        for (Coupon c : coupons) {
            couponsTable.setValueAt(c.getCouponCode(), num, 0);
            couponsTable.setValueAt(c.getValue(), num, 1);

            total -= c.getValue();
            num++;
        }
        return total;
    }

    /**
     * All of view Coupons page present below this
     */
    // EFFECTS: display the all coupons page
    private void viewCoupons() {
        JFrame frame = new JFrame();
        initializeDisposableWindow(frame);

        JPanel viewOptions = new JPanel();
        viewOptions.setLayout(new GridLayout(0, 1));
        viewOptions.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(viewOptions, BorderLayout.CENTER);

        JTable table = new JTable(superMarket.getCouponsList().size() + 1, 2);
        makeCouponsTable(table);

        viewOptions.add(table);

        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new GridLayout(0, 1));
        bottomRow.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(bottomRow, BorderLayout.PAGE_END);

        // Close Button
        addCloseButtonForCoupons(frame, bottomRow);

        frame.setVisible(true);
        frame.revalidate();
    }

    // EFFECTS: adds the close button to the coupons table
    private void addCloseButtonForCoupons(JFrame frame, JPanel bottomRow) {
        JButton close = new JButton("Close");
        close.setBounds(new Rectangle(40, 30));
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                setVisible(true);
            }
        });
        bottomRow.add(close);
    }

    // EFFECTS: makes the table for coupons
    private void makeCouponsTable(JTable table) {
        JScrollPane scrollpane = new JScrollPane(table);


        table.setRowHeight(30);

        table.setValueAt("COUPON CODE", 0, 0);
        table.setValueAt("COUPON VALUE", 0, 1);
        int count = 1;
        for (Coupon c : superMarket.getCouponsList()) {
            table.setValueAt(c.getCouponCode(), count, 0);
            table.setValueAt(c.getValue(), count, 1);

            count++;
        }
    }


    /**
     * // ALL OF SET PASSWORD PAGE IS BELOW THIS
     * // Set password page
     */

    // EFFECTS: opens the password change page and initializes the buttons and fields
    private void openPasswordChangePage() {
        Employee e = superMarket.searchEmployee(superMarket.getEmployeeLoggedIn());

        JFrame frame = new JFrame();
        initializeDisposableWindow(frame);

        JPanel start = new JPanel();
        start.setLayout(new GridLayout(0, 1));
        start.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(start, BorderLayout.NORTH);

        JLabel oldPasswordL = new JLabel("Old Password:");
        start.add(oldPasswordL);
        JTextField oldPassword = new JTextField();
        start.add(oldPassword);

        JLabel newPasswordL = new JLabel("New Password:");
        start.add(newPasswordL);
        JTextField newPassword = new JTextField();
        start.add(newPassword);

        JLabel confirmationL = new JLabel("Reconfirm Password:");
        start.add(confirmationL);
        JTextField confirmation = new JTextField();
        start.add(confirmation);

        setToolTipsForPasswordFields(oldPassword, newPassword, confirmation);

        addEndButtonsToPasswordChange(e, frame, oldPassword, newPassword, confirmation);

        frame.setVisible(true);
        frame.revalidate();
    }

    // EFFECTS: sets too; tips to the passeord page text fields
    private void setToolTipsForPasswordFields(JTextField oldPassword, JTextField newPassword, JTextField confirmation) {
        oldPassword.setToolTipText("enter old password");
        newPassword.setToolTipText("enter new password");
        confirmation.setToolTipText("passwords should match");
    }

    // EFFECTS: adds the page end button to the password change page
    private void addEndButtonsToPasswordChange(Employee e, JFrame frame, JTextField oldPassword,
                                               JTextField newPassword, JTextField confirmation) {
        JPanel end = new JPanel();
        end.setLayout(new GridLayout(1, 2));
        end.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(end, BorderLayout.PAGE_END);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setToolTipText("Cancel process");
        end.add(cancelButton);

        JButton submitButton = new JButton("Submit");
        submitButton.setToolTipText("Submit");
        end.add(submitButton);

        addActionListenersToPasswordChange(e, frame, oldPassword,
                newPassword, confirmation, cancelButton, submitButton);
    }

    // EFFECTS: adds action listeners to the password chang buttons in the page
    private void addActionListenersToPasswordChange(Employee e, JFrame frame, JTextField oldPassword,
                                                    JTextField newPassword, JTextField confirmation,
                                                    JButton cancelButton, JButton submitButton) {
        setUpCloseButton(frame, cancelButton);

        submitButton.addActionListener(new ActionListener() {
            Employee e1 = e;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (oldPassword.getText().equals(e1.getPassword())) {
                    if (newPassword.getText().equals(confirmation.getText())) {
                        e1.setPasscode(newPassword.getText());
                        JOptionPane.showMessageDialog(frame, "Password successfully changed");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Passwords don't match");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "incorrect old password");
                }
                disposePage(frame);
            }
        });
    }

    // EFFECTS: disposes the current frame and sets the class frame visibility to true
    private void disposePage(JFrame frame) {
        frame.dispose();
        setVisible(true);
    }

    // EFFECTS: initializes the windows with the exit operation as dispose
    private void initializeDisposableWindow(JFrame frame) {
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // EFFECTS: initializes the windows with the exit operation as exit
    private void initializeExitWindows(JFrame frame) {
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // EFFECTS: sets the close button with the operation of disposing the given frame
    private void setUpCloseButton(JFrame frame, JButton close) {
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disposePage(frame);
            }
        });
    }
}