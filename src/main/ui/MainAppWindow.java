package ui;

import model.Book;
import model.BookList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

// Main app window with book list table and buttons and other GUI elements

public class MainAppWindow implements ActionListener {
    JFrame frame;
    JPanel addBookPanel;
    JPanel otherMethodsPanel;
    JPanel bookListTablePanel;
    JPanel tableFooterPanel;
    int windowWidth;
    int windowHeight;
    int addBookPanelWidth;
    int addBookPanelHeight;
    int fieldFontSize;
    int fieldHeight;
    int textFieldWidth;
    int buttonHeight;
    JLabel bookName;
    JLabel pagesTotal;
    JLabel pagesRead;
    JLabel excitement;
    JLabel location;
    Font fieldsFont;
    Font addBookButtonFont;
    JTextField bookNameText;
    JTextField pagesTotalText;
    JTextField pagesReadText;
    JTextField excitementText;
    JTextField locationText;
    JTextField deleteRowText;
    JButton addBookButton;
    JButton deleteBookButton;
    JButton saveBookListButton;
    JTable mainBookTable;
    JTableHeader mainBookTableHeader;
    Vector<Vector<String>> data;
    Vector<String> columnNames;
    Color frameBackgroundColor;
    Color frameForegroundColor;
    Color fieldBackgroundColor;
    Color cellBackgroundColor;
    String jsonStore;
    JsonReader jsonReader;
    JsonWriter jsonWriter;
    private boolean loadBookList;
    private BookList bookList;

