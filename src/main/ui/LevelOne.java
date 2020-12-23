package ui;

import model.SuperMarket;
import ui.commonpages.InventoryPage;
import ui.commonpages.PasswordChangePage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// this class represents the stocking staff page of the supermarket
public class LevelOne extends JFrame {
    // Fields
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 750;
    private SuperMarket superMarket;
    private SuperMarketGUI superMarketGUI;
    private JPanel options;


    // Constructor
    // EFFECTS: constructs the level one page and assigns the respective fields
    public LevelOne(SuperMarket givenSuperMarket, SuperMarketGUI superMarketGUI1) {
        superMarket = givenSuperMarket;
        superMarketGUI = superMarketGUI1;
        levelOneAccessPage();
    }

    // EFFECTS: Constructs the frame and initializes the level one page
    public void levelOneAccessPage() {
        setLayout(new BorderLayout());
        setSize(new Dimension(WIDTH, HEIGHT));
        createOptions();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: creates the main page of level one accessible options
    private void createOptions() {
        options = new JPanel();
        add(options, BorderLayout.CENTER);
        options.setLayout(new GridLayout(5, 1));
        options.setBorder(new EmptyBorder(new Insets(100, 100, 100, 100)));

        JButton inventoryButton = new JButton("Store Inventory");
        inventoryButton.setToolTipText("Edit or View inventories");
        options.add(inventoryButton);

        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setToolTipText("change my password");
        options.add(changePasswordButton);

        addInventoryButton(inventoryButton);

        addChangePasswordButton(changePasswordButton);

        // Logut Button
        addLogoutButton();
    }

    // EFFECTS: adds the inventory button to the options panel
    private void addInventoryButton(JButton inventoryButton) {
        inventoryButton.addActionListener(e -> {
            setVisible(false);
//                openInventoryManagementPage();
            new InventoryPage(superMarket, this);
        });
    }

    // EFFECTS: adds the change password button to the options panel
    private void addChangePasswordButton(JButton changePasswordButton) {
        changePasswordButton.addActionListener(e -> {
//                openPasswordChangePage();
            new PasswordChangePage(superMarket);
        });
    }

    // EFFECTS: adds the logut button to the page
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
}