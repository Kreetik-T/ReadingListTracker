package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// The first window that is launched.
// It provides user with the choice to create new book list or load saved book list,
// then runs main app window.
public class LoadAppWindow implements ActionListener {
    JFrame frame;
    JPanel header;
    int windowWidth;
    int windowHeight;
    int headerFontSize;
    int headerTextLength;
    int headerTextXCoordinate;
    int headerTextYCoordinate;
    int headerHeight;
    Font headerFont;
    String headerText;
    String yesButtonText;
    String noButtonText;
    Color frameBackgroundColor;
    Color headerBackgroundColor;
    Color headerTextColor;
    Color buttonTextColor;
    Color buttonBackgroundColor;
    JButton yesButton;
    JButton noButton;

    // EFFECTS: runs the app startup window
    public LoadAppWindow() {
        setVariableValues();
        createFrame();
        createHeader();
        createButtons();

        frame.setVisible(true);
    }

    // EFFECTS: initiates variables and assigns values
    private void setVariableValues() {
        frame = new JFrame();
        header = new JPanel();
        windowWidth = 600;
        windowHeight = 300;
        headerFontSize = 20;
        headerFont = new Font("Calibri (Body)", Font.PLAIN, headerFontSize);
        headerHeight = (int) (windowHeight * 0.25);
        headerText = "Do you want to open saved book list or create a new one?";

        headerTextLength = (int) (headerText.length() * headerFontSize * 0.47);
        headerTextXCoordinate = (windowWidth - headerTextLength) / 2 + 20;
        headerTextYCoordinate = 20;

        yesButtonText = "Yes, open saved list";
        noButtonText = "No, I want to create a new list";

        frameBackgroundColor = new Color(0x202023);
        headerBackgroundColor = new Color(0x38C4A5);
        headerTextColor = new Color(0x3B3B3B);
        buttonTextColor = new Color(0xE5E5E5);
        buttonBackgroundColor = new Color(0x343434);
    }

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

    // EFFECTS: Creates the header with the question prompting the user to
    //          open saved book list or create a new one
    private void createHeader() {
        JLabel label = new JLabel();
        label.setText(headerText);
        label.setFont(headerFont);
        label.setForeground(headerTextColor);
        label.setBounds(headerTextXCoordinate, headerTextYCoordinate, 600, 30);
        header.setLayout(null);
        header.setBackground(headerBackgroundColor);
        header.setBounds(0, 0, windowWidth, headerHeight);
        header.add(label);

        try {
            int imgWidth = 30;
            int imgHeight = imgWidth;
            BufferedImage myPicture = ImageIO.read(new File("./images/Clock.png"));
            JLabel picLabel = new JLabel(new ImageIcon(
                    myPicture.getScaledInstance(imgWidth, imgHeight, Image.SCALE_FAST)));
            picLabel.setBounds(headerTextXCoordinate - imgWidth - 10, headerTextYCoordinate, imgWidth, imgHeight);
            header.add(picLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.add(header);
    }

    // EFFECTS: Creates the two buttons offering user the choice to open saved
    //          book list or create new one
    private void createButtons() {
        Border emptyBorder = BorderFactory.createEmptyBorder();

        yesButton = new JButton();
        yesButton.setText(yesButtonText);
        yesButton.setBounds(20, headerHeight + 50, windowWidth / 2 - 30, 60);
        yesButton.setForeground(buttonTextColor);
        yesButton.setBackground(buttonBackgroundColor);
        yesButton.setBorder(emptyBorder);
        yesButton.setFocusPainted(false);
        yesButton.addActionListener(this);
        frame.add(yesButton);

        noButton = new JButton();
        noButton.setText(noButtonText);
        noButton.setBounds(windowWidth / 2 + 10, headerHeight + 50, windowWidth / 2 - 40, 60);
        noButton.setForeground(buttonTextColor);
        noButton.setBackground(buttonBackgroundColor);
        noButton.setBorder(emptyBorder);
        noButton.setFocusPainted(false);
        noButton.addActionListener(this);
        frame.add(noButton);
    }

    // EFFECTS: Runs main app window with boolean value passed down to it
    //          about whether to load saved book list or create new one
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.print("User chose to: ");
        if (e.getSource() == yesButton) {
            System.out.println("open saved book list");
            new MainAppWindow(true);
            frame.dispose();
        } else {
            System.out.println("create new book list");
            new MainAppWindow(false);
            frame.dispose();
        }
    }
}
