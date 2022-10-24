package model;

import java.util.Comparator;

// Allows for the book list to be sorted by the Comparator module

public class SortByProgress implements Comparator<Book> {

    // EFFECTS: produces an integer that the Comparator module can use to sort the list by book.progressRatio()
    public int compare(Book firstBook, Book secondBook) {
        return (int) (100 * (secondBook.progressRatio() - firstBook.progressRatio()));
    }
}
