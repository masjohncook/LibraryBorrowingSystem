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
 * Represents a single borrow transaction in the library system.
 *
 * A BorrowRecord is created every time a member borrows a book, and it is
 * updated when the book is returned. It links a Member and a Books object
 * together with date information, forming an association between those classes.
 *
 * Attributes:
 *   - recordId   : unique identifier for this record (e.g. "REC001")
 *   - member     : the member who borrowed the book (association -> Member)
 *   - book       : the book that was borrowed (association -> Books)
 *   - borrowDate : the date the book was borrowed
 *   - returnDate : the date the book was returned ("-" if not yet returned)
 *   - returned   : true if the book has been returned, false otherwise
 */
public class BorrowRecord {

    // recordId is the unique ID for this transaction, e.g. "REC001"
    private String recordId;

    // member holds a reference to the Member who borrowed the book
    // This is an association: BorrowRecord -> Member
    private Member member;

    // book holds a reference to the Books object that was borrowed
    // This is an association: BorrowRecord -> Books
    private Books book;

    // borrowDate stores the date when the book was taken out
    private String borrowDate;

    // returnDate stores the date when the book was brought back
    // Set to "-" by default until the book is actually returned
    private String returnDate;

    // returned is a flag: false means the book is still out, true means it has been returned
    private boolean returned;

    /**
     * Creates a new borrow record when a member takes a book.
     * The record starts as not returned, with returnDate set to "-".
     *
     * @param recordId   unique identifier for this record
     * @param member     the member borrowing the book
     * @param book       the book being borrowed
     * @param borrowDate the date the book is borrowed
     */
    public BorrowRecord(String recordId, Member member, Books book, String borrowDate) {
        // Save the unique record ID
        this.recordId = recordId;

        // Save the reference to the member who is borrowing the book
        this.member = member;

        // Save the reference to the book being borrowed
        this.book = book;

        // Save the date the book was borrowed
        this.borrowDate = borrowDate;

        // Set return date to "-" because the book has not been returned yet
        this.returnDate = "-";

        // Mark the record as not returned yet
        this.returned = false;
    }

    // ── Getters ───────────────────────────────────────────────────────────────


    /** Returns the unique record ID. */
    public String getRecordId() { 
        return recordId; 
    }

    /** Returns the member associated with this record. */
    public Member getMember() { 
        return member; 
    }

    /** Returns the book associated with this record. */
    public Books getBook() { 
        return book; 
    }

    /** Returns the date the book was borrowed. */
    public String getBorrowDate() { 
        return borrowDate; 
    }

    /** Returns the date the book was returned, or "-" if not yet returned. */
    public String getReturnDate() { 
        return returnDate; 
    }

    /** Returns true if the book has been returned. */
    public boolean isReturned() { 
        return returned; 
    }

    // ── Setters ───────────────────────────────────────────────────────────────


    /** Updates the record ID. */
    public void setRecordId(String recordId) { 
        this.recordId = recordId; 
    }

    /** Updates the member linked to this record. */
    public void setMember(Member member) { 
        this.member = member; 
    }

    /** Updates the book linked to this record. */
    public void setBook(Books book) { 
        this.book = book; 
    }

    /** Updates the borrow date. */
    public void setBorrowDate(String borrowDate) { 
        this.borrowDate = borrowDate; 
    }

    /** Updates the return date when the book is handed back. */
    public void setReturnDate(String returnDate) { 
        this.returnDate = returnDate; 
    }

    /** Marks whether the book has been returned. */
    public void setReturned(boolean returned) { 
        this.returned = returned; 
    }

    /**
     * Returns a readable summary of this borrow record as a single String.
     * This is used whenever a BorrowRecord object is printed to the console.
     * Example: Record[REC001] Member: Alice | Book: 1984 | Borrowed: 2026-04-28 | Returned: -
     */
    public String toString() {
        // Build a formatted string combining the record ID, member name,
        // book title, borrow date, and return date into one readable line
        return "Record[" + recordId + "]"
                + " Member: "    + member.getName()   // get the member's name via getter
                + " | Book: "    + book.getTitle()     // get the book's title via getter
                + " | Borrowed: " + borrowDate
                + " | Returned: " + returnDate;
    }
}
