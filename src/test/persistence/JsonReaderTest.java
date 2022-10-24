package persistence;

import model.Book;
import model.BookList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Citation for this code:
//
// ***************************************************************************************
// *    Title: JsonSerializationDemo\src\test\persistence\JsonReaderTest
// *    Author: Paul Carter
// *    Date: 16th October 2021
// *    Code version: 20210307
// *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// ***************************************************************************************/

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            BookList bookList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderGeneralBookList() {
        JsonReader reader = new JsonReader("./data/testBookList.json");
        try {
            BookList bookList = reader.read();
            Book book0;
            Book book1;
            book0 = new Book("Superforecasting", 277, 100, "main folder", 4.0);
            book1 = new Book("Kafka on the shore", 500, 400, "shelf above the bed", 3.0);
            book0.addNote("data on page 80");
            BookList answerBookList = new BookList();
            answerBookList.add(book0);
            answerBookList.add(book1);

            assertEquals(answerBookList.get(0).getBookName(), bookList.get(0).getBookName());
            assertEquals(answerBookList.get(1).getPages(), bookList.get(1).getPages());
            assertEquals(answerBookList.get(0).getProgress(), bookList.get(0).getProgress());
            assertEquals(answerBookList.get(0).getLocation(), bookList.get(0).getLocation());
            assertEquals(answerBookList.get(1).getExcited(), bookList.get(1).getExcited());
            assertEquals(answerBookList.get(0).getNotes().get(0), bookList.get(0).getNotes().get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}