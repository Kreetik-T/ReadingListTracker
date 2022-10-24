package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

// Array of Books

public class BookList extends ArrayList<Book> {
    // EFFECTS: constructs an empty array of books
    public BookList() {
        super();
    }

    // EFFECTS: sorts book list by progress (ratio of pages
    // read out of total) in descending order
    public void sortByProgress() {
        Collections.sort(this, new SortByProgress());
    }

    // EFFECTS: sorts book list by excitement to read the
    // book in descending order
    public void sortByExcitement() {
        Collections.sort(this, new SortByExcitement());
    }

    // EFFECTS: converts book list data to a JSON object
    public JSONObject toJsonObject() {
        JSONObject jsonList = new JSONObject();
        int index = 0;

        for (Book book : this) {
            JSONObject jsonBook = new JSONObject();
            jsonBook.put("Book Name", book.getBookName());
            jsonBook.put("Pages", book.getPages());
            jsonBook.put("Progress", book.getProgress());
            jsonBook.put("Location", book.getLocation());
            // Trouble storing doubles in json, so I converted to
            // int, and reconvert when reading
            jsonBook.put("Excited", (int) (book.getExcited() * 100));

            JSONObject jsonBookNotes = new JSONObject();
            int noteIndex = 0;
            for (String note : book.getNotes()) {
                jsonBookNotes.put(Integer.toString(noteIndex), note);
                noteIndex++;
            }
            jsonBook.put("Notes", jsonBookNotes);

            jsonList.put(Integer.toString(index), jsonBook);
            index++;
        }

        return jsonList;
    }


    // EFFECTS: adds book and adds event to log
    public boolean add(Book book) {
        EventLog.getInstance().logEvent(new Event("Added book: " + book.getBookName()));
        return super.add(book);
    }

    // EFFECTS: removes book and adds event to log
    public boolean remove(Book book) {
        EventLog.getInstance().logEvent(new Event("Removed book: " + book.getBookName()));
        return super.remove(book);
    }

    // EFFECTS: removes book and adds event to log
    public Book remove(int bookInt) {
        Book book = super.remove(bookInt);
        EventLog.getInstance().logEvent(new Event("Removed book: " + book.getBookName()));
        return book;
    }

    // EFFECTS: prints log when book list is closed
    public void close() {
        System.out.println("\n\nEVENT LOG\n");
        for (Event e: EventLog.getInstance()) {
            System.out.println(e.toString());
        }
    }
}
