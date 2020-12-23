package ui;

import exceptions.StockCannotBeEditedException;
import model.*;
import ui.commonpages.PasswordChangePage;
import ui.commonpages.TransactionsPage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// this class represents the senior cashier page of the supermarket
public class LevelThree extends JFrame {
    // Fields
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 750;
    private SuperMarket superMarket;
    private SuperMarketGUI superMarketGUI;
    private List<Employee> accessibleEmployees;
    private JPanel options;
    public JFrame billingFrame;

    // Constructor
    // EFFECTS: constructs the level three page and assigns the respective fields
    public LevelThree(SuperMarket givenSuperMarket, SuperMarketGUI superMarketGUI1, JFrame billingFrame1) {
        this(givenSuperMarket, superMarketGUI1);
        billingFrame = billingFrame1;
    }

    // EFFECTS: Constructs the frame and initializes the level three page
    public LevelThree(SuperMarket givenSuperMarket, SuperMarketGUI superMarketGUI1) {
        superMarket = givenSuperMarket;
        superMarketGUI = superMarketGUI1;
        accessibleEmployees = new ArrayList<>();

        for (Employee e : superMarket.getEmployeeList()) {
            if (e.getAccessLevel() == 2) {
                accessibleEmployees.add(e);
            }
        }

        if (superMarket.isBillingInProcess()) {
            openBillingPage();
        } else {
            levelThreeAccessPage();
        }
    }

