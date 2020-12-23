package ui.commonpages;

import model.Employee;
import model.SuperMarket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Employee management page, this class consists of the windows for the management page
public class EmployeeMPage extends JFrame {
    // Fields
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 750;
    private SuperMarket superMarket;
    private List<Employee> accessibleEmployees;
    private JFrame mainFrame;

    // Constructor
    // EFFECTS: constructs an employee page with the given supermarket, employees and the given frame
    public EmployeeMPage(SuperMarket superMarket, List<Employee> accessibleEmployees, JFrame mainFrame) {
        this.superMarket = superMarket;
        this.accessibleEmployees = accessibleEmployees;
        this.mainFrame = mainFrame;

        openEmployeeManagementPage();

    }

    /**
     * // ALL OF EMPLOYEE MANAGEMENT PAGE BELOW THIS
     *
     * // Employee Management Page
     */

    // EFFECTS: creates the employee management page with the required buttons and text fields
    private void openEmployeeManagementPage() {
        initializeExitWindows();


        JPanel empOptions = new JPanel();
        empOptions.setLayout(new GridLayout(9, 0));
        empOptions.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        add(empOptions, BorderLayout.CENTER);

        JPanel upperRow = new JPanel();
        upperRow.setLayout(new GridLayout(1, 4));
        upperRow.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        add(upperRow, BorderLayout.NORTH);

        JPanel lowerRow = new JPanel();
        lowerRow.setLayout(new GridLayout(1, 4));
        lowerRow.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        add(lowerRow, BorderLayout.SOUTH);

        // Buttons/Fields/Boxes initializations
        makeAllFields(empOptions, upperRow, lowerRow);

        setVisible(true);
        revalidate();

        // Return to main menu page
        makeFinishButton(lowerRow);
    }

