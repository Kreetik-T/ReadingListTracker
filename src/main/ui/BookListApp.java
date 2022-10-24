package main.ui;

import main.model.Book;
import main.model.sortByEnthusiasm;
import main.model.sortByProgress;
import sun.awt.util.IdentityArrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class BookListApp {
    private static ArrayList<Book> myBookList = new ArrayList<Book>();
    private static Scanner scanner = new Scanner(System.in);
    private static boolean updateCommandList = true;


    // EFFECTS: reads multiple words and stores it in variable passed
    private static String inputLine(String storeVar){
        storeVar += scanner.next();
        if (scanner.hasNextLine()){
            storeVar += scanner.nextLine();
        }
        return storeVar;
    }

    // REQUIRES: Book name must be <= 80 characters
    // MODIFIES: this
    // EFFECTS: add book to book list
    public static void addBook() {
        Book newBook;

        System.out.println("What is the book's name? ");
        String bookName = "";
        bookName = inputLine(bookName);

        System.out.println("How many pages are in the book? ");
        int pages = scanner.nextInt();

        System.out.println("How many pages have you read so far? ");
        int progress = scanner.nextInt();

        System.out.println("Where is the book kept/stored on your system? ");
        String location = "";
        location = inputLine(location);

        System.out.println("Finally, how excited are you to read the book on a scale of zero to five? ");
        double excited = scanner.nextDouble();

        newBook = new Book(bookName, pages, progress, location, excited);
        myBookList.add(newBook);

        System.out.println("Successfully added book to the list!");
    }

    // REQUIRES: index passed must be < (size of the book list - 1)
    // MODIFIES: this
    // EFFECTS: removes book of given index from book list
    public static void removeBook() {
        System.out.println("What is the serial # of the book you'd like to remove from the list? ");
        int bookIndex = scanner.nextInt() - 1;
        Book book = myBookList.get(bookIndex);
        myBookList.remove(book);

        System.out.println("\"" + book.getBookName() + "\" shall be missed.");
    }

    // EFFECTS: produces list of notes
    public static void showNotes() {
        System.out.println("What is the serial # of the book whose notes you'd like to view? ");
        int selectedIndex = scanner.nextInt();
        Book book = myBookList.get(selectedIndex-1);
        if (book.getNotes().size() == 0) {
            System.out.println("The book has no notes");
        } else {
            System.out.println("Notes for \"" + book.getBookName() + "\" :");
            System.out.println("S.no.     Note");
            for (int i = 0; i < book.getNotes().size(); i++) {
                System.out.println((i+1) + ".)    " + book.getNotes().get(i));
            }
        }
    }

    // REQUIRES: note is not empty string, book index < (size of book list - 1)
    // MODIFIES: note list of book at given index in list
    // EFFECTS: adds note to the book in the list at given index
    public static void addNote() {
        System.out.println("What is the serial # of the book to which you'd like to add a note? ");
        int selectedIndex = scanner.nextInt() - 1;
        System.out.println("Enter note below: ");
        String note = "";
        note = inputLine(note);
        myBookList.get(selectedIndex).addNote(note);

        System.out.println("Successfully added note!");
    }

    // REQUIRES: note index < (note list size - 1), book index < (size of book list - 1)
    // MODIFIES: note list of book at given index in list
    // EFFECTS: adds note to the book in the list at given index
    public static void removeNote() {
        System.out.println("What is the serial # of the book from which you'd like to remove a note? ");
        int bookIndex = scanner.nextInt();
        System.out.println("What is the serial # of the note you'd like to remove? ");
        int noteIndex = scanner.nextInt()-1;

        ArrayList<String> notes = myBookList.get(bookIndex).getNotes();
        notes.remove(notes.get(noteIndex));

        System.out.println("Note successfully removed!");
    }

    // MODIFIES: this
    // EFFECTS: Sorts the book list by progress ratio
    private static void sortByProgress() {
        Collections.sort(myBookList, new sortByProgress());
    }

    // MODIFIES: this
    // EFFECTS: Sorts the book list by enthusiasm
    private static void sortByEnthusiasm() {
        Collections.sort(myBookList, new sortByEnthusiasm());
    }

    // REQUIRES: the double passed must be between 0 and 5
    // EFFECTS: produces string with filled and empty box emojis showing progress
    private static String loadingFill(double fill) {
        String returnString = "";

        for (int i=0; i < (int) fill; i++) {
            returnString += "▮";
        }

        for (int i=0; i < (5- (int) fill); i++) {
            returnString += "▯";
        }

        return returnString;
    }

    // EFFECTS: output the list of all books formatted properly
    public static void outputTable() {
        int i = 0;

        // Header output
        System.out.println("S.no.   BookName     Pages   Excited   Progress   Location");

        // Format table with spaces
        for (Book book: myBookList) {
            String rowString = (i+1) + ".)   ";
            rowString += book.getBookName() + "     " + book.getPages();
            rowString +=  "     " + loadingFill(book.getExcited()) + "     " +  loadingFill(5.0*book.progressRatio());
            rowString +=  "     " + book.getLocation();

            System.out.println(rowString);
            i++;
        }
    }

    public static void main(String[] args) {
        ArrayList<String> commands = new ArrayList<String>();
        commands.add("Quit");
        commands.add("Add new book");

        while (true) {
            System.out.println("You have access to the following commands:");

            for (int i=0; i<commands.size(); i++) {
                System.out.println((i+1) + ".)" + commands.get(i));
            }

            System.out.print("Enter command number: ");
            int commandInt = scanner.nextInt();

            if (commandInt <= commands.size()) {
                if (commandInt == 1) {
                    System.out.println("Terminated app");
                    break;
                } else if (commandInt == 2) {
                    if (updateCommandList) {
                        commands.add("Remove book");
                        commands.add("Add a note to a book");
                        commands.add("Remove a note from a book");
                        commands.add("View a book's notes");
                        commands.add("Sort the list by enthusiasm (descending)");
                        commands.add("Sort the list by progress ratio (descending)");

                        updateCommandList = false;
                    }

                    addBook();
                } else if (commandInt == 3) {
                    removeBook();
                } else if (commandInt == 4) {
                    addNote();
                } else if (commandInt == 5) {
                    removeNote();
                } else if (commandInt == 6) {
                    showNotes();
                } else if (commandInt == 7) {
                    sortByEnthusiasm();
                } else if (commandInt == 8) {
                    sortByProgress();
                }
                System.out.println("\n");
                outputTable();
                System.out.println("\n\n");
            } else {
                System.out.println("Please enter a valid command number");
            }
        }
    }
}