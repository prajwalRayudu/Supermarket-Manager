package ui.commonpages;

import model.Employee;
import model.SuperMarket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Password change page, this class consists of the windows which deals with the change password function
public class PasswordChangePage extends JFrame {
    // Fields
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 750;
    private SuperMarket superMarket;


    // Constructor
    // EFFECTS: Initializes the password change page with the given supermarket and opens the required page
    public PasswordChangePage(SuperMarket superMarket) {
        this.superMarket = superMarket;
        openPasswordChangePage();
    }

    /**
     * // ALL OF SET PASSWORD PAGE IS BELOW THIS
     * // Set password page
     */

    // EFFECTS: makes the set password page with the required buttons and fields
    private void openPasswordChangePage() {
        Employee e = superMarket.searchEmployee(superMarket.getEmployeeLoggedIn());

        initializeDisposableWindow();

        JPanel start = new JPanel();
        start.setLayout(new GridLayout(0, 1));
        start.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        add(start, BorderLayout.NORTH);

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

        addEndButtonsToPasswordChange(e, oldPassword, newPassword, confirmation);

        setVisible(true);
        revalidate();
    }

    // EFFECTS: sets tool tips for the password fields
    private void setToolTipsForPasswordFields(JTextField oldPassword, JTextField newPassword, JTextField confirmation) {
        oldPassword.setToolTipText("enter old password");
        newPassword.setToolTipText("enter new password");
        confirmation.setToolTipText("passwords should match");
    }

    // EFFECTS: adds the buttons at the end of the page
    private void addEndButtonsToPasswordChange(Employee e, JTextField oldPassword,
                                               JTextField newPassword, JTextField confirmation) {
        JPanel end = new JPanel();
        end.setLayout(new GridLayout(1, 2));
        end.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        add(end, BorderLayout.PAGE_END);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setToolTipText("Cancel process");
        end.add(cancelButton);

        JButton submitButton = new JButton("Submit");
        submitButton.setToolTipText("Submit");
        end.add(submitButton);

        addActionListenersToPasswordChange(e, oldPassword,
                newPassword, confirmation, cancelButton, submitButton);
    }

    // EFFECTS: adds action listeners to the buttons in thepassword change page
    private void addActionListenersToPasswordChange(Employee e, JTextField oldPassword,
                                                    JTextField newPassword, JTextField confirmation,
                                                    JButton cancelButton, JButton submitButton) {
        setUpCloseButton(cancelButton);

        submitButton.addActionListener(new ActionListener() {
            Employee e1 = e;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (oldPassword.getText().equals(e1.getPassword())) {
                    if (newPassword.getText().equals(confirmation.getText())) {
                        e1.setPasscode(newPassword.getText());
                        JOptionPane.showMessageDialog(null, "Password successfully changed");
                    } else {
                        JOptionPane.showMessageDialog(null, "Passwords don't match");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "incorrect old password");
                }
                disposePage();
            }
        });
    }

    // EFFECTS: disposes the given frame and sets the visibility of the class frame to true
    private void disposePage() {
        dispose();
    }

    // EFFECTS: initializes a window with exit operation as dispose
    private void initializeDisposableWindow() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: sets the close button to the old employees page
    private void setUpCloseButton(JButton close) {
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disposePage();
            }
        });
    }
}