    // EFFECTS: makes a finish button and adds it to the panel
    private void makeFinishButton(JPanel lowerRow) {
        JButton finish = new JButton("Return To Menu");
        finish.setBackground(new Color(0xFD0303));
        finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                mainFrame.setVisible(true);
            }
        });
        lowerRow.add(finish);
    }

    // EFFECTS: makes all the buttons and initializes them
    private void makeAllFields(JPanel empOptions, JPanel upperRow, JPanel lowerRow) {
        JButton addButton = new JButton("Add new Employee");
        JButton editButton = new JButton("Edit Employee");
        JButton removeButton = new JButton("Remove Employee");

        JLabel selectEmployee = new JLabel("Select Employee");
        JComboBox employeesJComboBox = new JComboBox();

        // Input Fields
        JLabel nameL = new JLabel("Name:");
        JTextField name = new JTextField();
        JLabel designationL = new JLabel("Designation:");
        JTextField designation = new JTextField();
        JLabel accessLevelL = new JLabel("Access Level:");
        JTextField accessLevel = new JTextField();
        JLabel passcodeL = new JLabel("Password:");
        JTextField passcode = new JTextField();


        JButton add = new JButton("Add");
        JButton save = new JButton("Save");
        JButton remove = new JButton("Remove");

        initializeAllFields(empOptions, upperRow, lowerRow, addButton, editButton, removeButton, selectEmployee,
                employeesJComboBox, nameL, name, designationL, designation, accessLevelL, accessLevel, passcodeL,
                passcode, add, save, remove);

        // ACTION LISTENERS
        addAllAL(addButton, editButton, removeButton, employeesJComboBox, name, designation, accessLevel,
                passcode, add, save, remove);
    }

    // EFFECTS: adds the buttons to panels and sets the action listeners
    private void initializeAllFields(JPanel empOptions, JPanel upperRow, JPanel lowerRow, JButton addButton,
                                     JButton editButton, JButton removeButton, JLabel selectEmployee,
                                     JComboBox employeesJComboBox, JLabel nameL, JTextField name,
                                     JLabel designationL, JTextField designation, JLabel accessLevelL,
                                     JTextField accessLevel, JLabel passcodeL, JTextField passcode, JButton add,
                                     JButton save, JButton remove) {
        addAllButtonsToPanels(empOptions, upperRow, lowerRow, addButton, editButton, removeButton, selectEmployee,
                employeesJComboBox, nameL, name, designationL, designation, accessLevelL, accessLevel, passcodeL,
                passcode, add, save, remove);

        setToolTipsForRequitedFields(addButton, editButton, removeButton, add, save);

        for (int e = 0; e < accessibleEmployees.size(); e++) {
            employeesJComboBox.addItem(accessibleEmployees.get(e).getName());
        }
        Employee selected = superMarket.searchEmployee((String) employeesJComboBox.getSelectedItem());


        if (selected != null) {
            name.setText(selected.getName());
            designation.setText(selected.getDesignation());
            accessLevel.setText(Integer.toString(selected.getAccessLevel()));
            passcode.setText(selected.getPassword());
        }

        setToDefaultEmployeePage(removeButton, editButton, addButton,
                employeesJComboBox, add, remove, save, name, designation, accessLevel, passcode);
    }

    // EFFECTS: sets the tool tips for all the buttons
    private void setToolTipsForRequitedFields(JButton addButton, JButton editButton, JButton removeButton,
                                              JButton add, JButton save) {
        addButton.setToolTipText("Add Employee");
        editButton.setToolTipText("Edit Employee");
        removeButton.setToolTipText("Remove Employee");

        add.setToolTipText("Add Employee");
        save.setToolTipText("Save Changes");
        save.setToolTipText("Remove");
    }

    // EFFECTS: adds all the fields to the respective panels
    private void addAllButtonsToPanels(JPanel empOptions, JPanel upperRow, JPanel lowerRow, JButton addButton,
                                       JButton editButton, JButton removeButton, JLabel selectEmployee,
                                       JComboBox employeesJComboBox, JLabel nameL, JTextField name,
                                       JLabel designationL, JTextField designation, JLabel accessLevelL,
                                       JTextField accessLevel, JLabel passcodeL, JTextField passcode,
                                       JButton add, JButton save, JButton remove) {
        upperRow.add(addButton);
        upperRow.add(editButton);

        if (superMarket.getAccess() == 5) {
            upperRow.add(removeButton);
        }

        empOptions.add(selectEmployee);
        empOptions.add(employeesJComboBox);
        empOptions.add(nameL);
        empOptions.add(name);
        empOptions.add(designationL);
        empOptions.add(designation);
        empOptions.add(accessLevelL);
        empOptions.add(accessLevel);

        if (superMarket.getAccess() == 5) {
            empOptions.add(passcodeL);
            empOptions.add(passcode);
        }

        lowerRow.add(add);
        lowerRow.add(save);

        if (superMarket.getAccess() == 5) {
            lowerRow.add(remove);
        }
    }

    // EFFECTS: add  the action listeners for all fields
    private void addAllAL(JButton addButton, JButton editButton, JButton removeButton,
                          JComboBox employeesJComboBox, JTextField name, JTextField designation,
                          JTextField accessLevel, JTextField passcode, JButton add, JButton save,
                          JButton remove) {
        addALToAddButtonFirstRow(addButton, editButton, removeButton, employeesJComboBox, name, designation,
                accessLevel, passcode, add, save, remove);

        addALToEditButtonFirstRow(addButton, editButton, removeButton, employeesJComboBox, name, designation,
                accessLevel, passcode, add, save, remove);

        addALToRemoveButtonFirstRow(addButton, editButton, removeButton, employeesJComboBox, name, designation,
                accessLevel, passcode, add, save, remove);

        addALEmployeeComboBox(employeesJComboBox, name, designation, accessLevel, passcode);

        addALAddButton(addButton, editButton, removeButton, employeesJComboBox, name, designation, accessLevel,
                passcode, add, save, remove);

        addALSaveButton(addButton, editButton, removeButton, employeesJComboBox, name, designation, accessLevel,
                passcode, add, save, remove);

        addALRemoveButton(addButton, editButton, removeButton, employeesJComboBox, name, designation, accessLevel,
                passcode, add, save, remove);
    }

    // EFFECTS: adds and sets the action listener for the add button at the start of the page
    private void addALToAddButtonFirstRow(JButton addButton, JButton editButton, JButton removeButton,
                                          JComboBox employeesJComboBox, JTextField name, JTextField designation,
                                          JTextField accessLevel, JTextField passcode, JButton add, JButton save,
                                          JButton remove) {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setToDefaultEmployeePage(removeButton, editButton, addButton,
                        employeesJComboBox, add, remove, save, name, designation, accessLevel, passcode);
                addButton.setBackground(new Color(0xF8F805));
                add.setEnabled(true);
                name.setEnabled(true);
                designation.setEnabled(true);
                accessLevel.setEnabled(true);
                passcode.setEnabled(true);
            }
        });
    }

    // EFFECTS: adds and sets the action listener for the edit button at the start of the page
    private void addALToEditButtonFirstRow(JButton addButton, JButton editButton, JButton removeButton,
                                           JComboBox employeesJComboBox, JTextField name, JTextField designation,
                                           JTextField accessLevel, JTextField passcode, JButton add, JButton save,
                                           JButton remove) {
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setToDefaultEmployeePage(removeButton, editButton, addButton,
                        employeesJComboBox, add, remove, save, name, designation, accessLevel, passcode);
                editButton.setBackground(new Color(0xF8F805));
                employeesJComboBox.setEnabled(true);
                save.setEnabled(true);
                name.setEnabled(true);
                designation.setEnabled(true);
                accessLevel.setEnabled(true);
                passcode.setEnabled(true);
            }
        });
    }

    // EFFECTS: adds and sets the action listener for the remove button at the start of the page
    private void addALToRemoveButtonFirstRow(JButton addButton, JButton editButton, JButton removeButton,
                                             JComboBox employeesJComboBox, JTextField name, JTextField designation,
                                             JTextField accessLevel, JTextField passcode, JButton add,
                                             JButton save, JButton remove) {
        removeButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setToDefaultEmployeePage(removeButton, editButton, addButton,
                        employeesJComboBox, add, remove, save, name, designation, accessLevel, passcode);
                removeButton.setBackground(new Color(0xF8F805));
                employeesJComboBox.setEnabled(true);
                remove.setEnabled(true);

            }
        }));
    }

    // EFFECTS: adds and sets the action listener for the employees comboBox
    private void addALEmployeeComboBox(JComboBox employeesJComboBox, JTextField name, JTextField designation,
                                       JTextField accessLevel, JTextField passcode) {
        employeesJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee e1 = superMarket.searchEmployee((String) employeesJComboBox.getSelectedItem());
                if (e1 != null) {
                    name.setText(e1.getName());
                    designation.setText(e1.getDesignation());
                    accessLevel.setText(Integer.toString(e1.getAccessLevel()));
                    passcode.setText(e1.getPassword());
                }
            }
        });
    }

    // EFFECTS: adds and sets the action listener for the add button
    private void addALAddButton(JButton addButton, JButton editButton, JButton removeButton,
                                JComboBox employeesJComboBox, JTextField name, JTextField designation,
                                JTextField accessLevel, JTextField passcode, JButton add, JButton save,
                                JButton remove) {
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addEmployeeFromFields(name, designation, accessLevel, passcode);
                    updateAccessibleEmployees();
                    JOptionPane.showMessageDialog(null, "Employee Added!");
                    setToDefaultEmployeePage(removeButton, editButton, addButton,
                            employeesJComboBox, add, remove, save, name, designation, accessLevel, passcode);
                    updateEmployees(employeesJComboBox);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Entries");
                }
            }
        });
    }

    // EFFECTS: makes an employee using the data from text fields and adds it to the employee list
    private void addEmployeeFromFields(JTextField name, JTextField designation, JTextField accessLevel,
                                       JTextField passcode) {
        Employee em;
        if (superMarket.getAccess() == 5) {
            em = new Employee(name.getText(), designation.getText(),
                    Integer.parseInt(accessLevel.getText()), passcode.getText());
        } else {
            em = new Employee(name.getText(), designation.getText(),
                    Integer.parseInt(accessLevel.getText()), name.getText());
            JOptionPane.showMessageDialog(null,"The default password of "
                    + "the employee is their name");
        }
        superMarket.getEmployeeList().add(em);
    }

    // EFFECTS: adds and sets the action listener for the save button
    private void addALSaveButton(JButton addButton, JButton editButton, JButton removeButton,
                                 JComboBox employeesJComboBox, JTextField name, JTextField designation,
                                 JTextField accessLevel, JTextField passcode, JButton add, JButton save,
                                 JButton remove) {
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee returned = superMarket.searchEmployee(((String) employeesJComboBox.getSelectedItem()));

                try {
                    returned.setName(name.getText());
                    returned.setDesignation(designation.getText());
                    returned.setAccessLevel(Integer.parseInt(accessLevel.getText()));

                    if (superMarket.getAccess() == 5) {
                        returned.setPasscode(passcode.getText());
                    }

                    JOptionPane.showMessageDialog(null, "Changes Saved");
                    setToDefaultEmployeePage(removeButton, editButton, addButton,
                            employeesJComboBox, add, remove, save, name, designation, accessLevel, passcode);
                    updateEmployees(employeesJComboBox);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid entries");
                }
            }
        });
    }

    // EFFECTS: adds and sets the action listener for the remove button
    private void addALRemoveButton(JButton addButton, JButton editButton, JButton removeButton,
                                   JComboBox employeesJComboBox, JTextField name, JTextField designation,
                                   JTextField accessLevel, JTextField passcode, JButton add, JButton save,
                                   JButton remove) {
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                superMarket.removeEmployee((String) employeesJComboBox.getSelectedItem());
                updateAccessibleEmployees();
                JOptionPane.showMessageDialog(null, "Employed Removed");
                setToDefaultEmployeePage(removeButton, editButton, addButton,
                        employeesJComboBox, add, remove, save, name, designation, accessLevel, passcode);
                updateEmployees(employeesJComboBox);
            }
        });
    }

    // EFFECTS: initializes the given frame with exit operation as exit
    private void initializeExitWindows() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    // EFFECTS: sets the employee page to default
    private void setToDefaultEmployeePage(JButton removeButton, JButton editButton, JButton addButton,
                                          JComboBox employeesJComboBox, JButton add, JButton remove,
                                          JButton save, JTextField name, JTextField designation,
                                          JTextField accessLevel, JTextField passcode) {
        removeButton.setBackground(null);
        editButton.setBackground(null);
        addButton.setBackground(null);
        employeesJComboBox.setEnabled(false);
        add.setEnabled(false);
        remove.setEnabled(false);
        save.setEnabled(false);
        name.setText("");
        designation.setText("");
        accessLevel.setText("");
        passcode.setText("");
        name.setEnabled(false);
        designation.setEnabled(false);
        accessLevel.setEnabled(false);
        passcode.setEnabled(false);
    }

    // EFFECTS: updates the given comboBox with the list of employees
    private void updateEmployees(JComboBox employeeComboBox) {
        employeeComboBox.removeAllItems();
        for (Employee e : accessibleEmployees) {
            employeeComboBox.addItem(e.getName());
        }
    }

    // EFFECTS: updates the list of accessible employees with the new changes
    private void updateAccessibleEmployees() {
        accessibleEmployees = superMarket.getEmployeesBelowAccess(superMarket.getAccess());
    }


}
