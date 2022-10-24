package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Primary test class for SortByProgress object

public class SortByProgressTest {
    private Book bookOne;
    private Book bookTwo;
    private Book bookThree;
    private SortByProgress comparator;

    @BeforeEach
    public void runBefore() {
        bookOne = new Book("One", 500, 235, "main folder", 3.5);
        bookTwo = new Book("Two", 1600, 112, "main folder", 5.0);
        bookThree = new Book("Three", 72, 71, "shelf", 2.0);
        comparator = new SortByProgress();
    }

    @Test
    public void compareTest() {
        assertEquals((int) (100 * (112.0 / 1600.0 - 235.0 / 500.0)),comparator.compare(bookOne, bookTwo));
        assertEquals((int) 0.0,comparator.compare(bookTwo, bookTwo));
        assertEquals((int) (100 * (112.0 / 1600.0 - 71.0 / 72.0)),comparator.compare(bookThree, bookTwo));
    }
}