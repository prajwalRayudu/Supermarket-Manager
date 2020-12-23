package ui.commonpages;

import model.*;

import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class shows the window with the table of transactions which are passed in the constructor
public class TransactionsPage extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 750;
    private SuperMarket superMarket;
    private List<TransactionRecord> transactions;
    private JFrame mainFrame;

    // Constructor
    // EFFECTS: constructs a transactions page with the given superMarket and the list of transactions.
    //           Also, keeps track of the main page given in the currnt level
    public TransactionsPage(SuperMarket superMarket, List<TransactionRecord> transactions, JFrame mainFrame) {
        this.superMarket = superMarket;
        this.transactions = transactions;
        this.mainFrame = mainFrame;
        openTransactionsPage();
    }

    /**
     * // ALL OF TRANSACTIONS PAGE BELOW THIS
     * // Open Transaction page
     */

    // EFFECTS: opens the transactions page and adds the data
    private void openTransactionsPage() {
        initializeExitWindows();

        JPanel start = new JPanel();
        start.setLayout(new GridLayout(0, 1));
        start.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        add(start, BorderLayout.NORTH);

        JLabel transactionsL = new JLabel("Transactions:");
        start.add(transactionsL);

        setUpTransactionsTableMid();

        // Loguot
        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new GridLayout(0, 1));
        bottomRow.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        add(bottomRow, BorderLayout.PAGE_END);

        // Close Button
        JButton close = new JButton("Close");
        addCloseButtonTransactions(close);
        bottomRow.add(close);

        setVisible(true);
        revalidate();
    }

    // EFFECTS: adds the close button to the transactions page
    private void addCloseButtonTransactions(JButton close) {
        close.setBounds(new Rectangle(40, 30));
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                mainFrame.setVisible(true);
            }
        });
    }

    // EFFECTS: set the transactions table middle panel
    private void setUpTransactionsTableMid() {
        List<TransactionRecord> records = transactions;
        JPanel oldEmp = new JPanel();
        oldEmp.setLayout(new GridLayout(0, 1));
        oldEmp.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));

        JTable table = new JTable(records.size() + 1, 3);

        oldEmp.add(new JScrollPane(table));
        add(oldEmp, BorderLayout.CENTER);
        table.setRowHeight(30);

        makeTransactionsTable(records, table);
    }

    // EFFECTS: construct the transactions table
    private void makeTransactionsTable(List<TransactionRecord> records, JTable table) {
        table.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                System.out.println(table.getValueAt(row, col).toString());
                viewReceipt(transactions.get(row - 1).getReceipt());
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
            count++;
        }
    }

    // VIEW RECEIPT PAGE, Used in multiple classes
    // EFFECTS: displays the receipt
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

    // EFFECTS: adds the close button to the given panel
    private void addCloseButton(JFrame frame, JPanel bottomRow) {
        JButton close = new JButton("Close");
        setUpCloseButton(frame, close);
        bottomRow.add(close);
    }

    // EFFECTS: process the making of receipt
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

    // EFFECTS: add the total label to the given panel
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

    // EFFECTS: makes the receipt tables based on the given parameters
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

    // EFFECTS: initializes a window with exit operation as dispose
    private void initializeDisposableWindow(JFrame frame) {
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // EFFECTS: sets the close button to the old employees page
    private void setUpCloseButton(JFrame frame, JButton close) {
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    // EFFECTS: initializes the given frame with exit operation as exit
    private void initializeExitWindows() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
