/**
 * @author      masjohncook
 * @version     0.0.1
 * @copyright   (C) Copyright 2026
 * @license     None
 * @maintainer  masjohncook
 * @email       mas.john.cook@gmail.com
 * @status      None
 */
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

    // bookId stores the unique code for this book, e.g. "B001"
    // private means only this class can access it directly
    private String bookId;

    // title stores the full title of the book
    private String title;

    // author stores the name of the person who wrote the book
    private String author;

    // available tracks whether the book is on the shelf (true) or borrowed (false)
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
        // Assign the given bookId to this object's bookId attribute
        this.bookId = bookId;

        // Assign the given title to this object's title attribute
        this.title = title;

        // Assign the given author to this object's author attribute
        this.author = author;

        // A new book is always available when it is first added to the system
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
        // Create an array that can hold 5 Books objects
        Books[] initial = new Books[5];

        // Fill each slot with a pre-defined book using its ID, title, and author
        initial[0] = new Books("B001", "The Great Gatsby",       "F. Scott Fitzgerald");
        initial[1] = new Books("B002", "To Kill a Mockingbird",  "Harper Lee");
        initial[2] = new Books("B003", "1984",                   "George Orwell");
        initial[3] = new Books("B004", "Brave New World",        "Aldous Huxley");
        initial[4] = new Books("B005", "The Catcher in the Rye", "J.D. Salinger");

        // Return the completed array to whoever called this method
        return initial;
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    // Getters allow other classes to read private attributes safely
    // without being able to change them directly

    /** Returns the unique book ID. */
    public String getBookId() { 
        return bookId; 
    }

    /** Returns the title of the book. */
    public String getTitle() { 
        return title; 
    }

    /** Returns the author of the book. */
    public String getAuthor() { 
        return author; 
    }

    /** Returns true if the book is currently available to borrow. */
    public boolean isAvailable() { 
        return available; 
    }

    // ── Setters ───────────────────────────────────────────────────────────────
    // Setters allow other classes to update private attributes in a controlled way

    /** Updates the book ID. */
    public void setBookId(String bookId) { 
        this.bookId = bookId; 
    }

    /** Updates the title of the book. */
    public void setTitle(String title) { 
        this.title = title; 
    }

    /** Updates the author of the book. */
    public void setAuthor(String author) { 
        this.author = author; 
    }

    /** Sets whether the book is available (true) or borrowed (false). */
    public void setAvailable(boolean available) { 
        this.available = available; 
    }

    /**
     * Returns a readable summary of this book as a single String.
     * This is used whenever a Books object is printed to the console.
     * Example output: [B001] "The Great Gatsby" by F. Scott Fitzgerald (Available)
     */
    public String toString() {
        // Determine the availability status as a word using if-else
        String status;
        if (available == true) {
            status = "Available";
        } else {
            status = "Borrowed";
        }

        // Build and return a formatted string showing the book ID, title, author, and status
        return "[" + bookId + "] \"" + title + "\" by " + author + " (" + status + ")";
    }
}
