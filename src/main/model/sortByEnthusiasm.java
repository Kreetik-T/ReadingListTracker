package main.model;

import java.util.Comparator;

public class sortByEnthusiasm implements Comparator<Book> {
    public int compare(Book firstBook, Book secondBook) {
        return (int) (100*(secondBook.getExcited() - firstBook.getExcited()));
    }
}
