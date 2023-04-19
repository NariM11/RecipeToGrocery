package persistence;

import org.json.JSONObject;

//interface with a method to convert classes to JSON format, modeled based on JsonSerializationDemo file
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
