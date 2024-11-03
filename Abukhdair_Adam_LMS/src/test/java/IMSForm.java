import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * LMSForm Class
 * Adam Abukhdair, CTSD Program, 2024-11-03
 *
 * This class represents the GUI for the Libaray Management System (LMS),
 * providing an interface for users to load books from a file, remove books by
 * barcode or title, check books in and out, display the database contents, and
 * exit the application. The LMSForm class interacts with the BookManager to
 * perform various operations on the book database.
 */
public class IMSForm extends JFrame {
    private JTextField fileNameField, barcodeField, titleField;
    private JButton loadFileButton, removeByBarcodeButton, removeByTitleButton;
    private JButton checkoutButton, checkinButton, displayButton, exitButton;
    private JTextArea displayArea;
    private BookManager bookManager;

    /**
     * Constructor for LMSForm
     * Initializes the GUI components and sets up the layout and action listeners.
     */
    public IMSForm() {
        bookManager = new BookManager();

        setTitle("Library Management System");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Initialize text fields and buttons
        fileNameField = new JTextField(20);
        loadFileButton = new JButton("Load Books from File");
        barcodeField = new JTextField(15);
        titleField = new JTextField(15);
        removeByBarcodeButton = new JButton("Remove by Barcode");
        removeByTitleButton = new JButton("Remove by Title");
        checkoutButton = new JButton("Checkout Book");
        checkinButton = new JButton("Checkin Book");
        displayButton = new JButton("Display Database");
        exitButton = new JButton("Exit");
        displayArea = new JTextArea();
        displayArea.setEditable(false);

        // Set component bounds for layout
        fileNameField.setBounds(20, 20, 200, 30);
        loadFileButton.setBounds(230, 20, 200, 30);
        barcodeField.setBounds(20, 60, 200, 30);
        removeByBarcodeButton.setBounds(230, 60, 200, 30);
        titleField.setBounds(20, 100, 200, 30);
        removeByTitleButton.setBounds(230, 100, 200, 30);
        checkoutButton.setBounds(20, 140, 200, 30);
        checkinButton.setBounds(230, 140, 200, 30);
        displayButton.setBounds(20, 180, 410, 30);
        exitButton.setBounds(20, 220, 410, 30);
        displayArea.setBounds(20, 260, 450, 180);

        // Add components to the frame
        add(fileNameField);
        add(loadFileButton);
        add(barcodeField);
        add(removeByBarcodeButton);
        add(titleField);
        add(removeByTitleButton);
        add(checkoutButton);
        add(checkinButton);
        add(displayButton);
        add(exitButton);
        add(displayArea);

        // Add action listeners for each button
        loadFileButton.addActionListener(new LoadFileAction());
        removeByBarcodeButton.addActionListener(new RemoveByBarcodeAction());
        removeByTitleButton.addActionListener(new RemoveByTitleAction());
        checkoutButton.addActionListener(new CheckoutAction());
        checkinButton.addActionListener(new CheckinAction());
        displayButton.addActionListener(new DisplayDatabaseAction());
        exitButton.addActionListener(e -> System.exit(0));
    }

    /**
     * LoadFileAction Class
     * Handles loading books from a specified file into the system.
     */
    private class LoadFileAction implements ActionListener {
        /**
         * actionPerformed
         * Attempts to load books from a hardcoded file path and displays success or error messages.
         *
         * @param e The ActionEvent triggered by clicking the "Load Books from File" button.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String fileName = "/Users/adam/Downloads/books.txt"; // Hardcoded file path for testing
            System.out.println("Load File Button Clicked - File Name: " + fileName);
            if (fileName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a file name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (bookManager.loadBooksFromFile(fileName)) {
                JOptionPane.showMessageDialog(null, "Books loaded successfully from " + fileName + ".");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to load file. Please check the file name and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * RemoveByBarcodeAction Class
     * Handles removing a book from the database using its barcode.
     */
    private class RemoveByBarcodeAction implements ActionListener {
        /**
         * actionPerformed
         * Removes a book based on the barcode entered by the user.
         *
         * @param e The ActionEvent triggered by clicking the "Remove by Barcode" button.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String barcode = barcodeField.getText();
            System.out.println("Remove by Barcode Button Clicked - Barcode: " + barcode);
            if (barcode.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a barcode.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (bookManager.removeBook(barcode)) {
                JOptionPane.showMessageDialog(null, "Book with barcode " + barcode + " was removed.");
            } else {
                JOptionPane.showMessageDialog(null, "Book with barcode " + barcode + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * RemoveByTitleAction Class
     * Handles removing a book from the database using its title.
     */
    private class RemoveByTitleAction implements ActionListener {
        /**
         * actionPerformed
         * Removes a book based on the title entered by the user.
         *
         * @param e The ActionEvent triggered by clicking the "Remove by Title" button.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = titleField.getText();
            System.out.println("Remove by Title Button Clicked - Title: " + title);
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a title.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (bookManager.removeBookByTitle(title)) {
                JOptionPane.showMessageDialog(null, "Book titled '" + title + "' was removed.");
            } else {
                JOptionPane.showMessageDialog(null, "Book titled '" + title + "' not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * CheckoutAction Class
     * Handles checking out a book by its title.
     */
    private class CheckoutAction implements ActionListener {
        /**
         * actionPerformed
         * Checks out a book based on the title entered by the user.
         *
         * @param e The ActionEvent triggered by clicking the "Checkout Book" button.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = titleField.getText();
            System.out.println("Checkout Book Button Clicked - Title: " + title);
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a title.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (bookManager.checkoutBookByTitle(title)) {
                JOptionPane.showMessageDialog(null, "Book titled '" + title + "' checked out.");
            } else {
                JOptionPane.showMessageDialog(null, "Book titled '" + title + "' is unavailable or already checked out.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * CheckinAction Class
     * Handles checking in a book by its title.
     */
    private class CheckinAction implements ActionListener {
        /**
         * actionPerformed
         * Checks in a book based on the title entered by the user.
         *
         * @param e The ActionEvent triggered by clicking the "Checkin Book" button.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = titleField.getText();
            System.out.println("Checkin Book Button Clicked - Title: " + title);
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a title.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (bookManager.checkInBookByTitle(title)) {
                JOptionPane.showMessageDialog(null, "Book titled '" + title + "' checked in.");
            } else {
                JOptionPane.showMessageDialog(null, "Book titled '" + title + "' not found or already checked in.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * DisplayDatabaseAction Class
     * Handles displaying all books in the database.
     */
    private class DisplayDatabaseAction implements ActionListener {
        /**
         * actionPerformed
         * Displays the list of books in the database on the display area.
         *
         * @param e The ActionEvent triggered by clicking the "Display Database" button.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Display Database Button Clicked");
            List<String> databaseContents = bookManager.getDatabaseContents();
            displayArea.setText(String.join("\n", databaseContents));
        }
    }

    /**
     * Main method
     * Launches the Library Management System GUI.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IMSForm imsForm = new IMSForm();
            imsForm.setVisible(true);
        });
    }
}
