package LibraryBorrowingSystem;

/**
 * Represents a book in the library collection.
 *
 * This class stores the details of a single book including its ID, title,
 * author, and availability status. It also provides the initial book data
 * used to pre-populate the library catalog on startup.
 *
 * Attributes:
 *   - bookId    : unique identifier for the book (e.g. "B001")
 *   - title     : the title of the book
 *   - author    : the name of the book's author
 *   - available : true if the book is on the shelf, false if it is borrowed
 */
public class Books {
    private String  bookId;
    private String  title;
    private String  author;
    private boolean available;

    /**
     * Creates a new book with the given details.
     * The book is set to available by default when first added.
     *
     * @param bookId  unique book identifier
     * @param title   title of the book
     * @param author  author of the book
     */
    public Books(String bookId, String title, String author) {
        this.bookId    = bookId;
        this.title     = title;
        this.author    = author;
        this.available = true;
    }

    /**
     * Returns the pre-defined initial book data for the library.
     * This method keeps the starting data inside the Books class
     * so each class is responsible for its own data.
     *
     * @return array of Books objects pre-filled with default catalog entries
     */
    public static Books[] getInitialBooks() {
        Books[] initial = new Books[5];
        initial[0] = new Books("B001", "The Great Gatsby",       "F. Scott Fitzgerald");
        initial[1] = new Books("B002", "To Kill a Mockingbird",  "Harper Lee");
        initial[2] = new Books("B003", "1984",                   "George Orwell");
        initial[3] = new Books("B004", "Brave New World",        "Aldous Huxley");
        initial[4] = new Books("B005", "The Catcher in the Rye", "J.D. Salinger");
        return initial;
    }

    // Getters — allow other classes to read private attributes

    /** Returns the unique book ID. */
    public String getBookId() { return bookId; }

    /** Returns the title of the book. */
    public String getTitle() { return title; }

    /** Returns the author of the book. */
    public String getAuthor() { return author; }

    /** Returns true if the book is currently available to borrow. */
    public boolean isAvailable() { return available; }

    // Setters — allow controlled modification of private attributes

    /** Updates the book ID. */
    public void setBookId(String bookId) { this.bookId = bookId; }

    /** Updates the title of the book. */
    public void setTitle(String title) { this.title = title; }

    /** Updates the author of the book. */
    public void setAuthor(String author) { this.author = author; }

    /** Sets whether the book is available (true) or borrowed (false). */
    public void setAvailable(boolean available) { this.available = available; }

    /**
     * Returns a readable summary of this book.
     * Example: [B001] "The Great Gatsby" by F. Scott Fitzgerald (Available)
     */
    public String toString() {
        return "[" + bookId + "] \"" + title + "\" by " + author
                + " (" + (available ? "Available" : "Borrowed") + ")";
    }
}
