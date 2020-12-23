package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeTest {
    // Fields
    Employee e1;

    @BeforeEach
    public void setUp() {
        e1 = new Employee("Mad", "Supervisor", 3, "qwerty");
    }

    @Test
    public void testConstructor() {
        assertEquals("Mad", e1.getName());
        assertEquals("Supervisor", e1.getDesignation());
        assertEquals(3, e1.getAccessLevel());
        assertEquals("qwerty", e1.getPassword());
    }

    @Test
    public void testToJsonConverter() {
        JSONObject object = e1.toJsonConverter();

        assertEquals("Mad", object.getString("name"));
        assertEquals("Supervisor", object.getString("designation"));
        assertEquals(3, object.getInt("accessLevel"));
        assertEquals("qwerty", object.getString("password"));
    }

    @Test
    public void testSetName() {
        e1.setName("Mad2");

        assertEquals("Mad2", e1.getName());
    }

    @Test
    public void testSetPasscodeCan() {
        Employee e2 = new Employee("Mad1", "admin", 5, "qwerty");
        e2.setPasscode("New");

        assertEquals("New", e2.getPassword());
    }

    @Test
    public void testSetPasscodeCannot() {
        e1.setPasscode("New");

        assertEquals("qwerty", e1.getPassword());
    }

    @Test
    public void testSetDesignation() {
        e1.setDesignation("Cashier");

        assertEquals("Cashier", e1.getDesignation());
    }

    @Test
    public void testSetAccessLevel() {
        e1.setAccessLevel(4);

        assertEquals(4, e1.getAccessLevel());
    }
}