    // MODIFIES: this
    // EFFECTS: Primary constructor class that runs the application
    public MainAppWindow(boolean loadBookList) {
        this.loadBookList = loadBookList;
        setVariableValues();
        createFrame();
        createAddBookPanel();
        createDisplayOtherMethodsPanel();
        createBookListPanel();
        printLogOnKill();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Initializes the variables
    public void setVariableValues() {
        frame = new JFrame();
        addBookButton = new JButton();
        bookList = new BookList();
        windowHeight = 600;
        windowWidth = 1000;
        addBookPanelWidth = windowWidth;
        addBookPanelHeight = (int) (windowHeight * 0.38);
        fieldFontSize = 20;
        fieldHeight = fieldFontSize + 10;
        buttonHeight = fieldHeight;
        fieldsFont = new Font("Calibri (Body)", Font.PLAIN, fieldFontSize);
        addBookButtonFont = new Font("Calibri (Body)", Font.PLAIN, fieldFontSize);
        textFieldWidth = (int) (windowWidth * 0.5) - 60;
        frameBackgroundColor = new Color(0x202023);
        frameForegroundColor = new Color(0xE5E5E5);
        fieldBackgroundColor = new Color(0x2E2E33);
        cellBackgroundColor = new Color(0x242426);
        jsonStore = "./data/booklist.json";
        jsonWriter = new JsonWriter(jsonStore);
        jsonReader = new JsonReader(jsonStore);
    }

    // MODIFIES: this
    // EFFECTS: Creates the frame for the window
    private void createFrame() {
        frame.setTitle("Book Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); // Prevent frame from being resized
        frame.setSize(windowWidth, windowHeight);
        frame.setLayout(null);

        ImageIcon logo = new ImageIcon("./images/appIcon.png");
        frame.setIconImage(logo.getImage());
        frame.getContentPane().setBackground(frameBackgroundColor);
    }

    // MODIFIES: this
    // EFFECTS: Creates the panel with labels, text fields and a button to add a new book
    private void createAddBookPanel() {
        addBookPanel = new JPanel();
        addBookPanel.setBounds(0, 0, addBookPanelWidth, addBookPanelHeight);
        addBookPanel.setBackground(frameBackgroundColor);
        addBookPanel.setLayout(null);

        createAddBookPanelLabels();
        createAddBookPanelFields();

        addBookPanel.add(bookName);
        addBookPanel.add(pagesTotal);
        addBookPanel.add(pagesRead);
        addBookPanel.add(excitement);
        addBookPanel.add(location);

        addBookButton.setText("Add New Book");
        addBookButton.setFont(addBookButtonFont);
        addBookButton.setBackground(fieldBackgroundColor);
        addBookButton.setForeground(frameForegroundColor);
        addBookButton.setBorder(null);
        addBookButton.setBounds(20, (int) (20 + fieldHeight * 5.5), windowWidth - 60, buttonHeight);
        addBookButton.setFocusPainted(false);
        addBookButton.addActionListener(this);
        addBookPanel.add(addBookButton);

        frame.add(addBookPanel);
    }

    // MODIFIES: this
    // EFFECTS: Carries out the action based on button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBookButton) {
            addBookButtonClicked();
        } else if (e.getSource() == deleteBookButton) {
            deleteBookButtonClicked();
        } else if (e.getSource() == saveBookListButton) {
            saveBookButtonClicked();
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds book to table and book list
    private void addBookButtonClicked() {
        String bookName = bookNameText.getText();
        int pagesTotal = Integer.parseInt(pagesTotalText.getText());
        int pagesRead = Integer.parseInt(pagesReadText.getText());
        double excitement = Double.parseDouble(excitementText.getText());
        String location = locationText.getText();

        bookList.add(new Book(bookName, pagesTotal, pagesRead, location, excitement));

        createBookListPanel();
        mainBookTable.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: Deletes row from table and book list of int equal to that in text field
    private void deleteBookButtonClicked() {
        int rowNumToDelete = Integer.parseInt(deleteRowText.getText());
        rowNumToDelete--;
        bookList.remove(rowNumToDelete);
//        System.out.println("Deleted row " + (rowNumToDelete + 1));

        loadBookList = false;
        bookListTablePanel.setVisible(false);

//        for (Book next : bookList) {
//            System.out.println(next.getBookName());
//        }
        createBookListPanel();
        frame.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: Saves book list
    private void saveBookButtonClicked() {
        try {
            jsonWriter.open();
            jsonWriter.write(bookList);
            jsonWriter.close();
            System.out.println("Saved book list" + " to " + jsonStore);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + jsonStore);
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates the labels for add book panel
    private void createAddBookPanelLabels() {
        bookName = new JLabel();
        pagesTotal = new JLabel();
        pagesRead = new JLabel();
        excitement = new JLabel();
        location = new JLabel();

        bookName.setText("Book Name");
        pagesTotal.setText("Number of Pages");
        pagesRead.setText("Number of Pages Read");
        excitement.setText("Excitement to Read the Book");
        location.setText("Location of the Book");

        setAllAddBookPanelLabelFontsAndColors();

        bookName.setBounds(20, 20, windowWidth, fieldHeight);
        pagesTotal.setBounds(20, 20 + fieldHeight, windowWidth, fieldHeight);
        pagesRead.setBounds(20, 20 + fieldHeight * 2, windowWidth, fieldHeight);
        excitement.setBounds(20, 20 + fieldHeight * 3, windowWidth, fieldHeight);
        location.setBounds(20, 20 + fieldHeight * 4, windowWidth, fieldHeight);
    }

    // MODIFIES: this
    // EFFECTS: Formats all the labels for add book panel
    private void setAllAddBookPanelLabelFontsAndColors() {
        bookName.setForeground(frameForegroundColor);
        pagesTotal.setForeground(frameForegroundColor);
        pagesRead.setForeground(frameForegroundColor);
        excitement.setForeground(frameForegroundColor);
        location.setForeground(frameForegroundColor);

        bookName.setFont(fieldsFont);
        pagesTotal.setFont(fieldsFont);
        pagesRead.setFont(fieldsFont);
        excitement.setFont(fieldsFont);
        location.setFont(fieldsFont);
    }

    // MODIFIES: this
    // EFFECTS: Creates text fields for add book panel
    private void createAddBookPanelFields() {
        bookNameText = new JTextField();
        pagesTotalText = new JTextField();
        pagesReadText = new JTextField();
        excitementText = new JTextField();
        locationText = new JTextField();

        bookNameText.setBounds((int) (windowWidth * 0.5), 20, textFieldWidth, fieldHeight - 10);
        pagesTotalText.setBounds((int) (windowWidth * 0.5), 20 + fieldHeight * 1, textFieldWidth, fieldHeight - 10);
        pagesReadText.setBounds((int) (windowWidth * 0.5), 20 + fieldHeight * 2, textFieldWidth, fieldHeight - 10);
        excitementText.setBounds((int) (windowWidth * 0.5), 20 + fieldHeight * 3, textFieldWidth, fieldHeight - 10);
        locationText.setBounds((int) (windowWidth * 0.5), 20 + fieldHeight * 4, textFieldWidth, fieldHeight - 10);

        stylizeFields();

        addBookPanel.add(bookNameText);
        addBookPanel.add(pagesTotalText);
        addBookPanel.add(pagesReadText);
        addBookPanel.add(excitementText);
        addBookPanel.add(locationText);
    }

    // MODIFIES: this
    // EFFECTS: stylizes text fields for add book panel
    private void stylizeFields() {
        bookNameText.setBackground(fieldBackgroundColor);
        bookNameText.setBorder(null);
        bookNameText.setForeground(frameForegroundColor);
        bookNameText.setCaretColor(frameForegroundColor);

        pagesTotalText.setBackground(fieldBackgroundColor);
        pagesTotalText.setBorder(null);
        pagesTotalText.setForeground(frameForegroundColor);
        pagesTotalText.setCaretColor(frameForegroundColor);

        pagesReadText.setBackground(fieldBackgroundColor);
        pagesReadText.setBorder(null);
        pagesReadText.setForeground(frameForegroundColor);
        pagesReadText.setCaretColor(frameForegroundColor);

        excitementText.setBackground(fieldBackgroundColor);
        excitementText.setBorder(null);
        excitementText.setForeground(frameForegroundColor);
        excitementText.setCaretColor(frameForegroundColor);

        locationText.setBackground(fieldBackgroundColor);
        locationText.setBorder(null);
        locationText.setForeground(frameForegroundColor);
        locationText.setCaretColor(frameForegroundColor);
    }

    // MODIFIES: this
    // EFFECTS: Creates panel with buttons for non-book-adding functionality
    private void createDisplayOtherMethodsPanel() {
        otherMethodsPanel = new JPanel();
        otherMethodsPanel.setBounds(0, addBookPanelHeight, addBookPanelWidth,
                windowHeight - (2 * addBookPanelHeight));
        otherMethodsPanel.setBackground(frameBackgroundColor);
        otherMethodsPanel.setLayout(null);

        addDeleteRowFunctionalityButtonAndTextField();
        addSaveBookListButton();

        frame.add(otherMethodsPanel);
    }

    // MODIFIES: this
    // EFFECTS: Adds delete row button and text field to the other methods panel
    private void addDeleteRowFunctionalityButtonAndTextField() {
        deleteBookButton = new JButton();

        deleteBookButton.setText("Enter row number and click to delete: ");
        deleteBookButton.setFont(addBookButtonFont);
        deleteBookButton.setBounds(20, 0, (windowWidth) / 2 - 60, buttonHeight);
        deleteBookButton.setBackground(fieldBackgroundColor);
        deleteBookButton.setForeground(frameForegroundColor);
        deleteBookButton.setBorder(null);
        deleteBookButton.setFocusPainted(false);
        deleteBookButton.addActionListener(this);

        deleteRowText = new JTextField();
        deleteRowText.setBounds((int) (windowWidth * 0.5), 0, textFieldWidth + 20, buttonHeight);
        deleteRowText.setFont(addBookButtonFont);
        deleteRowText.setBackground(fieldBackgroundColor);
        deleteRowText.setBorder(null);
        deleteRowText.setForeground(frameForegroundColor);
        deleteRowText.setCaretColor(frameForegroundColor);

        otherMethodsPanel.add(deleteBookButton);
        otherMethodsPanel.add(deleteRowText);
    }

    // MODIFIES: this
    // EFFECTS: Adds the button to save book list
    private void addSaveBookListButton() {
        saveBookListButton = new JButton();

        saveBookListButton.setText("Save book list");
        saveBookListButton.setFont(addBookButtonFont);
        saveBookListButton.setBounds(20, buttonHeight + 15, (windowWidth) - 60, buttonHeight);
        saveBookListButton.setBackground(fieldBackgroundColor);
        saveBookListButton.setForeground(frameForegroundColor);
        saveBookListButton.setBorder(null);
        saveBookListButton.setFocusPainted(false);
        saveBookListButton.addActionListener(this);

        otherMethodsPanel.add(saveBookListButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates the panel with list of all the books
    private void createBookListPanel() {
        bookListTablePanel = new JPanel();
        bookListTablePanel.setBounds(0, windowHeight - addBookPanelHeight, addBookPanelWidth, addBookPanelHeight);
        bookListTablePanel.setBackground(frameBackgroundColor);
        bookListTablePanel.setLayout(null);

        createBookTable();
//        generatePaddingBelowTable();

        frame.add(bookListTablePanel);
    }

    // MODIFIES: this
    // EFFECTS: Creates table and adds book list data to it
    private void createBookTable() {
        this.data = getBookListTableData();
        columnNames = new Vector<>();
        columnNames.add("S.No");
        columnNames.add("Book Name");
        columnNames.add("Pages");
        columnNames.add("Excited");
        columnNames.add("Progress");
        columnNames.add("Location");
        mainBookTable = new JTable(data, columnNames);

        mainBookTableHeader = mainBookTable.getTableHeader();
        mainBookTableHeader.setBackground(cellBackgroundColor);
        mainBookTableHeader.setResizingAllowed(true);
        mainBookTableHeader.setForeground(frameForegroundColor);
        mainBookTableHeader.setBounds(40, 0, windowWidth - 80, 30);

        mainBookTable.setBounds(40, 30, windowWidth - 80,
                mainBookTable.getRowHeight() * mainBookTable.getRowCount());
        mainBookTable.setBackground(cellBackgroundColor);
        mainBookTable.setForeground(frameForegroundColor);
        mainBookTable.setCellSelectionEnabled(false);

        bookListTablePanel.add(mainBookTableHeader);
        bookListTablePanel.add(mainBookTable);
    }

    // MODIFIES: this
    // EFFECTS: creates padding below the table
    private void generatePaddingBelowTable() {
        tableFooterPanel = new JPanel();
        tableFooterPanel.setBounds(40, 30 + mainBookTable.getHeight(), windowWidth - 80, 100);
        tableFooterPanel.setBackground(frameBackgroundColor);
        bookListTablePanel.add(tableFooterPanel);
    }

    // MODIFIES: this
    // EFFECTS: loads book list data if user wishes, and then returns data
    //          in the form the table requires
    private Vector<Vector<String>> getBookListTableData() {
        Vector<Vector<String>> tableData;
        if (loadBookList) {
            loadBookList();
            loadBookList = false;
        }
        tableData = bookListToTable();
        return tableData;
    }

    // MODIFIES: this
    // EFFECTS: loads book list from file
    private void loadBookList() {
        try {
            bookList = jsonReader.read();
            System.out.println("Loaded book list" + " from " + jsonStore);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + jsonStore);
        }
    }

    // MODIFIES: this
    // EFFECTS: Converts book list into appropriate data form for table row by row
    private Vector<Vector<String>> bookListToTable() {
        Vector<Vector<String>> tableData = new Vector<Vector<String>>();
        int index = 1;
        for (Book next : bookList) {
            Vector<String> row = new Vector<String>();
            row.add(String.valueOf(index));
            row.add(next.getBookName());
            row.add(String.valueOf(next.getPages()));
            row.add(String.valueOf(next.getExcited()));
            row.add(String.valueOf(next.getProgress()));
            row.add(next.getLocation());
            tableData.add(row);
            index++;
        }
        return tableData;
    }

    // EFFECTS: triggers event log printing on close
    public void printLogOnKill() {
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                bookList.close();
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
    }
}
