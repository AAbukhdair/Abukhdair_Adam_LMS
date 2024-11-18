import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * IMSForm Class
 * Adam Abukhdair, CTSD Program, 2024-11-03
 *
 * This class represents the GUI for the Library Management System (LMS),
 * providing an interface for users to interact with the SQLite database of books.
 * Users can load, remove, check out, check in, and display books stored in the database.
 */
public class IMSForm extends JFrame {
    private JTextField barcodeField, titleField;
    private JButton removeByBarcodeButton, removeByTitleButton;
    private JButton checkoutButton, checkinButton, displayButton, exitButton;
    private JTextArea displayArea;
    private Connection connection;

    /**
     * Constructor for IMSForm
     * Initializes the GUI components and sets up the layout and action listeners.
     */
    public IMSForm() {
        setTitle("Library Management System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Initialize text fields and buttons
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
        barcodeField.setBounds(20, 20, 200, 30);
        titleField.setBounds(20, 60, 200, 30);
        removeByBarcodeButton.setBounds(240, 20, 150, 30);
        removeByTitleButton.setBounds(240, 60, 150, 30);
        checkoutButton.setBounds(20, 100, 200, 30);
        checkinButton.setBounds(240, 100, 150, 30);
        displayButton.setBounds(20, 140, 530, 30);
        exitButton.setBounds(20, 180, 530, 30);
        displayArea.setBounds(20, 220, 530, 200);

        // Add components to the frame
        add(barcodeField);
        add(titleField);
        add(removeByBarcodeButton);
        add(removeByTitleButton);
        add(checkoutButton);
        add(checkinButton);
        add(displayButton);
        add(exitButton);
        add(displayArea);

        // Add action listeners for each button
        removeByBarcodeButton.addActionListener(new RemoveByBarcodeAction());
        removeByTitleButton.addActionListener(new RemoveByTitleAction());
        checkoutButton.addActionListener(new CheckoutAction());
        checkinButton.addActionListener(new CheckinAction());
        displayButton.addActionListener(new DisplayDatabaseAction());
        exitButton.addActionListener(e -> System.exit(0));

        // Establish database connection
        connectToDatabase();
    }

    static {
        try {
            Class.forName("org.sqlite.JDBC");  // Load the SQLite JDBC driver
            System.out.println("SQLite JDBC Driver successfully loaded.");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load SQLite JDBC Driver.");
            e.printStackTrace();  // This prints the detailed error
        }
    }



    private void connectToDatabase() {
        try {
            // Use the correct JDBC URL for your SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/adam/Downloads/Abukhdair_Adam_LMS/lms.db");
            System.out.println("Connected to the database successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }





    /**
     * DisplayDatabaseAction Class
     * Handles displaying all books in the database.
     */
    private class DisplayDatabaseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (connection == null) {
                JOptionPane.showMessageDialog(null, "Database connection is not available.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM books")) {

                StringBuilder books = new StringBuilder();
                while (rs.next()) {
                    books.append("Barcode: ").append(rs.getString("barcode"))
                            .append(", Title: ").append(rs.getString("title"))
                            .append(", Author: ").append(rs.getString("author"))
                            .append(", Genre: ").append(rs.getString("genre"))
                            .append(", Status: ").append(rs.getString("status"))
                            .append(", Due Date: ").append(rs.getString("due_date"))
                            .append("\n");
                }

                if (books.length() == 0) {
                    displayArea.setText("No books found in the database.");
                } else {
                    displayArea.setText(books.toString());
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Failed to query the database.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }


    /**
     * RemoveByBarcodeAction Class
     * Handles removing a book from the database by barcode.
     */
    private class RemoveByBarcodeAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String barcode = barcodeField.getText();
            if (barcode.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a barcode.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM books WHERE barcode = ?")) {
                stmt.setString(1, barcode);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Book removed successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "No book found with that barcode.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Failed to remove book.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    /**
     * RemoveByTitleAction Class
     * Handles removing a book from the database by title.
     */
    private class RemoveByTitleAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = titleField.getText();
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a title.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM books WHERE title = ?")) {
                stmt.setString(1, title);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Book titled '" + title + "' was removed.");
                } else {
                    JOptionPane.showMessageDialog(null, "No book found with the title '" + title + "'.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Failed to remove book by title.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    /**
     * CheckoutAction Class
     * Handles checking out a book by barcode.
     */
    private class CheckoutAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String barcode = barcodeField.getText();
            if (barcode.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a barcode.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE books SET status = 'checked out', due_date = DATE('now', '+4 weeks') WHERE barcode = ? AND status = 'checked in'")) {

                stmt.setString(1, barcode);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Book checked out successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Book not available or already checked out.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Failed to check out book.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    /**
     * CheckinAction Class
     * Handles checking in a book by barcode.
     */
    private class CheckinAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String barcode = barcodeField.getText();
            if (barcode.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a barcode.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE books SET status = 'checked in', due_date = NULL WHERE barcode = ? AND status = 'checked out'")) {

                stmt.setString(1, barcode);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Book checked in successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Book not found or already checked in.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Failed to check in book.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
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

