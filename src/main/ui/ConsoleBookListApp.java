package ui;

import model.Book;
import model.BookList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// User interaction via the console takes place in this class by allowing the user
// to enter integers that represent commands they have access to
// Allows the user to add and remove books to their list.
// Allows them to sort the list by their progress or how excited they
// said they were to read a particular book.
// Allows them to add or remove notes to or from a particular book and view the notes

public class ConsoleBookListApp {
    private static final String JSON_STORE = "./data/booklist.json";
    private static BookList myBookList = new BookList();
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean updateCommandList = true;
    private static final ArrayList<String> commands = new ArrayList<String>();
    private static JsonWriter jsonWriter;
    private static JsonReader jsonReader;

    // EFFECTS: runs a book list app
    public ConsoleBookListApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBookListApp();
    }

    // EFFECTS: reads multiple words and stores it in variable passed
    private static String inputLine(String storeVar) {
        storeVar += scanner.next();
        if (scanner.hasNextLine()) {
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

    // EFFECTS: prints list of notes
    public static void showNotes() {
        System.out.println("What is the serial # of the book whose notes you'd like to view? ");
        int selectedIndex = scanner.nextInt();
        Book book = myBookList.get(selectedIndex - 1);

        if (book.getNotes().size() == 0) {
            System.out.println("The book has no notes");
        } else {
            System.out.println("Notes for \"" + book.getBookName() + "\" :");
            System.out.println("S.no.     Note");
            for (int i = 0; i < book.getNotes().size(); i++) {
                System.out.println((i + 1) + ".)    " + book.getNotes().get(i));
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
        int noteIndex = scanner.nextInt();

        ArrayList<String> notes = myBookList.get(bookIndex - 1).getNotes();
        String note = notes.get(noteIndex - 1);
        notes.remove(note);

        System.out.println("Note successfully removed!");
    }

    // MODIFIES: this
    // EFFECTS: Sorts the book list by progress ratio
    private static void sortByProgress() {
        myBookList.sortByProgress();
    }

    // MODIFIES: this
    // EFFECTS: Sorts the book list by excitement
    private static void sortByExcitement() {
        myBookList.sortByExcitement();
    }

    // MODIFIES: this
    // EFFECTS: Updates number of pages read in a given book
    private static void updateProgress() {
        System.out.println("Enter index of book you'd like to update page numbers for: ");
        int bookIndex = scanner.nextInt();

        System.out.println("Enter number of pages read");
        int newProgress = scanner.nextInt();

        myBookList.get(bookIndex - 1).updateProgress(newProgress);

        System.out.println("Pages read update");
    }

    // REQUIRES: the double passed must be between 0 and 5
    // EFFECTS: produces string with filled and empty box emojis showing progress
    private static String loadingFill(double fill) {
        String returnString = "";

        for (int i = 0; i < (int) fill; i++) {
            returnString += "▮";
        }

        for (int i = 0; i < (5 - (int) fill); i++) {
            returnString += "▯";
        }

        return returnString;
    }

    // EFFECTS: produces a string of n spaces
    private static String numSpaces(int n) {
        String returnString = "";
        for (int i = 0; i < n; i++) {
            returnString += " ";
        }

        return returnString;
    }

    // EFFECTS: output the list of all books formatted properly
    public static void outputTable() {
        int i = 0;

        // Header output
        System.out.print("S.no." + numSpaces(18) + "BookName" + numSpaces(35));
        System.out.print("Pages     Excited      Progress     Location \n");

        // Format table with spaces
        for (Book book : myBookList) {
            String rowString = (i + 1) + ".)   ";
            rowString += book.getBookName() + numSpaces(60 - book.getBookName().length()) + (int) book.getPages();
            rowString += "       " + loadingFill(book.getExcited()) + "     " + loadingFill(5.0 * book.progressRatio());
            rowString += "     " + book.getLocation();

            System.out.println(rowString);
            i++;
        }
    }

    // EFFECTS: adds initial commands
    public static void initializeCommandList() {
        commands.add("Quit");
        commands.add("Add new book");
        commands.add("Load saved book list");
    }

    // EFFECTS: gives the user access to new commands by adding the commands to the command list
    public static void addLockedCommands() {
        if (updateCommandList) {
            commands.add("Remove book");
            commands.add("Add a note to a book");
            commands.add("Remove a note from a book");
            commands.add("View a book's notes");
            commands.add("Sort the list by how excited you are to read the book (descending)");
            commands.add("Sort the list by progress ratio (descending)");
            commands.add("Update pages read for a book");
            commands.add("Save book list");

            updateCommandList = false;
        }
    }

    // REQUIRES: The s.no of command provided must be < size of the command list
    // EFFECTS: Performs the command at user given index
    public static void handleCommandList(int commandInt) {
        if (commandInt == 2) {
            addLockedCommands();
            addBook();
        } else if (commandInt == 3) {
            addLockedCommands();
            loadBookList();
        } else if (commandInt == 4) {
            removeBook();
        } else if (commandInt == 5) {
            addNote();
        } else if (commandInt == 6) {
            removeNote();
        } else if (commandInt == 7) {
            showNotes();
        } else if (commandInt == 8) {
            sortByExcitement();
        } else if (commandInt == 9) {
            sortByProgress();
        } else if (commandInt == 10) {
            updateProgress();
        } else if (commandInt == 11) {
            saveBookList();
        }
    }

    // EFFECTS: Primary user interaction method.
    //          Begins and terminates the app, and makes calls to handle the commands
    public static void runBookListApp() {
        initializeCommandList();

        while (true) {
            System.out.println("You have access to the following commands:");

            for (int i = 0; i < commands.size(); i++) {
                System.out.println((i + 1) + ".)" + commands.get(i));
            }

            System.out.print("Enter command number: ");
            int commandInt = scanner.nextInt();

            if (commandInt <= commands.size()) {
                if (commandInt == 1) {
                    System.out.println("Terminated app");
                    break;
                } else {
                    handleCommandList(commandInt);
                }

                System.out.println("\n");
                outputTable();
                System.out.println("\n");
            } else {
                System.out.println("Please enter a valid command number");
            }
        }
    }

    // EFFECTS: saves the book list to file
    private static void saveBookList() {
        try {
            jsonWriter.open();
            jsonWriter.write(myBookList);
            jsonWriter.close();
            System.out.println("Saved book list" + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads book list from file
    private static void loadBookList() {
        try {
            myBookList = jsonReader.read();
            System.out.println("Loaded book list" + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}