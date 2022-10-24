package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Primary test class for book object

public class BookTest {
    private Book myBook;
    private ArrayList<String> assertAnswer;

    @BeforeEach
    void runsBefore() {
        myBook = new Book("Superforecasting", 263, 140, "Desktop, pdf folder", 4.0);
    }

    @Test
    void testConstructor() {
        assertEquals("Superforecasting", myBook.getBookName());
        assertEquals(263, myBook.getPages());
        assertEquals(140, myBook.getProgress());
        assertEquals("Desktop, pdf folder", myBook.getLocation());
        assertEquals(4.0, myBook.getExcited());
        assertTrue(myBook.getNotes().isEmpty());
    }

    @Test
    void TestUpdateProgress() {
        assertEquals(140.0, myBook.getProgress());
        myBook.updateProgress(200.0);
        assertEquals(200.0, myBook.getProgress());
    }

    @Test
    void TestProgressRatio() {
        assertEquals(140.0/263.0, myBook.progressRatio());

        myBook.updateProgress(180.0);
        assertEquals(180.0/263.0, myBook.progressRatio());
    }

    @Test
    void TestAddNote() {
        myBook.addNote("pg 80: Start with a base rate");
        assertAnswer = new ArrayList<String>();
        assertAnswer.add("pg 80: Start with a base rate");

        assertEquals(1, myBook.getNotes().size());
        assertEquals("pg 80: Start with a base rate", myBook.getNotes().get(0));
        assertEquals(myBook.getNotes(), assertAnswer);

        myBook.addNote("pg 110 Pretty diagram");
        assertAnswer.add("pg 110 Pretty diagram");

        assertEquals(2, myBook.getNotes().size());
        assertEquals("pg 80: Start with a base rate", myBook.getNotes().get(0));
        assertEquals("pg 110 Pretty diagram", myBook.getNotes().get(1));
        assertEquals(myBook.getNotes(), assertAnswer);
    }

    @Test
    void TestRemoveNote() {
        assertAnswer = new ArrayList<String>();

        myBook.addNote("pg 80: Base rate");
        assertEquals(1, myBook.getNotes().size());

        // Index out of bounds
        myBook.removeNote(100);
        assertEquals(1, myBook.getNotes().size());

        // Note not in list
        myBook.removeNote("pg80: Base rate");
        assertEquals(1, myBook.getNotes().size());

        // Remove by index
        myBook.removeNote(0);
        assertEquals(0, myBook.getNotes().size());

        myBook.addNote("pg 80: Base rate");
        myBook.addNote("loved page 120");
        myBook.addNote("Data on pg 131");
        assertEquals(3, myBook.getNotes().size());

        // Remove by value
        myBook.removeNote("loved page 120");
        assertAnswer.add("pg 80: Base rate");
        assertAnswer.add("Data on pg 131");

        assertEquals(2, myBook.getNotes().size());
        assertEquals("Data on pg 131", myBook.getNotes().get(1));
        assertEquals(myBook.getNotes(), assertAnswer);
    }
}
