package ui;

import model.*;
import ui.commonpages.EmployeeMPage;
import ui.commonpages.InventoryPage;
import ui.commonpages.PasswordChangePage;
import ui.commonpages.TransactionsPage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// this class represents the admin page of the supermarket
public class LevelFive extends JFrame {
    // Fields
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 750;
    private SuperMarket superMarket;
    private SuperMarketGUI superMarketGUI;
    private List<Employee> accessibleEmployees;
    private JPanel options;

    // Constructor
    // EFFECTS: constructs the level five page and assigns the respective fields
    public LevelFive(SuperMarket givenSuperMarket, SuperMarketGUI superMarketGui) {
        superMarketGUI = superMarketGui;
        superMarket = givenSuperMarket;
        accessibleEmployees = superMarket.getEmployeesBelowAccess(5);
        adminAccessPage();
    }

    // EFFECTS: Constructs the frame and initializes the level five page
    public void adminAccessPage() {
        setLayout(new BorderLayout());
        setSize(new Dimension(WIDTH, HEIGHT));
        createOptionsPage();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Options page, or main menu page
    // EFFECTS: creates the main page of level five
    private void createOptionsPage() {
        options = new JPanel();
        add(options, BorderLayout.CENTER);
        options.setLayout(new GridLayout(5, 1));
        options.setBorder(new EmptyBorder(new Insets(100, 100, 100, 100)));

        addEmployeesButton();

        addOldStaffButton();

        addTransactionsButton();

        addInventoryButton();

        addCouponsButton();

        addPasswordChangeButton();

        addSaveButton();

        addLoadButton();

        // logout button
        addLogoutButton();

        setVisible(true);
        revalidate();
    }

    // EFFECTS: adds the save button to options panel
    private void addSaveButton() {
        JButton saveButton = new JButton("Save Session");
        saveButton.setToolTipText("Save Session");
        saveButton.addActionListener(e -> {
            superMarketGUI.saveSuperMarket();
            superMarket = superMarketGUI.getSuperMarket();
            JOptionPane.showMessageDialog(this, "Session Saved");
        });
        options.add(saveButton);
    }

    // EFFECTS: adds the load button to options panel
    private void addLoadButton() {
        JButton loadButton = new JButton("Load Previous Session");
        loadButton.setToolTipText("load Previous Session");
        loadButton.addActionListener(e -> {
            superMarketGUI.loadSuperMarket();
            superMarket = superMarketGUI.getSuperMarket();
            JOptionPane.showMessageDialog(this, "Session Loaded");
        });
        options.add(loadButton);
    }

    // EFFECTS: adds the employees button to options panel
    private void addEmployeesButton() {
        JButton manageStaffButton = new JButton("Current Employees");
        manageStaffButton.setToolTipText("edit or view staff");
        manageStaffButton.addActionListener(e -> {
            setVisible(false);
//            openEmployeeManagementPage();
            new EmployeeMPage(superMarket, accessibleEmployees, this);
        });
        options.add(manageStaffButton);
    }

    // EFFECTS: adds the old employees button to options panel
    private void addOldStaffButton() {
        JButton oldStaffButton = new JButton("Old Employees");
        oldStaffButton.setToolTipText("view the list of old staff");
        oldStaffButton.addActionListener(e -> {
            setVisible(false);
            openOldEmployeesPage();
        });
        options.add(oldStaffButton);
    }

    // EFFECTS: adds the transactions button to options panel
    private void addTransactionsButton() {
        JButton transactionsButton = new JButton("Transaction Records");
        transactionsButton.setToolTipText("view all transactions");
        transactionsButton.addActionListener(e -> {
            setVisible(false);
//            openTransactionsPage();
            new TransactionsPage(superMarket, superMarket.getTransactionsList(), this);
        });
        options.add(transactionsButton);
    }

    // EFFECTS: adds the inventory button to options panel
    private void addInventoryButton() {
        JButton manageInventoryButton = new JButton("Store Inventory");
        manageInventoryButton.setToolTipText("view Store Inventory/Storage");
        manageInventoryButton.addActionListener(e -> {
            setVisible(false);
            new InventoryPage(superMarket, this);
        });
        options.add(manageInventoryButton);
    }

    // EFFECTS: adds the coupons button to options panel
    private void addCouponsButton() {
        JButton manageCouponsButton = new JButton("Manage Coupons");
        manageCouponsButton.setToolTipText("Edit or View Coupons");
        manageCouponsButton.addActionListener(e -> {
            setVisible(false);
            openCouponManagementPage();
        });
        options.add(manageCouponsButton);
    }

    // EFFECTS: adds the password change button to options panel
    private void addPasswordChangeButton() {
        JButton passwordChangeButton = new JButton("Change Password");
        passwordChangeButton.setToolTipText("Change Password");
        passwordChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PasswordChangePage(superMarket);
            }
        });
        options.add(passwordChangeButton);
    }

    // EFFECTS: adds the logout button to the class frame
    private void addLogoutButton() {
        JPanel returnB = new JPanel();
        returnB.setLayout(new GridLayout(1, 1));
        returnB.setBorder(new EmptyBorder(new Insets(50, 800, 50, 50)));
        add(returnB, BorderLayout.PAGE_END);

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


    /**
     * // ALL OF OLD EMPLOYEES PAGE
     * // Open Old Employees page
     */

    // EFFECTS: makes the old employees page and adds the data to it
    private void openOldEmployeesPage() {
        JFrame frame = new JFrame();
        initializeExitWindows(frame);

        JPanel oldEmp = new JPanel();
        oldEmp.setLayout(new GridLayout(1, 1));
        oldEmp.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(oldEmp, BorderLayout.CENTER);

        JTable table = new JTable(superMarket.getOldEmployees().size() + 1, 4);
        JScrollPane scrollpane = new JScrollPane(table);
        table.setRowHeight(30);

        makeOldEmployeesTable(table);

        oldEmp.add(table);

        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new GridLayout(0, 1));
        bottomRow.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(bottomRow, BorderLayout.PAGE_END);

        // Close Button
        JButton closeButton = new JButton("Close");
        closeButton.setToolTipText("Close");
        setUpCloseButton(frame, closeButton);
        bottomRow.add(closeButton);


        frame.setVisible(true);
        frame.revalidate();
    }

    // EFFECTS: sets the close button to the old employees page
    private void setUpCloseButton(JFrame frame, JButton close) {
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disposePage(frame);
            }
        });
    }

    // EFFECTS: makes the old employees table
    private void makeOldEmployeesTable(JTable table) {
        int count = 1;
        table.setValueAt("Employee name", 0, 0);
        table.setValueAt("Designation", 0, 1);
        table.setValueAt("Access Level", 0, 2);
        table.setValueAt("Password", 0, 3);

        for (Employee e : superMarket.getOldEmployees()) {
            table.setValueAt(e.getName(), count, 0);
            table.setValueAt(e.getDesignation(), count, 1);
            table.setValueAt(e.getAccessLevel(), count, 2);
            table.setValueAt(e.getPassword(), count, 3);
            count++;
        }
    }

    /**
     * // ALL OF COUPONS PAGE BELOW THIS
     * // COUPONS MANAGEMENT PAGE
     */

    // EFFECTS: creates the coupon management page with the required buttons and fields
    private void openCouponManagementPage() {
        JFrame frame = new JFrame();
        initializeExitWindows(frame);


        JPanel upperRow = new JPanel();
        initializeUpperRowCouponsPage(frame, upperRow);

        // Buttons, Fields and Boxes
        JButton viewAllButton = new JButton("View All");


        JButton addCouponButton = new JButton("Add Coupon");


        JButton removeCouponButton = new JButton("Remove Coupon");


        JPanel middleColumn = new JPanel();
        initializeMiddleRowCouponsPage(frame, middleColumn);

        JLabel couponsComboBoxL = new JLabel("Select Coupon:");


        JComboBox couponsJComboBox = new JComboBox();


        JLabel couponCodeL = new JLabel("Coupon Code:");


        JTextField couponCode = new JTextField();


        JLabel couponValueL = new JLabel("Coupon Value:");


        JTextField couponValue = new JTextField();


        JPanel lowerRow = new JPanel();
        initializeLowerRowCouponsPage(frame, lowerRow);

        JButton addButton = new JButton("Add");

        JButton removeButton = new JButton("Remove");

        addButtonsAndLastPartOfMethodCouponsPage(frame, upperRow, viewAllButton, addCouponButton,
                removeCouponButton, middleColumn, couponsComboBoxL, couponsJComboBox, couponCodeL,
                couponCode, couponValueL, couponValue, lowerRow, addButton, removeButton);
    }

    // EFFECTS: initializes the given panel for page end orientation
    private void initializeLowerRowCouponsPage(JFrame frame, JPanel lowerRow) {
        lowerRow.setLayout(new GridLayout(2, 2));
        frame.add(lowerRow, BorderLayout.SOUTH);
        lowerRow.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
    }

    // EFFECTS: initializes the given panel for page mid orientation
    private void initializeMiddleRowCouponsPage(JFrame frame, JPanel middleColumn) {
        middleColumn.setLayout(new GridLayout(5, 1));
        frame.add(middleColumn, BorderLayout.CENTER);
        middleColumn.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
    }

    // EFFECTS: initializes the given panel for page start orientation
    private void initializeUpperRowCouponsPage(JFrame frame, JPanel upperRow) {
        upperRow.setLayout(new GridLayout(1, 3));
        frame.add(upperRow, BorderLayout.NORTH);
        upperRow.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
    }

    // EFFECTS: adds the buttons to the coupons page
    private void addButtonsAndLastPartOfMethodCouponsPage(JFrame frame, JPanel upperRow, JButton viewAllButton,
                                                          JButton addCouponButton, JButton removeCouponButton,
                                                          JPanel middleColumn, JLabel couponsComboBoxL,
                                                          JComboBox couponsJComboBox, JLabel couponCodeL,
                                                          JTextField couponCode, JLabel couponValueL,
                                                          JTextField couponValue, JPanel lowerRow,
                                                          JButton addButton, JButton removeButton) {
        upperRow.add(viewAllButton);
        upperRow.add(addCouponButton);
        upperRow.add(removeCouponButton);
        middleColumn.add(couponsComboBoxL);
        middleColumn.add(couponsJComboBox);
        middleColumn.add(couponCodeL);
        middleColumn.add(couponCode);
        middleColumn.add(couponValueL);
        middleColumn.add(couponValue);
        lowerRow.add(addButton);
        lowerRow.add(removeButton);

        finalPartOfCouponPage(frame, viewAllButton, addCouponButton, removeCouponButton,
                couponsJComboBox, couponCode, couponValue, lowerRow, addButton, removeButton);

        frame.setVisible(true);
        frame.revalidate();
    }

    // EFFECTS: adds action listeners to the coupon page
    private void finalPartOfCouponPage(JFrame frame, JButton viewAllButton, JButton addCouponButton,
                                       JButton removeCouponButton, JComboBox couponsJComboBox,
                                       JTextField couponCode, JTextField couponValue, JPanel lowerRow,
                                       JButton addButton, JButton removeButton) {
        setToDefaultCouponsPage(viewAllButton, addCouponButton, removeCouponButton, couponsJComboBox,
                couponCode, couponValue, addButton, removeButton);

        // ActionListeners for the  buttons
        addActionListenersForCouponManagement(frame, viewAllButton, addCouponButton, removeCouponButton,
                couponsJComboBox, couponCode, couponValue, addButton, removeButton);

        // Return to main menu page
        JButton finish = new JButton("Return To Menu");
        setUpFinishButtonCouponPage(frame, finish);
        lowerRow.add(finish);
    }

    // EFFECTS: sets up the finish button for the coupons page
    private void setUpFinishButtonCouponPage(JFrame frame, JButton finish) {
        finish.setBackground(new Color(0xFD0303));
        finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disposePage(frame);
                setVisible(true);
            }
        });
    }

    // EFFECTS: adds action listeners for the coupon management page
    private void addActionListenersForCouponManagement(JFrame frame, JButton viewAllButton, JButton addCouponButton,
                                                       JButton removeCouponButton, JComboBox couponsJComboBox,
                                                       JTextField couponCode, JTextField couponValue,
                                                       JButton addButton, JButton removeButton) {
        couponsJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coupon c = superMarket.giveCoupon((String) couponsJComboBox.getSelectedItem());

                if (c != null) {
                    couponCode.setText(c.getCouponCode());
                    couponValue.setText(Integer.toString(c.getValue()));
                }
            }
        });

        addActionListenersForUpperRowCouponPage(viewAllButton, addCouponButton, removeCouponButton,
                couponsJComboBox, couponCode, couponValue, addButton, removeButton);

        setActionListenerForAddButtonCouponPage(frame, viewAllButton, addCouponButton, removeCouponButton,
                couponsJComboBox, couponCode, couponValue, addButton, removeButton);

        setActionListenerForRemoveButtonCouponPage(frame, viewAllButton, addCouponButton,
                removeCouponButton, couponsJComboBox, couponCode, couponValue, addButton, removeButton);
    }

    // EFFECTS: adds action listeners for the upper buttons in the coupons page
    private void addActionListenersForUpperRowCouponPage(JButton viewAllButton, JButton addCouponButton,
                                                         JButton removeCouponButton, JComboBox couponsJComboBox,
                                                         JTextField couponCode, JTextField couponValue,
                                                         JButton addButton, JButton removeButton) {
        setViewAllActionListenerCouponPage(viewAllButton, addCouponButton, removeCouponButton,
                couponsJComboBox, couponCode, couponValue, addButton, removeButton);

        addCouponButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setToDefaultCouponsPage(viewAllButton, addCouponButton, removeCouponButton, couponsJComboBox,
                        couponCode, couponValue, addButton, removeButton);
                addCouponButton.setBackground(new Color(0xF8F805));
                couponCode.setEnabled(true);
                couponValue.setEnabled(true);
                addButton.setEnabled(true);
            }
        });

        removeCouponButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setToDefaultCouponsPage(viewAllButton, addCouponButton, removeCouponButton, couponsJComboBox,
                        couponCode, couponValue, addButton, removeButton);
                removeCouponButton.setBackground(new Color(0xF8F805));
                couponsJComboBox.setEnabled(true);
                removeButton.setEnabled(true);
            }
        });
    }

    // EFFECTS: adds action listener for view all button in coupon management page
    private void setViewAllActionListenerCouponPage(JButton viewAllButton, JButton addCouponButton,
                                                    JButton removeCouponButton, JComboBox couponsJComboBox,
                                                    JTextField couponCode, JTextField couponValue,
                                                    JButton addButton, JButton removeButton) {
        viewAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setToDefaultCouponsPage(viewAllButton, addCouponButton, removeCouponButton, couponsJComboBox,
                        couponCode, couponValue, addButton, removeButton);
                viewCoupons();
            }
        });
    }

    // EFFECTS: sets action listener for remove button in coupon page
    private void setActionListenerForRemoveButtonCouponPage(JFrame frame, JButton viewAllButton,
                                                            JButton addCouponButton, JButton removeCouponButton,
                                                            JComboBox couponsJComboBox, JTextField couponCode,
                                                            JTextField couponValue, JButton addButton,
                                                            JButton removeButton) {
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = (String) couponsJComboBox.getSelectedItem();
                superMarket.getCouponsList().removeIf(c -> c.getCouponCode().equals(code));
                JOptionPane.showMessageDialog(frame, "Coupon Removed");
                setToDefaultCouponsPage(viewAllButton, addCouponButton, removeCouponButton, couponsJComboBox,
                        couponCode, couponValue, addButton, removeButton);
                updateCoupons(couponsJComboBox);
            }
        });
    }

    // EFFECTS: sets action listener for add button in coupons page
    private void setActionListenerForAddButtonCouponPage(JFrame frame, JButton viewAllButton, JButton addCouponButton,
                                                         JButton removeCouponButton, JComboBox couponsJComboBox,
                                                         JTextField couponCode, JTextField couponValue,
                                                         JButton addButton, JButton removeButton) {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (superMarket.giveCoupon(couponCode.getText()) == null) {
                        Coupon added = new Coupon(couponCode.getText(), Integer.parseInt(couponValue.getText()));
                        superMarket.getCouponsList().add(added);
                        updateCoupons(couponsJComboBox);
                        JOptionPane.showMessageDialog(frame, "Coupon Added!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Coupon already Exists");
                    }
                    setToDefaultCouponsPage(viewAllButton, addCouponButton, removeCouponButton, couponsJComboBox,
                            couponCode, couponValue, addButton, removeButton);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid Inputs");
                    setToDefaultCouponsPage(viewAllButton, addCouponButton, removeCouponButton, couponsJComboBox,
                            couponCode, couponValue, addButton, removeButton);
                }
            }
        });
    }

    // EFFECTS: sets the coupon page to default
    private void setToDefaultCouponsPage(JButton viewAllButton, JButton addCouponButton, JButton removeCouponButton,
                                         JComboBox couponsJComboBox, JTextField couponCode, JTextField couponValue,
                                         JButton addButton, JButton removeButton) {
        viewAllButton.setBackground(null);
        addCouponButton.setBackground(null);
        removeCouponButton.setBackground(null);
        couponsJComboBox.setEnabled(false);
        couponCode.setEnabled(false);
        couponValue.setEnabled(false);
        couponCode.setText("");
        couponValue.setText("");
        addButton.setEnabled(false);
        removeButton.setEnabled(false);
    }

    // EFFECTS: updates the given comboBox based on the coupons
    private void updateCoupons(JComboBox box) {
        box.removeAll();
        for (Coupon c : superMarket.getCouponsList()) {
            box.addItem(c.getCouponCode());
        }
    }

    // EFFECTS: displays a table of all the coupons
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
        JButton close = new JButton("Close");
        close.setBounds(new Rectangle(40, 30));
        setCloseButtonForJustDisposePages(frame, close);
        bottomRow.add(close);

        frame.setVisible(true);
        frame.revalidate();
    }

    // EFFECTS: makes the coupons table
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

    // EFFECTS: makes the set password page with the required buttons and fields
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

    // EFFECTS: sets tool tips for the password fields
    private void setToolTipsForPasswordFields(JTextField oldPassword, JTextField newPassword, JTextField confirmation) {
        oldPassword.setToolTipText("enter old password");
        newPassword.setToolTipText("enter new password");
        confirmation.setToolTipText("passwords should match");
    }

    // EFFECTS: adds the buttons at the end of the page
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

    // EFFECTS: adds action listeners to the buttons in thepassword change page
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

    // EFFECTS: disposes the given frame and sets the visibility of the class frame to true
    private void disposePage(JFrame frame) {
        frame.dispose();
        setVisible(true);
    }

    // EFFECTS: initializes a window with exit operation as dispose
    private void initializeDisposableWindow(JFrame frame) {
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // EFFECTS: initializes the given frame with exit operation as exit
    private void initializeExitWindows(JFrame frame) {
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // EFFECTS: sets the action listener for the close button of the temporary pages
    private void setCloseButtonForJustDisposePages(JFrame frame, JButton close) {
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }
}
