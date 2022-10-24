package persistence;

import model.Book;
import model.BookList;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

// Citation for this code:
//
// ***************************************************************************************
// *    Title: JsonSerializationDemo\src\main\persistence\JsonWriter
// *    Author: Paul Carter
// *    Date: 16th October 2021
// *    Code version: 20210307
// *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// ***************************************************************************************/

// Represents a reader that reads book list from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads book list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public BookList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBookList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses book list from JSON object and returns it
    private BookList parseBookList(JSONObject jsonObject) {
        Iterator<String> keys = jsonObject.keys();
        BookList bookList = new BookList();

        while (keys.hasNext()) {
            Book newBook;
            JSONObject jsonBook = jsonObject.getJSONObject(keys.next());
            String name = (String) jsonBook.get("Book Name");
            int pages = (int) jsonBook.get("Pages");
            int progress = (int) jsonBook.get("Progress");
            String location = (String) jsonBook.get("Location");
            double excited = ((double) (int) jsonBook.get("Excited")) / 100.0;

            newBook = new Book(name, pages, progress, location, excited);

            JSONObject notes = (JSONObject) jsonBook.get("Notes");
            Iterator<String> noteKeys = notes.keys();
            while (noteKeys.hasNext()) {
                newBook.addNote(notes.get(noteKeys.next()).toString());
            }

            bookList.add(newBook);
        }

        return bookList;
    }
}