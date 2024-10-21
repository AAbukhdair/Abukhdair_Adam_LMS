import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * FileManager handles reading book data from a file.
 *
 * Adam Abukhdair, CEN-3024C, 2024-09-05
 */
public class FileManager {

    /**
     * Reads books from a specified file.
     * @param filename Path to the file containing books data.
     * @return A list of books read from the file.
     * @throws FileNotFoundException if the file is not found.
     */
    public List<Book> readBooksFromFile(String filename) throws FileNotFoundException {
        List<Book> books = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(",");
            if (data.length == 3) {
                books.add(new Book(data[0].trim(), data[1].trim(), data[2].trim()));
            }
        }
        scanner.close();
        return books;
    }
}
