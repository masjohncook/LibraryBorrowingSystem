package LibraryBorrowingSystem;

/**
 * Represents a library member who can borrow, return, and search for books.
 *
 * Each member has a unique ID and a name. A member can hold up to MAX_BORROW
 * books at the same time. The books currently borrowed are stored in an array.
 * This class also provides the initial member data used at system startup.
 *
 * Attributes:
 *   - memberId      : unique identifier for the member (e.g. "M001")
 *   - name          : full name of the member
 *   - borrowedBooks : array of books the member is currently borrowing
 *   - borrowCount   : number of books currently borrowed
 */
public class Member {
    private String  memberId;
    private String  name;
    private Books[] borrowedBooks;   // association -> Books array
    private int     borrowCount;
    private static final int MAX_BORROW = 5;

    /**
     * Creates a new member with the given ID and name.
     * The borrowed books array is initialized empty.
     *
     * @param memberId unique member identifier
     * @param name     full name of the member
     */
    public Member(String memberId, String name) {
        this.memberId      = memberId;
        this.name          = name;
        this.borrowedBooks = new Books[MAX_BORROW];
        this.borrowCount   = 0;
    }

    /**
     * Returns the pre-defined initial member data for the library.
     * Keeping this data here ensures the Member class owns its own defaults.
     *
     * @return array of Member objects pre-filled with default members
     */
    public static Member[] getInitialMembers() {
        Member[] initial = new Member[3];
        initial[0] = new Member("M001", "Alice");
        initial[1] = new Member("M002", "Bob");
        initial[2] = new Member("M003", "Charlie");
        return initial;
    }

    /**
     * Borrows a book for this member.
     * Fails if the book is already borrowed by someone else,
     * or if this member has reached the maximum borrow limit.
     *
     * @param book the book to borrow
     * @return true if the borrow was successful, false otherwise
     */
    public boolean borrowBook(Books book) {
        if (!book.isAvailable()) {
            System.out.println("  [FAILED] \"" + book.getTitle() + "\" is currently not available.");
            return false;
        }
        if (borrowCount >= MAX_BORROW) {
            System.out.println("  [FAILED] " + name + " has reached the borrow limit (" + MAX_BORROW + ").");
            return false;
        }
        borrowedBooks[borrowCount++] = book;
        book.setAvailable(false);
        System.out.println("  [SUCCESS] " + name + " borrowed \"" + book.getTitle() + "\".");
        return true;
    }

    /**
     * Returns a borrowed book back to the library.
     * Fails if this member does not currently have the given book.
     *
     * @param book the book to return
     * @return true if the return was successful, false otherwise
     */
    public boolean returnBook(Books book) {
        for (int i = 0; i < borrowCount; i++) {
            if (borrowedBooks[i] != null
                    && borrowedBooks[i].getBookId().equals(book.getBookId())) {
                book.setAvailable(true);
                // Fill the gap by moving the last entry into this slot
                borrowedBooks[i] = borrowedBooks[--borrowCount];
                borrowedBooks[borrowCount] = null;
                System.out.println("  [SUCCESS] " + name + " returned \"" + book.getTitle() + "\".");
                return true;
            }
        }
        System.out.println("  [FAILED] " + name + " does not have \"" + book.getTitle() + "\".");
        return false;
    }

    /**
     * Searches the library catalog for books whose title contains the given keyword.
     * The search is case-insensitive. All matching books are printed to the console.
     *
     * @param catalog     the full array of books in the library
     * @param catalogSize the number of valid books in the catalog array
     * @param keyword     the search keyword to match against book titles
     */
    public void searchBook(Books[] catalog, int catalogSize, String keyword) {
        System.out.println("  Search results for \"" + keyword + "\":");
        boolean found = false;
        for (int i = 0; i < catalogSize; i++) {
            if (catalog[i] != null
                    && catalog[i].getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("    -> " + catalog[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("    No books found matching \"" + keyword + "\".");
        }
    }

    // Getters — allow other classes to read private attributes

    /** Returns the unique member ID. */
    public String getMemberId() { return memberId; }

    /** Returns the name of the member. */
    public String getName() { return name; }

    /** Returns the array of books currently borrowed by this member. */
    public Books[] getBorrowedBooks() { return borrowedBooks; }

    /** Returns the number of books currently borrowed by this member. */
    public int getBorrowCount() { return borrowCount; }

    // Setters — allow controlled modification of private attributes

    /** Updates the member ID. */
    public void setMemberId(String memberId) { this.memberId = memberId; }

    /** Updates the name of the member. */
    public void setName(String name) { this.name = name; }

    /**
     * Returns a readable summary of this member.
     * Example: Member[M001] Alice (borrowing: 1 book(s))
     */
    public String toString() {
        return "Member[" + memberId + "] " + name
                + " (borrowing: " + borrowCount + " book(s))";
    }
}
