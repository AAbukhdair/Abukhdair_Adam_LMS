import java.util.Scanner;

/**
 * Main driver class for the Library Management System. Handles user interactions and system initialization.
 * Adam Abukhdair, CTSD Program, 2024-09-08
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static BookManager bookManager = new BookManager();

    public static void main(String[] args) {
        bookManager.loadBooksFromFile("/Users/adam/Downloads/books.txt"); // Load books at start

        String option;
        do {
            System.out.println("\nChoose an option:");
            System.out.println("1 - List all books");
            System.out.println("2 - Add a book");
            System.out.println("3 - Remove a book by ID");
            System.out.println("4 - Check out a book by ID");
            System.out.println("5 - Check in a book by ID");
            System.out.println("6 - Exit");
            System.out.print("Enter option: ");
            option = scanner.nextLine();

            switch (option) {
                case "1":
                    bookManager.listBooks();
                    break;
                case "2":
                    addBook();
                    break;
                case "3":
                    removeBookById();
                    break;
                case "4":
                    checkoutBookById();
                    break;
                case "5":
                    checkInBookById();
                    break;
                case "6":
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        } while (!option.equals("6"));

        scanner.close();
    }

    private static void addBook() {
        System.out.println("Enter book ID, title, and author separated by commas:");
        String[] details = scanner.nextLine().split(",");
        if (details.length == 3) {
            bookManager.addBook(new Book(details[0].trim(), details[1].trim(), details[2].trim()));
        } else {
            System.out.println("Invalid book format!");
        }
    }

    private static void removeBookById() {
        System.out.println("Enter the book ID to remove:");
        String id = scanner.nextLine();
        if (bookManager.removeBook(id)) {
            System.out.println("The book was successfully removed.");
        } else {
            System.out.println("No book found with that ID.");
        }
    }

    private static void checkoutBookById() {
        System.out.println("Enter the ID of the book to check out:");
        String id = scanner.nextLine();
        if (bookManager.checkoutBookById(id)) {
            System.out.println("The book was successfully checked out.");
        } else {
            System.out.println("No book found with that ID, or it's already checked out.");
        }
    }

    private static void checkInBookById() {
        System.out.println("Enter the ID of the book to check in:");
        String id = scanner.nextLine();
        if (bookManager.checkInBookById(id)) {
            System.out.println("The book was successfully checked in.");
        } else {
            System.out.println("No book found with that ID, or it wasn't checked out.");
        }
    }
}
