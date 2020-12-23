package persistence;

import model.SuperMarket;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// DOCUMENTATION
// Used the JsonWriter class from the repository named JsonSerializationDemo to design this class
// Repository link https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// JSON writer, converts the supermatket to Json format and stores it in the file
public class JsonWriter {
    // Fields
    private PrintWriter printWriter;
    private String fileLocation;
    private static final int indent = 4;

    // Constructor
    // EFFECTS: Constructs a JsonWriter with the given file location
    public JsonWriter(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    // Methods
//
    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void openFileToWrite() throws FileNotFoundException {
        File file = new File(fileLocation);
        printWriter = new PrintWriter(file);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file and writes string to file
    public void writeOnFile(SuperMarket s) {
        JSONObject object = s.toJsonConverter();
        printWriter.print(object.toString(indent));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void closeFile() {
        printWriter.close();
    }
}
