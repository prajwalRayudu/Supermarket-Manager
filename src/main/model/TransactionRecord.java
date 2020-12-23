package model;

import org.json.JSONObject;
import persistence.Converter;

// Transaction Record, consists of the receipt and the cashier name stored as the form of a transaction
public class TransactionRecord implements Converter {
    // Fields
    private Receipt receipt;
    private String cashierName;

    // Constructor
    // EFFECTS: Constructs the Object transaction record with given
    //          inputs (i.e. receipt, and cashierName)
    public TransactionRecord(Receipt receipt, String cashierName) {
        this.receipt = receipt;
        this.cashierName = cashierName;
    }

    // Methods
//
    // DOCUMENTATION
    // Used the toJson() method in Thingy class from the repository named JsonSerializationDemo to design this method
    // Repository link https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJsonConverter() {
        JSONObject object = new JSONObject();
        object.put("receipt", receipt.toJsonConverter());
        object.put("cashierName", cashierName);
        return object;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public String getCashierName() {
        return cashierName;
    }
}
