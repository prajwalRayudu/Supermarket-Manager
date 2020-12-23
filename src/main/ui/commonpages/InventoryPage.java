package ui.commonpages;

import model.Inventory;
import model.Item;
import model.SuperMarket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Inventory management page, this class consists of the windows for the inventory page of the store
public class InventoryPage extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 750;
    private SuperMarket superMarket;
    private JFrame mainFrame;

    // Constructor
    // EFFECTS: constructs the inventory page with the given supermarket and main page, and call sthe method for page
    public InventoryPage(SuperMarket superMarket, JFrame mainFrame) {
        this.superMarket = superMarket;
        this.mainFrame = mainFrame;

        openInventoryManagementPage();
    }

    /**
     * // ALL OF INVENTORY MANAGEMENT PAGE BELOW THIS
     * // Open Inventory Management page
     */

    // EFFECTS: creates the inventory management page and adds the required buttons and text fields
    private void openInventoryManagementPage() {
        initializeExitWindows();

        JPanel inventoryOptions = new JPanel();
        inventoryOptions.setLayout(new GridLayout(12, 1));
        inventoryOptions.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        add(inventoryOptions, BorderLayout.CENTER);

        JPanel upperOptions = new JPanel();
        upperOptions.setLayout(new GridLayout(1, 4));
        upperOptions.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        add(upperOptions, BorderLayout.NORTH);

        JPanel lowerOptions = new JPanel();
        lowerOptions.setLayout(new GridLayout(1, 4));
        lowerOptions.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        add(lowerOptions, BorderLayout.SOUTH);

        // Buttons, Fields and Boxes
        addAllFields(inventoryOptions, upperOptions, lowerOptions);

        // Return to main menu page
        JButton finish = new JButton("Return To Menu");
        finish.setBackground(new Color(0xFD0303));
        lowerOptions.add(finish);
        addALToFinishButton(finish);

        setVisible(true);
        revalidate();
    }

    private void addALToFinishButton(JButton finish) {
        finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                mainFrame.setVisible(true);

            }
        });
    }

    // EFFECTS: makes all teh required fields and adds them to the panels
    private void addAllFields(JPanel inventoryOptions, JPanel upperOptions, JPanel lowerOptions) {
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


        addFinalRowOfButtons(inventoryOptions, upperOptions, lowerOptions, viewInventoryButton, addItemButton,
                removeItemButton, editItemButton, inventoryComboBoxL, inventoryComboBox, itemsComboBoxL,
                itemsComboBox, itemNameL, itemName, itemCodeL, itemCode, numberL, number, priceOfOneL, priceOfOne);
    }

    // EFFECTS: makes the final row of buttons and adds them to the panels
    private void addFinalRowOfButtons(JPanel inventoryOptions, JPanel upperOptions, JPanel lowerOptions,
                                      JButton viewInventoryButton, JButton addItemButton, JButton removeItemButton,
                                      JButton editItemButton, JLabel inventoryComboBoxL, JComboBox inventoryComboBox,
                                      JLabel itemsComboBoxL, JComboBox itemsComboBox, JLabel itemNameL,
                                      JTextField itemName, JLabel itemCodeL, JTextField itemCode, JLabel numberL,
                                      JTextField number, JLabel priceOfOneL, JTextField priceOfOne) {
        JButton viewButton = new JButton("View");
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        JButton saveButton = new JButton("Save");


        addAllButtonsToPanels(inventoryOptions, upperOptions, lowerOptions, viewInventoryButton, addItemButton,
                removeItemButton, editItemButton, inventoryComboBoxL, inventoryComboBox, itemsComboBoxL, itemsComboBox,
                itemNameL, itemName, itemCodeL, itemCode, numberL, number, priceOfOneL, priceOfOne, viewButton,
                addButton, removeButton, saveButton);
    }

    // EFFECTS: adds the given buttons and fields to their panels
    private void addAllButtonsToPanels(JPanel inventoryOptions, JPanel upperOptions, JPanel lowerOptions,
                                       JButton viewInventoryButton, JButton addItemButton, JButton removeItemButton,
                                       JButton editItemButton, JLabel inventoryComboBoxL, JComboBox inventoryComboBox,
                                       JLabel itemsComboBoxL, JComboBox itemsComboBox, JLabel itemNameL,
                                       JTextField itemName, JLabel itemCodeL, JTextField itemCode, JLabel numberL,
                                       JTextField number, JLabel priceOfOneL, JTextField priceOfOne,
                                       JButton viewButton, JButton addButton, JButton removeButton,
                                       JButton saveButton) {
        addFields(inventoryOptions, upperOptions, lowerOptions, viewInventoryButton, addItemButton, removeItemButton,
                editItemButton, inventoryComboBoxL, inventoryComboBox, itemsComboBoxL, itemsComboBox, itemNameL,
                itemName, itemCodeL, itemCode, numberL, number, priceOfOneL, priceOfOne, viewButton, addButton,
                removeButton, saveButton);

        setToDefaultInventoryPage(viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton,
                removeButton, saveButton);

        // ActionListeners for the  buttons
        addALToAllFields(viewInventoryButton, addItemButton, removeItemButton, editItemButton, inventoryComboBox,
                itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton, removeButton,
                saveButton);
    }

    // EFFECTS: Add all the buttons to their respective panels
    private void addFields(JPanel inventoryOptions, JPanel upperOptions, JPanel lowerOptions,
                           JButton viewInventoryButton, JButton addItemButton, JButton removeItemButton,
                           JButton editItemButton, JLabel inventoryComboBoxL, JComboBox inventoryComboBox,
                           JLabel itemsComboBoxL, JComboBox itemsComboBox, JLabel itemNameL, JTextField itemName,
                           JLabel itemCodeL, JTextField itemCode, JLabel numberL, JTextField number,
                           JLabel priceOfOneL, JTextField priceOfOne, JButton viewButton, JButton addButton,
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

    // EFFECTS: sets the action listeners of all the buttons in the inventory page
    private void addALToAllFields(JButton viewInventoryButton, JButton addItemButton, JButton removeItemButton,
                                  JButton editItemButton, JComboBox inventoryComboBox, JComboBox itemsComboBox,
                                  JTextField itemName, JTextField itemCode, JTextField number, JTextField priceOfOne,
                                  JButton viewButton, JButton addButton, JButton removeButton, JButton saveButton) {
        addALToViewInventoryButton(viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton,
                removeButton, saveButton);

        addALToAddItemButton(viewInventoryButton, addItemButton, removeItemButton, editItemButton, inventoryComboBox,
                itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton, removeButton, saveButton);

        addALToRemoveItemButton(viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton,
                removeButton, saveButton);

        addALToEditItemButton(viewInventoryButton, addItemButton, removeItemButton, editItemButton, inventoryComboBox,
                itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton, removeButton, saveButton);

        addALToInventoryCBox(inventoryComboBox, itemsComboBox);

        addALToItemsCBox(inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne);

        addALToViewButton(viewInventoryButton, addItemButton, removeItemButton, editItemButton, inventoryComboBox,
                itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton, removeButton, saveButton);

        addALToAddButton(viewInventoryButton, addItemButton, removeItemButton, editItemButton, inventoryComboBox,
                itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton, removeButton, saveButton);


        addALToRemoveButton(viewInventoryButton, addItemButton, removeItemButton, editItemButton, inventoryComboBox,
                itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton, removeButton, saveButton);

        addALToSaveButton(viewInventoryButton, addItemButton, removeItemButton, editItemButton, inventoryComboBox,
                itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton, addButton, removeButton, saveButton);
    }

    // EFFECTS: adds and sets the function of the save button
    private void addALToSaveButton(JButton viewInventoryButton, JButton addItemButton, JButton removeItemButton,
                                   JButton editItemButton, JComboBox inventoryComboBox, JComboBox itemsComboBox,
                                   JTextField itemName, JTextField itemCode, JTextField number, JTextField priceOfOne,
                                   JButton viewButton, JButton addButton, JButton removeButton, JButton saveButton) {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Inventory inventory = superMarket.getNeededInventory((String) inventoryComboBox.getSelectedItem());
                    Item selectedItem = inventory.getItems().get(itemsComboBox.getSelectedIndex());
                    int code = Integer.parseInt(itemCode.getText());
                    int num = Integer.parseInt(number.getText());
                    int price = Integer.parseInt(priceOfOne.getText());

                    if (superMarket.giveItemByCode(code) != null) {
                        JOptionPane.showMessageDialog(null,
                                "Item with the same code already exists");
                    } else {
                        changeItem(selectedItem, itemName.getText(), code, num, price, viewInventoryButton,
                                addItemButton, removeItemButton, editItemButton, inventoryComboBox,
                                itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton,
                                addButton, removeButton, saveButton);
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Entries");
                }
            }
        });
    }

    // EFFECTS: adds and sets the function of the remove button
    private void addALToRemoveButton(JButton viewInventoryButton, JButton addItemButton, JButton removeItemButton,
                                     JButton editItemButton, JComboBox inventoryComboBox, JComboBox itemsComboBox,
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

    // EFFECTS: adds and sets the function of the add button
    private void addALToAddButton(JButton viewInventoryButton, JButton addItemButton, JButton removeItemButton,
                                  JButton editItemButton, JComboBox inventoryComboBox, JComboBox itemsComboBox,
                                  JTextField itemName, JTextField itemCode, JTextField number, JTextField priceOfOne,
                                  JButton viewButton, JButton addButton, JButton removeButton, JButton saveButton) {
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
                        JOptionPane.showMessageDialog(null, "Item with that code already exists");
                    }
                    setToDefaultInventoryPage(viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                            inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton,
                            addButton, removeButton, saveButton);
                    JOptionPane.showMessageDialog(null, "Item Added!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Entries");
                }
            }
        });
    }

    // EFFECTS: adds and sets the function of the view button
    private void addALToViewButton(JButton viewInventoryButton, JButton addItemButton, JButton removeItemButton,
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

    // EFFECTS: adds and sets the function of the items comboBox
    private void addALToItemsCBox(JComboBox inventoryComboBox, JComboBox itemsComboBox, JTextField itemName,
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

    // EFFECTS: adds and sets the function of the inventory comboBox
    private void addALToInventoryCBox(JComboBox inventoryComboBox, JComboBox itemsComboBox) {
        inventoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inventoryName = (String) inventoryComboBox.getSelectedItem();
                Inventory inventory = superMarket.getNeededInventory(inventoryName);
                updateItemsComboBox(itemsComboBox, inventory);
            }
        });
    }

    // EFFECTS: adds and sets the function of the edit item button
    private void addALToEditItemButton(JButton viewInventoryButton, JButton addItemButton, JButton removeItemButton,
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

    // EFFECTS: adds and sets the function of the remove item button
    private void addALToRemoveItemButton(JButton viewInventoryButton, JButton addItemButton, JButton removeItemButton,
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

    // EFFECTS: adds and sets the function of the add item button
    private void addALToAddItemButton(JButton viewInventoryButton, JButton addItemButton, JButton removeItemButton,
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

    // EFFECTS: adds and sets the function of the view inventory button
    private void addALToViewInventoryButton(JButton viewInventoryButton, JButton addItemButton,
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


    // EFFECTS: makes the item based ton the inputs given from the text field
    private void changeItem(Item selectedItem, String name, int code, int num, int price,
                            JButton viewInventoryButton, JButton addItemButton, JButton removeItemButton,
                            JButton editItemButton, JComboBox inventoryComboBox, JComboBox itemsComboBox,
                            JTextField itemName, JTextField itemCode, JTextField number, JTextField priceOfOne,
                            JButton viewButton, JButton addButton, JButton removeButton,
                            JButton saveButton) {
        selectedItem.setName(name);
        selectedItem.setItemCode(code);
        selectedItem.setNumber(num);
        selectedItem.setPriceOfOne(price);
        setToDefaultInventoryPage(viewInventoryButton, addItemButton, removeItemButton, editItemButton,
                inventoryComboBox, itemsComboBox, itemName, itemCode, number, priceOfOne, viewButton,
                addButton, removeButton, saveButton);
        JOptionPane.showMessageDialog(null, "Item Added!");
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

    // EFFECTS: updates the inventory comboBox based on the inventories present
    private void updateInventoryComboBox(JComboBox inventoryComboBox) {
        inventoryComboBox.removeAll();
        for (Inventory i : superMarket.getInventoriesList()) {
            inventoryComboBox.addItem(i.getInventoryName());
        }
    }

    // EFFECTS: updates the items comboBox based on the inventory given
    private void updateItemsComboBox(JComboBox itemsComboBox, Inventory inventory) {
        itemsComboBox.removeAllItems();
        for (Item i : inventory.getItems()) {
            itemsComboBox.addItem(i.getName());
        }
    }

    // EFFECTS: displays the inventory given in the form of table
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

    // EFFECTS: sets the action listener for the close button of the temporary pages
    private void setCloseButtonForJustDisposePages(JFrame frame, JButton close) {
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    // EFFECTS: creates the view inventory page with the tables
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

    // EFFECTS: initializes a window with exit operation as dispose

    private void initializeDisposableWindow(JFrame frame) {
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
