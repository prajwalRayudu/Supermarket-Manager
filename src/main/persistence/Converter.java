package persistence;

import org.json.JSONObject;

// DOCUMENTATION
// Used the Writable interface from the repository named JsonSerializationDemo to design this class
// Repository link https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
//
// Converter, the common methods among all the model classes which convert to Json format
public interface Converter {
    // EFFECTS: Converts this to a JSONObject
    JSONObject toJsonConverter();
}
