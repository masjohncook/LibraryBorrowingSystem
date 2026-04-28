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

    // memberId is the unique code for this member, e.g. "M001"
    private String memberId;

    // name stores the full name of the member
    private String name;

    // borrowedBooks is an array holding the books this member currently has
    // This is an association: Member -> Books[]
    private Books[] borrowedBooks;

    // borrowCount tracks how many books the member is currently borrowing
    private int borrowCount;

    // MAX_BORROW is a constant — a member can borrow at most 5 books at once
    // static means it belongs to the class, not to any single object
    // final means this value cannot be changed after it is set
    private static final int MAX_BORROW = 5;

    /**
     * Creates a new member with the given ID and name.
     * The borrowed books array is initialized empty.
     *
     * @param memberId unique member identifier
     * @param name     full name of the member
     */
    public Member(String memberId, String name) {
        // Assign the given memberId to this object's memberId attribute
        this.memberId = memberId;

        // Assign the given name to this object's name attribute
        this.name = name;

        // Create an empty array with MAX_BORROW slots to hold borrowed books
        this.borrowedBooks = new Books[MAX_BORROW];

        // No books are borrowed yet, so the count starts at 0
        this.borrowCount = 0;
    }

    /**
     * Returns the pre-defined initial member data for the library.
     * Keeping this data here ensures the Member class owns its own defaults.
     *
     * @return array of Member objects pre-filled with default members
     */
    public static Member[] getInitialMembers() {
        // Create an array that can hold 3 Member objects
        Member[] initial = new Member[3];

        // Fill each slot with a pre-defined member using their ID and name
        initial[0] = new Member("M001", "Alice");
        initial[1] = new Member("M002", "Bob");
        initial[2] = new Member("M003", "Charlie");

        // Return the completed array to whoever called this method
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
        // Check if the book is available — if not, reject the request
        if (!book.isAvailable()) {
            System.out.println("  [FAILED] \"" + book.getTitle() + "\" is currently not available.");
            return false; // Stop here and report failure
        }

        // Check if this member has already reached their borrowing limit
        if (borrowCount >= MAX_BORROW) {
            System.out.println("  [FAILED] " + name + " has reached the borrow limit (" + MAX_BORROW + ").");
            return false; // Stop here and report failure
        }

        // Add the book to the member's borrowedBooks array at the next open slot
        // borrowCount++ adds the book at index borrowCount, then increases borrowCount by 1
        borrowedBooks[borrowCount++] = book;

        // Mark the book as no longer available in the system
        book.setAvailable(false);

        // Confirm the successful borrow to the console
        System.out.println("  [SUCCESS] " + name + " borrowed \"" + book.getTitle() + "\".");

        // Return true to indicate the borrow was successful
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
        // Loop through all books this member is currently borrowing
        for (int i = 0; i < borrowCount; i++) {

            // Check if this slot has a book and its ID matches the book to return
            if (borrowedBooks[i] != null
                    && borrowedBooks[i].getBookId().equals(book.getBookId())) {

                // Mark the book as available again in the system
                book.setAvailable(true);

                // Remove the book from the array by replacing it with the last entry
                // --borrowCount decreases the count first, then uses that value as the index
                borrowedBooks[i] = borrowedBooks[--borrowCount];

                // Clear the last slot to avoid keeping a duplicate reference
                borrowedBooks[borrowCount] = null;

                // Confirm the successful return to the console
                System.out.println("  [SUCCESS] " + name + " returned \"" + book.getTitle() + "\".");

                // Return true to indicate the return was successful
                return true;
            }
        }

        // If the loop finishes without finding the book, report failure
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
        // Print a header showing what keyword is being searched
        System.out.println("  Search results for \"" + keyword + "\":");

        // found tracks whether at least one matching book was found
        boolean found = false;

        // Loop through every valid book in the catalog
        for (int i = 0; i < catalogSize; i++) {

            // Convert both strings to lowercase so the search is case-insensitive
            // contains() checks if the title includes the keyword anywhere inside it
            if (catalog[i] != null
                    && catalog[i].getTitle().toLowerCase().contains(keyword.toLowerCase())) {

                // Print the matching book's details
                System.out.println("    -> " + catalog[i]);

                // Mark that we found at least one result
                found = true;
            }
        }

        // If no match was found after checking all books, print a message
        if (!found) {
            System.out.println("    No books found matching \"" + keyword + "\".");
        }
    }

    // ── Getters ───────────────────────────────────────────────────────────────


    /** Returns the unique member ID. */
    public String getMemberId() { 
        return memberId; 
    }

    /** Returns the name of the member. */
    public String getName() { 
        return name; 
        

    /** Returns the array of books currently borrowed by this member. */
    public Books[] getBorrowedBooks() { 
        return borrowedBooks; 
    }

    /** Returns the number of books currently borrowed by this member. */
    public int getBorrowCount() { 
        return borrowCount; 
    }

    // ── Setters ───────────────────────────────────────────────────────────────


    /** Updates the member ID. */
    public void setMemberId(String memberId) { 
        this.memberId = memberId; 
    }

    /** Updates the name of the member. */
    public void setName(String name) { 
        this.name = name; 
    }

    /**
     * Returns a readable summary of this member as a single String.
     * This is used whenever a Member object is printed to the console.
     * Example output: Member[M001] Alice (borrowing: 1 book(s))
     */
    public String toString() {
        // Build a formatted string showing the member ID, name, and borrow count
        return "Member[" + memberId + "] " + name
                + " (borrowing: " + borrowCount + " book(s))";
    }
}
