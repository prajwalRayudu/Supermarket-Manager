package ui;

import model.SuperMarket;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// this class represents the superMarket login page of the supermarket
public class SuperMarketGUI extends JFrame {
    // Fields
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 750;

    private SuperMarket superMarket;
    private JPanel options;
    public JFrame billingFrame;

    // JSON Data saving related Fields
    private static final String JSON_STORE = "./data/supermarket.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Constructor
    // EFFECTS: Constructs a SuperMarketGUi makes a supermarket object and initiates the program with the mainMenu
    public SuperMarketGUI(String superMarketName) {
        super(superMarketName);
        superMarket = new SuperMarket(superMarketName);
        superMarket.setBillingInProcess(false);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        mainPage();
    }

    //Methods

    // EFFECTS: load the supermarket from file, constructs the frame and initializes the login page
    public void mainPage() {
        loadSuperMarket();

        setLayout(new BorderLayout());
        setSize(new Dimension(WIDTH, HEIGHT));

        createLoginPage();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: constructs the login page with all the buttons
    private void createLoginPage() {
        options = new JPanel();
        add(options, BorderLayout.CENTER);

        options.setLayout(null);

        JLabel userLabel = new JLabel("Username");
        JTextField username = new JTextField();
        createUsernameField(userLabel, username);

        JLabel passwordLabel = new JLabel("Password");
        JPasswordField password = new JPasswordField("Password");
        createPasswordField(passwordLabel, password);

        JButton button = new JButton("Login");
        button.setBounds(450, 350, 80, 30);
        button.setToolTipText("Press to Login");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processLogin(username.getText(), new String(password.getPassword()));
                username.setText("Username");
                password.setText("Password");
            }
        });
        options.add(button);
    }

    // EFFECTS: creates the username field in the login page
    private void createUsernameField(JLabel userLabel, JTextField username) {
        userLabel.setBounds(450, 225, 80, 35);
        options.add(userLabel);

        username.setBounds(450, 250, 200, 35);
        username.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (username.getText().equals("Username")) {
                    username.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (username.getText().equals("")) {
                    username.setText("Username");
                }
            }
        });
        options.add(username);
    }

    // EFFECTS: creates the password field in the login page
    private void createPasswordField(JLabel passwordLabel, JPasswordField password) {
        passwordLabel.setBounds(450, 285, 80, 35);
        options.add(passwordLabel);

        password.setBounds(450, 310, 200, 35);
        password.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if ((new String(password.getPassword())).equals("Password")) {
                    password.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if ((new String(password.getPassword())).equals("")) {
                    password.setText("Password");
                }
            }
        });
        options.add(password);
    }

    // EFFECTS: processes the login info and gets the user logged in if credentials are valid
    private void processLogin(String username, String password) {
        boolean validLogin = verifyLogin(username, password);
        if (!validLogin) {
            superMarket.setAccess(-1);
            JOptionPane.showMessageDialog(this, "Employee Credentials Wrong");
        }

        if ((superMarket.getEmployeeList().size() == 1) && (superMarket.getNumLogins() == 0) && validLogin) {
            processInitialPasswordReset();
        }

        if (validLogin) {
            superMarket.setNumLogins(superMarket.getNumLogins() + 1);
            getLoggedIn();
        }
    }

    // EFFECTS: gets the user logged in to their respective pages.
    private void getLoggedIn() {
        if (superMarket.isBillingInProcess()) {
            setVisible(false);
            LevelThree l3 = new LevelThree(superMarket, this, billingFrame);
            l3.billingFrame = billingFrame;
        } else {

            processNormalLogin();
        }
    }

    // EFFECTS: Continues the login process of the user
    private void processNormalLogin() {
        switch (superMarket.getAccess()) {
            case 1:
                setVisible(false);
                new LevelOne(superMarket, this);
                break;

            case 2:
                setVisible(false);
                new LevelTwo(superMarket, this);
                break;

            case 3:
                setVisible(false);
                new LevelThree(superMarket, this);
                break;

            case 4:
                setVisible(false);
                new LevelFour(superMarket, this);
                break;

            case 5:
                setVisible(false);
                new LevelFive(superMarket, this);
                break;

        }
    }

    // EFFECTS: processes the initial password reset of the admin
    private void processInitialPasswordReset() {
        String newPassword = JOptionPane.showInputDialog(this, "Enter new Password:");
        superMarket.getEmployeeList().get(0).setPasscode(newPassword);
    }

    // EFFECTS: verifies the login input given by the user with the employee list
    private boolean verifyLogin(String name, String password) {

        boolean validLogin = superMarket.verifyEmployee(name, password);

        if (validLogin && !superMarket.isBillingInProcess()) {
            superMarket.setEmployeeLoggedIn(name);
        } else if (validLogin) {
            if (superMarket.searchEmployee(name).getAccessLevel() != 3) {
                JOptionPane.showMessageDialog(this, "Only Senior Cashier Allowed");
                return false;
            }
        }

        return validLogin;
    }

    // JSON Methods
    // EFFECTS: saves the workroom to file
    public void saveSuperMarket() {
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
    public void loadSuperMarket() {
        try {
            superMarket = jsonReader.read();
            System.out.println("Loaded " + superMarket.getSuperMarketName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    public SuperMarket getSuperMarket() {
        return superMarket;
    }
}
