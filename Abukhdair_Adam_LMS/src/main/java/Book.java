/**
 * Represents a book with an ID, title, and author.
 * Adam Abukhdair, CTSD Program, 2024-09-08
 */
public class Book {
    private String id;
    private String title;
    private String author;

    /**
     * Constructor for a book object.
     * @param id The unique identifier for the book.
     * @param title The title of the book.
     * @param author The author of the book.
     */
    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // Getters for book properties.
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
}