    // EFFECTS: creates the main page of level three accessible options
    public void levelThreeAccessPage() {
        setLayout(new BorderLayout());
        setSize(new Dimension(WIDTH, HEIGHT));
        createOptions();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: makes all the buttons and adds them to options panel, also initializes the options panel
    private void createOptions() {
        options = new JPanel();
        add(options, BorderLayout.CENTER);
        options.setLayout(new GridLayout(5, 1));
        options.setBorder(new EmptyBorder(new Insets(100, 100, 100, 100)));

        addBillingButton();

        addCashierRecordsButton();


        addPasswordChangeButton();

        // Logut Button
        JPanel returnB = new JPanel();
        returnB.setLayout(new GridLayout(1, 1));
        returnB.setBorder(new EmptyBorder(new Insets(50, 800, 50, 50)));
        add(returnB, BorderLayout.PAGE_END);

        JButton logout = new JButton("Logout");
        logout.setBackground(new Color(0xFD0303));
        setUpLogoutButton(logout);
        returnB.add(logout);
    }

    // EFFECTS: sets the action listener for the logout button
    private void setUpLogoutButton(JButton logout) {
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
    }

    // EFFECTS: adds the password change buttin to teh options panel
    private void addPasswordChangeButton() {
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setToolTipText("change my password");
        options.add(changePasswordButton);

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PasswordChangePage(superMarket);
            }
        });
    }

    // EFFECTS: adds the view cashiers button to the options panel
    private void addCashierRecordsButton() {
        JButton viewCashiersAndTheirInformation = new JButton("Cashiers");
        viewCashiersAndTheirInformation.setToolTipText("View Cashiers");
        options.add(viewCashiersAndTheirInformation);

        viewCashiersAndTheirInformation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                viewCashiers();
            }
        });
    }

    // EFFECTS: adds the billing button the options panel
    private void addBillingButton() {
        JButton billingButton = new JButton("Start Billing");
        billingButton.setToolTipText("start new billing");
        options.add(billingButton);

        billingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                openBillingPage();
            }
        });
    }

    /**
     * View Cashiers Page Below This
     */

    // EFFECTS: makes the window which displays all the cashiers working
    public void viewCashiers() {
        JFrame frame = new JFrame();
        initializeExitWindows(frame);


        JPanel cashiers = new JPanel();
        cashiers.setLayout(new GridLayout(0, 1));
        cashiers.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));

        makeCashiersTable(frame, cashiers);
        frame.add(cashiers, BorderLayout.CENTER);

        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new GridLayout(0, 1));
        bottomRow.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(bottomRow, BorderLayout.PAGE_END);

        // Close Button
        JButton close = new JButton("Close");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                setVisible(true);
            }
        });

        bottomRow.add(close);
    }

    // EFFECTS: makes a table with the list of cashiers
    private void makeCashiersTable(JFrame frame, JPanel cashiers) {
        JTable table = new JTable(accessibleEmployees.size() + 1, 4);
        frame.add(new JScrollPane(table));
        cashiers.add(table);

        table.setRowHeight(30);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                System.out.println(table.getValueAt(row, col).toString());
                showTransactions(frame, superMarket.getCashierRecords(accessibleEmployees.get(row - 1).getName()));
            }

        });

        int count = 1;
        table.setValueAt("Employee name", 0, 0);
        table.setValueAt("Designation", 0, 1);
        table.setValueAt("Access Level", 0, 2);
        table.setValueAt("Transactions", 0, 3);

        addCashiersToTable(table, count);
    }

    // EFFECTS: adds Cashiers to the table
    private void addCashiersToTable(JTable table, int count) {
        for (Employee e : accessibleEmployees) {
            table.setValueAt(e.getName(), count, 0);
            table.setValueAt(e.getDesignation(), count, 1);
            table.setValueAt(e.getAccessLevel(), count, 2);
            table.setValueAt("Click to view Transactions", count, 3);
            count++;
        }
    }

    /**
     * Transactions Page
     */

    // EFFECTS: makse a window which shows all teh transactions
    private void showTransactions(JFrame frame, List<TransactionRecord> records) {
        new TransactionsPage(superMarket, records, frame);
    }


    /**
     * // ALL OF BILLING PAGE IS BELOW THIS
     * // Billing page
     */

    // EFFECTS: opens the billing page and makes all the buttons and initializes them
    private void openBillingPage() {
        if (!superMarket.isBillingInProcess()) {
            superMarket.commonReceipt = new Receipt();
        }
        JFrame frame = new JFrame();
        initializeDisposableWindow(frame);

        JPanel initial = new JPanel();
        initializeAbovePanelBillingPage(frame, initial);

        JButton addNextItemButton = new JButton("Add Next Item");
        addNextItemButton.setToolTipText("Add new item to billing");
        initial.add(addNextItemButton);

        JButton addCoupon = new JButton("Add Coupon");
        addCoupon.setToolTipText("Apply Coupon To Billing");
        initial.add(addCoupon);

        JButton removeItemButton = new JButton("Remove Item");
        addNextItemButton.setToolTipText("Remove item from billing");
        initial.add(removeItemButton);

        JButton removeCoupon = new JButton("Remove Coupon");
        addCoupon.setToolTipText("Remove Coupon from Billing");
        initial.add(removeCoupon);

        addSignOutButtonToBillingPage(frame, initial, addNextItemButton, addCoupon, removeItemButton, removeCoupon);

        frame.setVisible(true);
        frame.revalidate();
    }

    // EFFECTS: initializes the panel at the start of the billing page
    private void initializeAbovePanelBillingPage(JFrame frame, JPanel initial) {
        initial.setLayout(new GridLayout(1, 3));
        initial.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(initial, BorderLayout.NORTH);
    }

    // EFFECTS: adds the sign out button to the billing page
    private void addSignOutButtonToBillingPage(JFrame frame, JPanel initial, JButton addNextItemButton,
                                               JButton addCoupon, JButton removeItemButton, JButton removeCoupon) {
        JButton signOutButton = new JButton("Sign Out");
        signOutButton.setToolTipText("Hand over the billing back to the cashier");
        initial.add(signOutButton);
        if (!superMarket.isBillingInProcess()) {
            signOutButton.setEnabled(false);
        }

        // CENTER

        addCenterPanelToBillingPage(frame, addNextItemButton, addCoupon, removeItemButton, removeCoupon, signOutButton);
    }

    // EFFECTS: adds the fields t and buttons to the center part of the billing page
    private void addCenterPanelToBillingPage(JFrame frame, JButton addNextItemButton, JButton addCoupon,
                                             JButton removeItemButton, JButton removeCoupon, JButton signOutButton) {
        JPanel pageCenter = new JPanel();
        pageCenter.setLayout(new GridLayout(8, 1));
        pageCenter.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));


        JLabel nameLabel = new JLabel("Item Code");
        pageCenter.add(nameLabel);

        JTextField codesInput = new JTextField();
        pageCenter.add(codesInput);

        JLabel quantity = new JLabel("Quantity");
        pageCenter.add(quantity);

        JTextField quantityInput = new JTextField();
        pageCenter.add(quantityInput);

        JButton addButton = new JButton("Add");
        addButton.setToolTipText("Add Item to Billing");
        pageCenter.add(addButton);

        JButton removeButton = new JButton("Remove");
        addButton.setToolTipText("Remove Item from Billing");
        pageCenter.add(removeButton);

        pageCenter.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.LINE_START);

        addTablesToBillingPage(frame, addNextItemButton, addCoupon, removeItemButton, removeCoupon,
                signOutButton, pageCenter, nameLabel, codesInput, quantityInput, addButton, removeButton);
    }

    // EFFECTS: adds the receipt tables to the billing page
    private void addTablesToBillingPage(JFrame frame, JButton addNextItemButton, JButton addCoupon,
                                        JButton removeItemButton, JButton removeCoupon, JButton signOutButton,
                                        JPanel pageCenter, JLabel nameLabel, JTextField codesInput,
                                        JTextField quantityInput, JButton addButton, JButton removeButton) {
        DefaultTableModel itemsModel = new DefaultTableModel(0, 3);
        JTable itemsTable = new JTable(itemsModel);
        pageCenter.add(new JScrollPane(itemsTable));
        itemsTable.setRowHeight(30);
        itemsTable.setBounds(new Rectangle(100, 150));


        DefaultTableModel couponsModel = new DefaultTableModel(0, 2);
        JTable couponsTable = new JTable(couponsModel);

        pageCenter.add(new JScrollPane(couponsTable));
        couponsTable.setRowHeight(30);

        JLabel total = new JLabel("TOTAL: " + 0);
        pageCenter.add(total);
        updateReceipt(pageCenter, itemsModel, couponsModel, total);


        frame.add(pageCenter, BorderLayout.CENTER);


        billingPageAddLowerRow(frame, addNextItemButton, addCoupon, removeItemButton, removeCoupon,
                signOutButton, pageCenter, nameLabel, codesInput, quantityInput, addButton, removeButton,
                itemsModel, couponsModel, total);
    }

    // EFFECTS: adds the lower row of buttons to the billing page
    private void billingPageAddLowerRow(JFrame frame, JButton addNextItemButton, JButton addCoupon,
                                        JButton removeItemButton, JButton removeCoupon, JButton signOutButton,
                                        JPanel pageCenter, JLabel nameLabel, JTextField codesInput,
                                        JTextField quantityInput, JButton addButton, JButton removeButton,
                                        DefaultTableModel itemsModel, DefaultTableModel couponsModel, JLabel total) {
        JPanel lowerRow = new JPanel();
        lowerRow.setLayout(new GridLayout(1, 2));
        lowerRow.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(lowerRow, BorderLayout.PAGE_END);

        JButton cancelBillingButton = new JButton("Cancel Billing");
        cancelBillingButton.setToolTipText("Cancel current billing");
        lowerRow.add(cancelBillingButton);

        JButton finishBillingButton = new JButton("Finish Billing");
        finishBillingButton.setToolTipText("Finish Billing");
        lowerRow.add(finishBillingButton);

        billingPageInitializationAL(frame, addNextItemButton, addCoupon, removeItemButton, removeCoupon,
                signOutButton, pageCenter, nameLabel, codesInput, quantityInput, addButton, removeButton,
                itemsModel, couponsModel, total, cancelBillingButton, finishBillingButton);
    }

    // EFFECTS: initializes the billing page
    private void billingPageInitializationAL(JFrame frame, JButton addNextItemButton, JButton addCoupon,
                                             JButton removeItemButton, JButton removeCoupon, JButton signOutButton,
                                             JPanel pageCenter, JLabel nameLabel, JTextField codesInput,
                                             JTextField quantityInput, JButton addButton, JButton removeButton,
                                             DefaultTableModel itemsModel, DefaultTableModel couponsModel,
                                             JLabel total, JButton cancelBillingButton, JButton finishBillingButton) {
        if (superMarket.isBillingInProcess()) {
            cancelBillingButton.setEnabled(false);
            finishBillingButton.setEnabled(false);
        }

        setToDefaultBPage(addNextItemButton, addCoupon, removeItemButton, removeCoupon,
                nameLabel, codesInput, quantityInput, addButton);

        addActionListenersToBillingPage(frame, addNextItemButton, addCoupon, removeItemButton,
                removeCoupon, signOutButton, pageCenter, nameLabel, codesInput, quantityInput,
                addButton, removeButton, itemsModel, couponsModel, total, cancelBillingButton, finishBillingButton);
    }

    // EFFECTS: adds action listeners to all the buttons and fields in the page
    private void addActionListenersToBillingPage(JFrame frame, JButton addNextItemButton, JButton addCoupon,
                                                 JButton removeItemButton, JButton removeCoupon, JButton signOutButton,
                                                 JPanel pageCenter, JLabel nameLabel, JTextField codesInput,
                                                 JTextField quantityInput, JButton addButton, JButton removeButton,
                                                 DefaultTableModel itemsModel, DefaultTableModel couponsModel,
                                                 JLabel total, JButton cancelBillingButton,
                                                 JButton finishBillingButton) {
        // Action Listners
        setUpALForNextItemButton(addNextItemButton, addCoupon, removeItemButton, removeCoupon, nameLabel,
                codesInput, quantityInput, addButton);

        setUpALForAddCouponButton(addNextItemButton, addCoupon, removeItemButton, removeCoupon, nameLabel,
                codesInput, quantityInput, addButton);

        setUpALForRemoveItemButton(addNextItemButton, addCoupon, removeItemButton, removeCoupon, nameLabel,
                codesInput, quantityInput, addButton, removeButton);

        setUpALForRemoveCouponButton(addNextItemButton, addCoupon, removeItemButton, removeCoupon, nameLabel,
                codesInput, quantityInput, addButton, removeButton);


        setUpALForSignOutButton(frame, signOutButton);

        setUpALForAddButton(frame, addNextItemButton, addCoupon, removeItemButton, removeCoupon, pageCenter,
                nameLabel, codesInput, quantityInput, addButton, itemsModel, couponsModel, total);

        setUpALForRemoveButton(frame, addNextItemButton, addCoupon, removeItemButton, removeCoupon, nameLabel,
                codesInput, quantityInput, addButton, removeButton);

        setUpALForCancelButton(frame, cancelBillingButton);

        setUpALForFinishButton(frame, finishBillingButton);
    }

    // EFFECTS: adds the action listener to the finish button
    private void setUpALForFinishButton(JFrame frame, JButton finishBillingButton) {
        finishBillingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransactionRecord t = new TransactionRecord(superMarket.commonReceipt,
                        superMarket.getEmployeeLoggedIn());
                superMarket.getTransactionsList().add(t);
                try {
                    LevelTwo.produceFinishBillingSound();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (LineUnavailableException lineUnavailableException) {
                    lineUnavailableException.printStackTrace();
                } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                    unsupportedAudioFileException.printStackTrace();
                }
                frame.setVisible(false);
                setVisible(true);
            }
        });
    }

    // EFFECTS: adds the action listener to the cancel button
    private void setUpALForCancelButton(JFrame frame, JButton cancelBillingButton) {
        cancelBillingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                superMarket.commonReceipt = new Receipt();
                frame.setVisible(false);
                setVisible(true);
            }
        });
    }

    // EFFECTS: adds the action listener to the remove button
    private void setUpALForRemoveButton(JFrame frame, JButton addNextItemButton, JButton addCoupon,
                                        JButton removeItemButton, JButton removeCoupon, JLabel nameLabel,
                                        JTextField codesInput, JTextField quantityInput, JButton addButton,
                                        JButton removeButton) {
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (nameLabel.getText().equals("Item Code")) {
                        int code = Integer.parseInt(codesInput.getText());

                        processItemRemoval(code, frame);

                    } else {
                        processCouponRemoval(codesInput, frame);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid Entries");
                }

                setToDefaultBPage(addNextItemButton, addCoupon, removeItemButton, removeCoupon,
                        nameLabel, codesInput, quantityInput, addButton);
            }
        });
    }

    // EFFECTS: adds the action listener to the add  button
    private void setUpALForAddButton(JFrame frame, JButton addNextItemButton, JButton addCoupon,
                                     JButton removeItemButton, JButton removeCoupon, JPanel pageCenter,
                                     JLabel nameLabel, JTextField codesInput, JTextField quantityInput,
                                     JButton addButton, DefaultTableModel itemsModel, DefaultTableModel couponsModel,
                                     JLabel total) {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (nameLabel.getText().equals("Item Code")) {

                        processItem(codesInput, quantityInput, pageCenter, itemsModel, couponsModel, total, frame);
                    } else {
                        processAddCoupon(superMarket.giveCoupon(codesInput.getText()), pageCenter,
                                itemsModel, couponsModel, total, frame);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid Entries");
                }
                setToDefaultBPage(addNextItemButton, addCoupon, removeItemButton, removeCoupon,
                        nameLabel, codesInput, quantityInput, addButton);
            }
        });
    }

    // EFFECTS: processes the addition of item to receipt
    private void processItem(JTextField codesInput, JTextField quantityInput, JPanel pageCenter,
                             DefaultTableModel itemsModel, DefaultTableModel couponsModel,
                             JLabel total, JFrame frame) {
        int code = Integer.parseInt(codesInput.getText());
        Item item = superMarket.giveItemByCode(code);

        if (item.getName() != null) {
            int num = Integer.parseInt(quantityInput.getText());
            if (item.getNumber() >= num) {
                processAddItem(item, num, pageCenter, itemsModel, couponsModel, total, frame);
            } else {
                JOptionPane.showMessageDialog(frame, "Required Stock not available");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Item with the code not available");
        }
    }

    // EFFECTS: adds the action listener to the signOut button
    private void setUpALForSignOutButton(JFrame frame, JButton signOutButton) {
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Cashier will be logged back in");
                superMarket.setAccess(2);
                superMarket.setBillingInProcess(false);
                superMarketGUI.saveSuperMarket();
                frame.dispose();
                billingFrame.setVisible(true);
            }
        });
    }

    // EFFECTS: adds the action listener to the remove coupon button
    private void setUpALForRemoveCouponButton(JButton addNextItemButton, JButton addCoupon, JButton removeItemButton,
                                              JButton removeCoupon, JLabel nameLabel, JTextField codesInput,
                                              JTextField quantityInput, JButton addButton, JButton removeButton) {
        removeCoupon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setToDefaultBPage(addNextItemButton, addCoupon, removeItemButton, removeCoupon,
                        nameLabel, codesInput, quantityInput, addButton);
                removeCoupon.setBackground(new Color(0xF8F805));
                nameLabel.setText("Coupon Code");
                codesInput.setEnabled(true);
                removeButton.setText("Remove Coupon");
                removeButton.setToolTipText("Remove Coupon");
                removeButton.setEnabled(true);
            }
        });
    }

    // EFFECTS: adds the action listener to the remove item button
    private void setUpALForRemoveItemButton(JButton addNextItemButton, JButton addCoupon, JButton removeItemButton,
                                            JButton removeCoupon, JLabel nameLabel, JTextField codesInput,
                                            JTextField quantityInput, JButton addButton, JButton removeButton) {
        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setToDefaultBPage(addNextItemButton, addCoupon, removeItemButton, removeCoupon,
                        nameLabel, codesInput, quantityInput, addButton);
                removeItemButton.setBackground(new Color(0xF8F805));
                nameLabel.setText("Item Code");
                codesInput.setEnabled(true);
                removeButton.setText("Remove Item");
                removeButton.setToolTipText("Remove Item");
                removeButton.setEnabled(true);
            }
        });
    }

    // EFFECTS: adds the action listener to the add coupon button
    private void setUpALForAddCouponButton(JButton addNextItemButton, JButton addCoupon, JButton removeItemButton,
                                           JButton removeCoupon, JLabel nameLabel, JTextField codesInput,
                                           JTextField quantityInput, JButton addButton) {
        addCoupon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setToDefaultBPage(addNextItemButton, addCoupon, removeItemButton, removeCoupon,
                        nameLabel, codesInput, quantityInput, addButton);
                addCoupon.setBackground(new Color(0xF8F805));
                nameLabel.setText("Coupon Code");
                codesInput.setEnabled(true);
                addButton.setText("Add Coupon");
                addButton.setToolTipText("Apply Coupon");
                addButton.setEnabled(true);
            }
        });
    }

    // EFFECTS: adds the action listener to the next item butto
    private void setUpALForNextItemButton(JButton addNextItemButton, JButton addCoupon, JButton removeItemButton,
                                          JButton removeCoupon, JLabel nameLabel, JTextField codesInput,
                                          JTextField quantityInput, JButton addButton) {
        addNextItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setToDefaultBPage(addNextItemButton, addCoupon, removeItemButton, removeCoupon,
                        nameLabel, codesInput, quantityInput, addButton);
                addNextItemButton.setBackground(new Color(0xF8F805));
                nameLabel.setText("Item Code");
                codesInput.setEnabled(true);
                quantityInput.setEnabled(true);
                addButton.setToolTipText("Add Item to Billing");
                addButton.setEnabled(true);
            }
        });
    }

    // EFFECTS: processes removal of coupon from receipt by  getting the required information from the text fields
    private void processCouponRemoval(JTextField codesInput, JFrame frame) {
        for (Coupon c : superMarket.commonReceipt.getCouponsUsed()) {
            if (codesInput.getText().equals(c.getCouponCode())) {
                superMarket.commonReceipt.getCouponsUsed().remove(c);
                superMarket.getCouponsList().add(c);
                break;
            }
        }
        JOptionPane.showMessageDialog(frame,
                superMarket.commonReceipt.removeCoupon(codesInput.getText()));
    }

    // EFFECTS: processes removal of item from receipt by  getting the required information from the text fields
    private void processItemRemoval(int code, JFrame frame) {
        Item item = null;
        for (Item i : superMarket.commonReceipt.getItemsBought()) {
            if (i.getItemCode() == code) {
                item = i;
                superMarket.commonReceipt.getItemsBought().remove(i);
                break;
            }
        }

        if (item != null) {
            Item itemR = superMarket.giveItemByCode(item.getItemCode());
            try {
                itemR.addStock(item.getNumber());
            } catch (StockCannotBeEditedException e) {
                JOptionPane.showMessageDialog(frame, "Quantity entered has to be greater than 0");
            }
            JOptionPane.showMessageDialog(frame, "item removed");
        } else {
            JOptionPane.showMessageDialog(frame, "item not present");
        }
    }

    // EFFECTS: processes addition of item to receipt by  getting the required information from the text fields
    private void processAddItem(Item item, int num, JPanel pageCenter, DefaultTableModel itemsModel,
                                DefaultTableModel couponsModel, JLabel total, JFrame frame) {
        int price = item.getPriceOfOne();
        try {
            item.removeStock(num);
            Item itemToReceipt = new Item(item.getName(), item.getItemCode(), num, price);
            superMarket.commonReceipt.getItemsBought().add(itemToReceipt);
            updateReceipt(pageCenter, itemsModel, couponsModel, total);
            JOptionPane.showMessageDialog(frame, "Item Added");
        } catch (StockCannotBeEditedException e) {
            JOptionPane.showMessageDialog(frame, "Invalid quantity");
        }
    }

    // EFFECTS: processes addition of coupon to receipt by  getting the required information from the text fields
    private void processAddCoupon(Coupon coupon, JPanel pageCenter, DefaultTableModel itemsModel,
                                  DefaultTableModel couponsModel, JLabel total, JFrame frame) {
        if (coupon != null) {
            superMarket.commonReceipt.getCouponsUsed().add(coupon);
            superMarket.getCouponsList().remove(coupon);
            updateReceipt(pageCenter, itemsModel, couponsModel, total);
            JOptionPane.showMessageDialog(frame, "Coupon Applied");
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid Coupon Code");
        }
    }

    // EFFECTS: sets the billing page to its default state
    private void setToDefaultBPage(JButton addNextItemButton, JButton addCoupon, JButton removeItemButton,
                                   JButton removeCoupon, JLabel nameLabel,
                                   JTextField codesInput, JTextField quantityInput, JButton addButton) {
        addNextItemButton.setBackground(null);
        addCoupon.setBackground(null);
        removeItemButton.setBackground(null);
        removeCoupon.setBackground(null);
        nameLabel.setText("Item Code");
        codesInput.setText("");
        codesInput.setEnabled(false);
        quantityInput.setEnabled(false);
        quantityInput.setText("");
        addButton.setText("Add");
        addButton.setToolTipText("Add Item to Billing");
    }

    // EFFECTS: updates the receipt by clearing the given tables and adding items and coupons to it from the
    //          common receipt from supermarket
    private void updateReceipt(JPanel pageCenter, DefaultTableModel itemsModel, DefaultTableModel couponsModel,
                               JLabel totalC) {
        List<Item> items = superMarket.commonReceipt.getItemsBought();
        List<Coupon> coupons = superMarket.commonReceipt.getCouponsUsed();

        itemsModel.setRowCount(0);
        couponsModel.setRowCount(0);


        int total = 0;
        itemsModel.addRow(new String[]{"ITEM NAME", "QUANTITY", "PRICE"});
        for (Item i : items) {
            itemsModel.addRow(new String[]{i.getName() + "(" + i.getItemCode() + ")", Integer.toString(i.getNumber()),
                    Integer.toString(i.getNumber() * i.getPriceOfOne())});

            total += i.getNumber() * i.getPriceOfOne();
        }

        couponsModel.addRow(new String[]{"COUPON CODE", "COUPON VALUE"});

        for (Coupon c : coupons) {
            couponsModel.addRow(new String[]{c.getCouponCode(), Integer.toString(c.getValue())});

            total -= c.getValue();
        }

        if (total >= 0) {
            totalC.setText("Total:" + total);
        } else {
            totalC.setText("Total:" + 0);
        }
    }

    // EFFECTS: initializes a window with exit operation as dispose

    private void initializeDisposableWindow(JFrame frame) {
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // EFFECTS: initializes the given frame with exit operation as exit
    private void initializeExitWindows(JFrame frame) {
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}