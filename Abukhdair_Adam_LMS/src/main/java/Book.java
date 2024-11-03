import java.time.LocalDate;

public class Book {
    private String id;
    private String title;
    private String author;
    private boolean isCheckedOut;
    private LocalDate dueDate;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isCheckedOut = false;
        this.dueDate = null;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(boolean checkedOut, LocalDate dueDate) {
        this.isCheckedOut = checkedOut;
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return String.format("%s - %s by %s%s", id, title, author,
                isCheckedOut ? " (Checked out until " + dueDate + ")" : " (Available)");
    }
}

