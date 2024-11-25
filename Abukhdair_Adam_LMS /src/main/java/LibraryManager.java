import java.util.ArrayList;
import java.util.List;

/**
 * LibraryManager manages the operations related to books in the library.
 *
 * Adam Abukhdair, CEN-3024C, 2024-09-05
 */
public class LibraryManager {
    private List<Book> books;

    /**
     * Constructor for LibraryManager.
     */
    public LibraryManager() {
        this.books = new ArrayList<>();
    }

    /**
     * Adds a new book to the collection.
     * @param book The book to add.
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Removes a book from the collection using its ID.
     * @param id The ID of the book to remove.
     */
    public void removeBook(String id) {
        books.removeIf(book -> book.getId().equals(id));
    }

    /**
     * Lists all books currently in the collection.
     * @return A list of all books.
     */
    public List<Book> listBooks() {
        return new ArrayList<>(books);
    }
}
