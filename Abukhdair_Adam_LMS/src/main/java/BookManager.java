import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

/**
 * Manages a collection of books including operations to add, remove, and list books.
 * Adam Abukhdair, CTSD Program, 2024-09-08
 */
public class BookManager {
    private List<Book> books = new ArrayList<>();

    /**
     * Adds a book to the collection.
     * @param book The book to add.
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Removes a book from the collection by ID.
     * @param id The ID of the book to remove.
     */
    public void removeBook(String id) {
        books.removeIf(b -> b.getId().equals(id));
    }

    /**
     * Lists all books in the collection.
     */
    public void listBooks() {
        books.forEach(book -> System.out.println(book.getId() + " - " + book.getTitle() + " by " + book.getAuthor()));
    }

    /**
     * Loads books from a specified file path into the collection.
     * @param filePath The path to the file containing book data.
     */
    public void loadBooksFromFile(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    addBook(new Book(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load books from file: " + e.getMessage());
        }
    }
}


