package ui;

import exceptions.StockCannotBeEditedException;
import model.*;
import ui.commonpages.PasswordChangePage;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

// this class represents the cashier page of the supermarket
public class LevelTwo extends JFrame {
    // Fields
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 750;
    private SuperMarket superMarket;
    private SuperMarketGUI superMarketGUI;
    private JPanel options;

    // Constructor
    // EFFECTS: constructs the level two page and assigns the respective fields
    public LevelTwo(SuperMarket givenSuperMarket, SuperMarketGUI superMarketGUI1) {
        superMarket = superMarketGUI1.getSuperMarket();
        superMarketGUI = superMarketGUI1;
        levelTwoAccessPage();
    }

    // EFFECTS: Constructs the frame and initializes the level two page
    public void levelTwoAccessPage() {
        setLayout(new BorderLayout());
        setSize(new Dimension(WIDTH, HEIGHT));
        createOptions();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: creates the main page of level two accessible options
    private void createOptions() {
        options = new JPanel();
        add(options, BorderLayout.CENTER);
        options.setLayout(new GridLayout(5, 1));
        options.setBorder(new EmptyBorder(new Insets(100, 100, 100, 100)));

        addBillingButton();

        addChangePasswordButton();

        // Logut Button
        JPanel returnB = new JPanel();
        returnB.setLayout(new GridLayout(1, 1));
        returnB.setBorder(new EmptyBorder(new Insets(50, 800, 50, 50)));
        add(returnB, BorderLayout.PAGE_END);

        addLogoutButtonLevelTwo(returnB);
    }

    // EFFECTS: makes and adds the logout button to the given panel
    private void addLogoutButtonLevelTwo(JPanel returnB) {
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

    // EFFECTS: makes and adds the password change button to options panel
    private void addChangePasswordButton() {
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

    // EFFECTS: makes and adds the billing button to options panel
    private void addBillingButton() {
        JButton billingButton = new JButton("Start Customer Billing");
        billingButton.setToolTipText("start a new billing");
        options.add(billingButton);
        billingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                openCustomerBillingPage();
            }
        });
    }


