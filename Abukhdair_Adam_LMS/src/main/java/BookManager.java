import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.time.LocalDate;

public class BookManager {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public boolean removeBook(String id) {
        return books.removeIf(book -> book.getId().equals(id));
    }

    public boolean checkoutBookById(String id) {
        for (Book book : books) {
            if (book.getId().equals(id) && !book.isCheckedOut()) {
                book.setCheckedOut(true, LocalDate.now().plusWeeks(4));
                return true;
            }
        }
        return false;
    }

    public boolean checkInBookById(String id) {
        for (Book book : books) {
            if (book.getId().equals(id) && book.isCheckedOut()) {
                book.setCheckedOut(false, null);
                return true;
            }
        }
        return false;
    }

    public void listBooks() {
        books.forEach(System.out::println);
    }

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
