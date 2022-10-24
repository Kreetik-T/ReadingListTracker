package model;

import java.util.ArrayList;

// This class represents a book object.  A book object has a name, number of pages,
// the number of pages the user has read, a note on where the book is kept/stored,
// a list of notes a user has made about the book, and how excited the user has said they are to read the book.

public class Book {
    private final String bookName;
    private final double pages;
    private double progress;
    private final String location;
    private final double excited;
    private final ArrayList<String> notes;

    // REQUIRES: Book name must be <= 60 chars, progress must be less than number of pages,
    //           excited must be double between 0 and 5
    // EFFECTS: Constructs a book with given name, number of pages,
    //          pages read so far, where the book is kept/saved, and
    //          how excited the user is to read the book
    public Book(String bookName, int pages, int progress, String location, double excited) {
        this.bookName = bookName;
        this.pages = pages;
        this.progress = progress;
        this.location = location;
        this.excited = excited;
        this.notes = new ArrayList<String>();
    }

    // REQUIRES: number of pages read must be >= number of pages read so far
    // MODIFIES: this
    // EFFECTS: updates progress made
    public void updateProgress(double progress) {
        this.progress = progress;
    }

    // EFFECTS: produces a double between 0 and 1 (inclusive)
    //          by dividing pages read by total number of pages
    public double progressRatio() {
        return progress / pages;
    }

    // REQUIRES: note to be added must not be an empty string
    // MODIFIES: this
    // EFFECTS: Adds note to the book so user may store important
    //          ideas or quotes
    public void addNote(String note) {
        notes.add(note);
    }

    // REQUIRES: note to be removed must not be an empty string,
    //           note must be in the notes
    // MODIFIES: this
    // EFFECTS: Removes note with given index from notes
    public void removeNote(Integer noteIndex) {
        if (noteIndex < notes.size()) {
            notes.remove(notes.get(noteIndex)); // Explicit implementation of removal by object to avoid ambiguity
        }
    }

    // REQUIRES: note to be removed must not be an empty string
    // MODIFIES: this
    // EFFECTS: Removes note equal to given note from notes
    public void removeNote(String note) {
        notes.remove(note);
    }

    public String getBookName() {
        return bookName;
    }

    public double getPages() {
        return pages;
    }

    public double getProgress() {
        return progress;
    }

    public String getLocation() {
        return location;
    }

    public double getExcited() {
        return excited;
    }

    public ArrayList<String> getNotes() {
        return notes;
    }
}
