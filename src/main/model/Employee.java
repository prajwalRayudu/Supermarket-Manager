package model;


import org.json.JSONObject;
import persistence.Converter;

// Employee, consists of all the important elements of an employee, used by the superMarket class
public class Employee implements Converter {
    // Fields
    private String name;
    private String designation;
    private int accessLevel;
    private String password;

    // Constructor
    // EFFECTS: Constructs an employee with the given inputs
    //          (i.e. name, designation, accessLevel and password)
    public Employee(String name, String designation, int accessLevel, String password) {
        this.name = name;
        this.designation = designation;
        this.accessLevel = accessLevel;
        this.password = password;
    }

    // Methods

    // DOCUMENTATION
    // Used the toJson() method in Thingy class from the repository named JsonSerializationDemo to design this method
    // Repository link https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJsonConverter() {
        JSONObject object = new JSONObject();
        object.put("name", name);
        object.put("designation", designation);
        object.put("accessLevel", accessLevel);
        object.put("password", password);
        return object;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPasscode(String newPassword) {
        if (accessLevel == 5) {
            this.password = newPassword;
        }
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public String getPassword() {
        return password;
    }
}
