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
    private String  recordId;
    private Member  member;       // association -> Member
    private Books   book;         // association -> Books
    private String  borrowDate;
    private String  returnDate;
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
        this.recordId   = recordId;
        this.member     = member;
        this.book       = book;
        this.borrowDate = borrowDate;
        this.returnDate = "-";
        this.returned   = false;
    }

    // Getters — allow other classes to read private attributes

    /** Returns the unique record ID. */
    public String getRecordId() { return recordId; }

    /** Returns the member associated with this record. */
    public Member getMember() { return member; }

    /** Returns the book associated with this record. */
    public Books getBook() { return book; }

    /** Returns the date the book was borrowed. */
    public String getBorrowDate() { return borrowDate; }

    /** Returns the date the book was returned, or "-" if not yet returned. */
    public String getReturnDate() { return returnDate; }

    /** Returns true if the book has been returned. */
    public boolean isReturned() { return returned; }

    // Setters — allow controlled modification of private attributes

    /** Updates the record ID. */
    public void setRecordId(String recordId) { this.recordId = recordId; }

    /** Updates the member linked to this record. */
    public void setMember(Member member) { this.member = member; }

    /** Updates the book linked to this record. */
    public void setBook(Books book) { this.book = book; }

    /** Updates the borrow date. */
    public void setBorrowDate(String borrowDate) { this.borrowDate = borrowDate; }

    /** Updates the return date when the book is handed back. */
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }

    /** Marks whether the book has been returned. */
    public void setReturned(boolean returned) { this.returned = returned; }

    /**
     * Returns a readable summary of this borrow record.
     * Example: Record[REC001] Member: Alice | Book: 1984 | Borrowed: 2026-04-28 | Returned: -
     */
    public String toString() {
        return "Record[" + recordId + "]"
                + " Member: " + member.getName()
                + " | Book: "     + book.getTitle()
                + " | Borrowed: " + borrowDate
                + " | Returned: " + returnDate;
    }
}