    // EFFECTS: Initializes the given frame with close operation to be dispose
    private void initializeDisposableWindow(JFrame frame) {
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // EFFECTS: Initializes the given frame with close operation to be exit
    private void initializeExitWindows(JFrame frame) {
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * ALL OF BILLING PAGE BELOW THIS
     */

    // EFFECTS: creates the billing page of the cashier with the required buttons and tables
    private void openCustomerBillingPage() {
        superMarket.commonReceipt = new Receipt();

        JFrame frame = new JFrame();
        initializeDisposableWindow(frame);


        JPanel initial = new JPanel();
        initial.setLayout(new GridLayout(1, 3));
        initial.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        frame.add(initial, BorderLayout.NORTH);

        JButton addNextItemButton = new JButton("Add Next Item");
        addNextItemButton.setToolTipText("Add new item to billing");
        initial.add(addNextItemButton);

        JButton addCoupon = new JButton("Add Coupon");
        addCoupon.setToolTipText("Apply Coupon To Billing");
        initial.add(addCoupon);

        JButton callSeniorCashierButton = new JButton("Call Senior Cashier");
        callSeniorCashierButton.setToolTipText("Call Senior Cashier For Assistance");
        initial.add(callSeniorCashierButton);

        addCenterPartOfFrameBillingPage(frame, addNextItemButton, addCoupon, callSeniorCashierButton);

        frame.setVisible(true);
        frame.revalidate();
    }

    // EFFECTS: adds a panel with fields to the middle part of the frame
    private void addCenterPartOfFrameBillingPage(JFrame frame, JButton addNextItemButton, JButton addCoupon,
                                                 JButton callSeniorCashierButton) {
        // CENTER

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

        pageCenter.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.LINE_START);


        addTablesBillingPage(frame, addNextItemButton, addCoupon, callSeniorCashierButton, pageCenter, nameLabel,
                codesInput, quantityInput, addButton);
    }

    // EFFECTS: adds the receipt outline to the given frame
    private void addTablesBillingPage(JFrame frame, JButton addNextItemButton, JButton addCoupon,
                                      JButton callSeniorCashierButton, JPanel pageCenter, JLabel nameLabel,
                                      JTextField codesInput, JTextField quantityInput, JButton addButton) {
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


        addLowerRowBillingPage(frame, addNextItemButton, addCoupon, callSeniorCashierButton, pageCenter, nameLabel,
                codesInput, quantityInput, addButton, itemsModel, couponsModel, total);
    }

    // EFFECTS: adds the buttons at the end of the billing page and sets action listeners for all buttons in the page
    private void addLowerRowBillingPage(JFrame frame, JButton addNextItemButton, JButton addCoupon,
                                        JButton callSeniorCashierButton, JPanel pageCenter, JLabel nameLabel,
                                        JTextField codesInput, JTextField quantityInput, JButton addButton,
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

        setToDefaultBPage(addNextItemButton, addCoupon, callSeniorCashierButton,
                nameLabel, codesInput, quantityInput, addButton);

        // Action Listners
        setBillingPageALButtons(frame, addNextItemButton, addCoupon, callSeniorCashierButton, pageCenter, nameLabel,
                codesInput, quantityInput, addButton, itemsModel, couponsModel, total, cancelBillingButton,
                finishBillingButton);
    }

    // EFFECTS: sets action listeners for all buttons in the page
    private void setBillingPageALButtons(JFrame frame, JButton addNextItemButton, JButton addCoupon,
                                         JButton callSeniorCashierButton, JPanel pageCenter, JLabel nameLabel,
                                         JTextField codesInput, JTextField quantityInput, JButton addButton,
                                         DefaultTableModel itemsModel, DefaultTableModel couponsModel, JLabel total,
                                         JButton cancelBillingButton, JButton finishBillingButton) {

        setAddItemButtonAL(addNextItemButton, addCoupon, callSeniorCashierButton, nameLabel, codesInput,
                quantityInput, addButton);

        setAddCouponButtonAL(addNextItemButton, addCoupon, callSeniorCashierButton, nameLabel, codesInput,
                quantityInput, addButton);

        setCallSCButtonAL(frame, callSeniorCashierButton, pageCenter, itemsModel, couponsModel, total);

        setAddButtonAL(frame, addNextItemButton, addCoupon, callSeniorCashierButton, pageCenter, nameLabel,
                codesInput, quantityInput, addButton, itemsModel, couponsModel, total);

        setFrameReturnFunctionBillingPage(frame, pageCenter, itemsModel, couponsModel, total);

        setCancelBillingButtonAL(frame, cancelBillingButton);

        setFinishBillingButtonAL(frame, finishBillingButton);
    }

    // EFFECTS: sets action listener for add Item button buttons in the page
    private void setAddItemButtonAL(JButton addNextItemButton, JButton addCoupon, JButton callSeniorCashierButton,
                                    JLabel nameLabel, JTextField codesInput, JTextField quantityInput,
                                    JButton addButton) {
        addNextItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setToDefaultBPage(addNextItemButton, addCoupon, callSeniorCashierButton,
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

    // EFFECTS: sets action listener for add coupon button buttons in the page
    private void setAddCouponButtonAL(JButton addNextItemButton, JButton addCoupon, JButton callSeniorCashierButton,
                                      JLabel nameLabel, JTextField codesInput, JTextField quantityInput,
                                      JButton addButton) {
        addCoupon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setToDefaultBPage(addNextItemButton, addCoupon, callSeniorCashierButton,
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

    // EFFECTS: sets action listener for finish billing button buttons in the page
    private void setFinishBillingButtonAL(JFrame frame, JButton finishBillingButton) {
        finishBillingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransactionRecord t = new TransactionRecord(superMarket.commonReceipt,
                        superMarket.getEmployeeLoggedIn());
                superMarket.getTransactionsList().add(t);
                try {
                    produceFinishBillingSound();
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

    // EFFECTS: sets action listener for cancel billing button in the page
    private void setCancelBillingButtonAL(JFrame frame, JButton cancelBillingButton) {
        cancelBillingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                setVisible(true);
            }
        });
    }

    // EFFECTS: deals with the case when the billing frame reappears after senior cashier signsOut
    private void setFrameReturnFunctionBillingPage(JFrame frame, JPanel pageCenter, DefaultTableModel itemsModel,
                                                   DefaultTableModel couponsModel, JLabel total) {
        frame.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {
                System.out.println("billing frame shown");
                superMarketGUI.loadSuperMarket();
                superMarket = superMarketGUI.getSuperMarket();
                updateReceipt(pageCenter, itemsModel, couponsModel, total);
                superMarket.setAccess(2);
            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

    // EFFECTS: sets action listener for call senior cashier button in the page
    private void setCallSCButtonAL(JFrame frame, JButton callSeniorCashierButton, JPanel pageCenter,
                                   DefaultTableModel itemsModel, DefaultTableModel couponsModel, JLabel total) {
        callSeniorCashierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callSeniorCashier(frame);
                superMarket.setBillingInProcess(false);
                updateReceipt(pageCenter, itemsModel, couponsModel, total);
            }
        });
    }

    // EFFECTS: sets action listener for add button in the page
    private void setAddButtonAL(JFrame frame, JButton addNextItemButton, JButton addCoupon,
                                JButton callSeniorCashierButton, JPanel pageCenter, JLabel nameLabel,
                                JTextField codesInput, JTextField quantityInput, JButton addButton,
                                DefaultTableModel itemsModel, DefaultTableModel couponsModel, JLabel total) {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (nameLabel.getText().equals("Item Code")) {

                        processAddItemToBillingPage(codesInput, quantityInput, pageCenter, itemsModel,
                                couponsModel, total, frame);
                    } else {
                        processAddCouponToBillingPage(nameLabel, pageCenter, itemsModel, couponsModel, total, frame);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid Entries");
                }
                setToDefaultBPage(addNextItemButton, addCoupon, callSeniorCashierButton,
                        nameLabel, codesInput, quantityInput, addButton);
            }
        });
    }

