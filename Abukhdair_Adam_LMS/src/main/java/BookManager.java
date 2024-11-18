import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Manages a collection of books including operations to add, remove, list, check in, and check out books.
 * Adam Abukhdair, CTSD Program, 2024-09-08
 */
public class BookManager {
    private List<Book> books = new ArrayList<>();

    // Adds a book to the collection
    public void addBook(Book book) {
        books.add(book);
    }

    // Removes a book from the collection by ID
    public boolean removeBook(String id) {
        return books.removeIf(book -> book.getId().equals(id));
    }

    // Removes a book from the collection by title
    public boolean removeBookByTitle(String title) {
        return books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

    // Checks out a book by ID
    public boolean checkoutBookById(String id) {
        for (Book book : books) {
            if (book.getId().equals(id) && !book.isCheckedOut()) {
                book.setCheckedOut(true, LocalDate.now().plusWeeks(4));
                return true;
            }
        }
        return false;
    }

    // Checks out a book by title
    public boolean checkoutBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && !book.isCheckedOut()) {
                book.setCheckedOut(true, LocalDate.now().plusWeeks(4));
                return true;
            }
        }
        return false;
    }

    // Checks in a book by ID
    public boolean checkInBookById(String id) {
        for (Book book : books) {
            if (book.getId().equals(id) && book.isCheckedOut()) {
                book.setCheckedOut(false, null);
                return true;
            }
        }
        return false;
    }

    // Checks in a book by title
    public boolean checkInBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && book.isCheckedOut()) {
                book.setCheckedOut(false, null);
                return true;
            }
        }
        return false;
    }

    // Lists all books in the collection
    public void listBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the collection.");
        } else {
            books.forEach(System.out::println);
        }
    }

    // Returns the contents of the book database for display in the GUI
    public List<String> getDatabaseContents() {
        List<String> contents = new ArrayList<>();
        for (Book book : books) {
            contents.add(book.toString());
        }
        return contents;
    }

    // Loads books from a specified file path into the collection
    public boolean loadBooksFromFile(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    addBook(new Book(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                }
            }
            return true; // Indicate success
        } catch (IOException e) {
            System.err.println("Failed to load books from file: " + e.getMessage());
            return false; // Indicate failure
        }
    }
}
