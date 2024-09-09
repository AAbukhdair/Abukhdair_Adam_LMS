import java.util.Scanner;

/**
 * Main driver class for the Library Management System. Handles user interactions and system initialization.
 * Adam Abukhdair, CTSD Program, 2024-09-08
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static BookManager bookManager = new BookManager();

    public static void main(String[] args) {
        //books from a file
        bookManager.loadBooksFromFile("/Users/adam/Downloads/books.txt");

        String option;
        do {
            System.out.println("\nChoose an option:");
            System.out.println("1 - List all books");
            System.out.println("2 - Add a book");
            System.out.println("3 - Remove a book");
            System.out.println("4 - Exit");
            System.out.print("Enter option: ");
            option = scanner.nextLine();

            switch (option) {
                case "1":
                    bookManager.listBooks();
                    break;
                case "2":
                    System.out.println("Enter book ID, title, and author separated by commas:");
                    String[] details = scanner.nextLine().split(",");
                    if (details.length == 3) {
                        bookManager.addBook(new Book(details[0].trim(), details[1].trim(), details[2].trim()));
                    } else {
                        System.out.println("Invalid book format!");
                    }
                    break;
                case "3":
                    System.out.println("Enter the book ID to remove:");
                    String id = scanner.nextLine();
                    bookManager.removeBook(id);
                    break;
                case "4":
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
            }
        } while (!option.equals("4"));

        scanner.close();
    }
}
