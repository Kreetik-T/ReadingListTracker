package model;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Test class for BookList object

public class BookListTest {
    private BookList myBooks;
    private ArrayList<Book> assertAnswerList;

    private Book mostProgress;
    private Book leastProgress;
    private Book middleProgress;

    private Book mostExcited;
    private Book leastExcited;
    private Book middleExcited;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void runsBefore() {
        myBooks = new BookList();
        assertAnswerList = new ArrayList<Book>();
        System.setOut(new PrintStream(outputStreamCaptor));

        mostProgress = new Book("A", 200, 199, "shelf", 4.0);
        leastProgress = new Book("B", 500, 0, "shelf", 4.0);
        middleProgress = new Book("C", 280, 110, "shelf", 4.0);

        mostExcited = new Book("A", 200, 0, "shelf", 4.8);
        leastExcited = new Book("B", 200, 0, "shelf", 2.0);
        middleExcited = new Book("C", 200, 0, "shelf", 3.7);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void sortByProgressAlreadySortedTest() {
        myBooks.add(middleProgress);
        assertAnswerList.add(middleProgress);

        myBooks.sortByProgress();

        // Single element sorting test (no change expected)
        assertEquals(assertAnswerList, myBooks);

        myBooks.add(leastProgress);
        assertAnswerList.add(leastProgress);

        myBooks.sortByProgress();
        // Two elements sorting test (no change expected)
        assertEquals(assertAnswerList, myBooks);
    }

    @Test
    void sortByProgressOppositeOrderTest() {
        myBooks.add(leastProgress);
        myBooks.add(middleProgress);
        myBooks.add(mostProgress);

        assertAnswerList.add(mostProgress);
        assertAnswerList.add(middleProgress);
        assertAnswerList.add(leastProgress);

        myBooks.sortByProgress();

        // Expected: order reversed
        assertEquals(assertAnswerList, myBooks);
    }

    @Test
    void sortByExcitementAlreadySortedTest() {
        myBooks.add(middleExcited);
        assertAnswerList.add(middleExcited);

        myBooks.sortByExcitement();

        // Single element sorting test (no change expected)
        assertEquals(assertAnswerList, myBooks);

        myBooks.add(leastExcited);
        assertAnswerList.add(leastExcited);

        myBooks.sortByExcitement();
        // Two elements sorting test (no change expected)
        assertEquals(assertAnswerList, myBooks);
    }

    @Test
    void sortByExcitementOppositeOrderTest() {
        myBooks.add(leastExcited);
        myBooks.add(middleExcited);
        myBooks.add(mostExcited);

        assertAnswerList.add(mostExcited);
        assertAnswerList.add(middleExcited);
        assertAnswerList.add(leastExcited);

        myBooks.sortByExcitement();

        // Expected: order reversed
        assertEquals(assertAnswerList, myBooks);
    }

    @Test
    void toJsonObjectTest() {
        myBooks.add(mostExcited);
        myBooks.add(middleProgress);

        JSONObject testJson = myBooks.toJsonObject();
        JSONObject answerJson = new JSONObject();
        JSONObject mostExcitedJson = new JSONObject();
        JSONObject middleProgressJson = new JSONObject();
        JSONObject notesJson = new JSONObject();

        mostExcitedJson.put("Book Name", mostExcited.getBookName());
        mostExcitedJson.put("Pages", mostExcited.getPages());
        mostExcitedJson.put("Progress", mostExcited.getProgress());
        mostExcitedJson.put("Location", mostExcited.getLocation());
        mostExcitedJson.put("Excited", (int) (mostExcited.getExcited() * 100));
        mostExcitedJson.put("Notes", notesJson);

        middleProgressJson.put("Book Name", middleProgress.getBookName());
        middleProgressJson.put("Pages", middleProgress.getPages());
        middleProgressJson.put("Progress", middleProgress.getProgress());
        middleProgressJson.put("Location", middleProgress.getLocation());
        middleProgressJson.put("Excited", (int) (middleProgress.getExcited() * 100));
        middleProgressJson.put("Notes", notesJson);

        answerJson.put("0",mostExcitedJson);
        answerJson.put("1", middleProgressJson);

        assertEquals(answerJson.toString(), testJson.toString());
    }

    @Test
    void addTest() {
        myBooks.add(mostProgress);
        String eventString = EventLog.getInstance().iterator().next().toString();
        assertTrue(eventString.contains("Added book: A"));
    }

    @Test
    void removeTest() {
        myBooks.add(mostProgress);
        myBooks.remove(mostProgress);
        myBooks.close();
        assertTrue(outputStreamCaptor.toString().trim().contains("Removed book: A"));

        myBooks.add(mostProgress);
        myBooks.remove(0);
        myBooks.close();
        assertTrue(outputStreamCaptor.toString().trim().contains("Removed book: A"));
    }

    @Test
    void closeTest() {
        myBooks.add(mostExcited);
        myBooks.remove(mostExcited);
        myBooks.close();
        assertTrue(outputStreamCaptor.toString().trim().contains("EVENT LOG"));
    }
}
