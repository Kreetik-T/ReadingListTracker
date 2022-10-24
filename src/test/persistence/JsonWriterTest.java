package persistence;

import model.Book;
import model.BookList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Citation for this code:
//
// ***************************************************************************************
// *    Title: JsonSerializationDemo\src\test\persistence\JsonWriterTest
// *    Author: Paul Carter
// *    Date: 16th October 2021
// *    Code version: 20210307
// *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// ***************************************************************************************/

class JsonWriterTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            BookList bookList = new BookList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterGeneralBookList() {
        try {
            BookList bookList = new BookList();
            Book book0;
            Book book1;
            book0 = new Book("Superforecasting", 277, 100, "main folder", 4.0);
            book1 = new Book("Kafka on the shore", 500, 400, "shelf above the bed", 3.0);
            book0.addNote("data on page 80");
            bookList.add(book0);
            bookList.add(book1);
            JsonWriter writer = new JsonWriter("./data/testWriterBookList.json");
            writer.open();
            writer.write(bookList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterBookList.json");
            BookList answerBookList = reader.read();
            assertEquals(answerBookList.get(0).getBookName(), bookList.get(0).getBookName());
            assertEquals(answerBookList.get(1).getPages(), bookList.get(1).getPages());
            assertEquals(answerBookList.get(0).getProgress(), bookList.get(0).getProgress());
            assertEquals(answerBookList.get(0).getLocation(), bookList.get(0).getLocation());
            assertEquals(answerBookList.get(1).getExcited(), bookList.get(1).getExcited());
            assertEquals(answerBookList.get(0).getNotes().get(0), bookList.get(0).getNotes().get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}