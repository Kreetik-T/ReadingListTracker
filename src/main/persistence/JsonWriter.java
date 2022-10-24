package persistence;

import model.BookList;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Citation for this code:
//
// ***************************************************************************************
// *    Title: JsonSerializationDemo\src\main\persistence\JsonWriter
// *    Author: Paul Carter
// *    Date: 16th October 2021
// *    Code version: 20210307
// *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// ***************************************************************************************/

// Represents a writer that writes JSON representation of book list to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private final String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of book list to file
    public void write(BookList bookList) {
        JSONObject json = bookList.toJsonObject();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