    // EFFECTS: collects information from the text fields and processes adding coupon to receipt
    private void processAddCouponToBillingPage(JLabel nameLabel, JPanel pageCenter, DefaultTableModel itemsModel,
                                               DefaultTableModel couponsModel, JLabel total, JFrame frame) {
        String code = nameLabel.getText();
        Coupon coupon = superMarket.giveCoupon(code);
        if (coupon != null) {
            superMarket.commonReceipt.getCouponsUsed().add(coupon);
            superMarket.getCouponsList().remove(coupon);
            updateReceipt(pageCenter, itemsModel, couponsModel, total);
            JOptionPane.showMessageDialog(frame, "Coupon Applied");
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid Coupon Code");
        }
    }

    // EFFECTS: collects information from the text fields and processes adding item to receipt
    private void processAddItemToBillingPage(JTextField codesInput, JTextField quantityInput, JPanel pageCenter,
                                             DefaultTableModel itemsModel, DefaultTableModel couponsModel,
                                             JLabel total, JFrame frame) {
        int code = Integer.parseInt(codesInput.getText());
        Item item = superMarket.giveItemByCode(code);

        if (item.getName() != null) {
            int num = Integer.parseInt(quantityInput.getText());

            if (item.getNumber() >= num) {
                int price = item.getPriceOfOne();
                try {
                    item.removeStock(num);
                } catch (StockCannotBeEditedException e) {
                    JOptionPane.showMessageDialog(frame, "Invalid quantity");
                }
                Item itemToReceipt = new Item(item.getName(), item.getItemCode(), num, price);
                superMarket.commonReceipt.getItemsBought().add(itemToReceipt);
                updateReceipt(pageCenter, itemsModel, couponsModel, total);
                JOptionPane.showMessageDialog(frame, "Item Added");
            } else {
                JOptionPane.showMessageDialog(frame, "Required Stock not available");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Item with the code not available");
        }
    }

    // EFFECTS: sets the billing frame to the default condition
    private void setToDefaultBPage(JButton addNextItemButton, JButton addCoupon,
                                   JButton callSeniorCashierButton, JLabel nameLabel,
                                   JTextField codesInput, JTextField quantityInput, JButton addButton) {
        addNextItemButton.setBackground(null);
        addCoupon.setBackground(null);
        callSeniorCashierButton.setBackground(null);
        nameLabel.setText("Item Code");
        codesInput.setText("");
        codesInput.setEnabled(false);
        quantityInput.setEnabled(false);
        quantityInput.setText("");
        addButton.setText("Add");
        addButton.setToolTipText("Add Item to Billing");
    }

    // EFFECTS: updates the receipt to the new receipt in the suermarket
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

    // EFFECTS: calls the senior cashier and saves the session at this point
    public void callSeniorCashier(JFrame frame) {
        frame.setVisible(false);
        superMarket.setBillingInProcess(true);
        superMarketGUI.billingFrame = frame;
        superMarketGUI.saveSuperMarket();
        superMarketGUI.mainPage();

    }

    // EFFECTS: produces the finish billing sound when called or throws an IO exception or LineUnavailableException
    //          or UnsupportedAudioFileException
    public static void produceFinishBillingSound() throws IOException,
            LineUnavailableException, UnsupportedAudioFileException {
        File soundFile = new File("./data/soundEffect.wav");
        AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile.toURI().toURL());

        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        clip.start();
    }

}

