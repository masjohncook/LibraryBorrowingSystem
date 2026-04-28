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
 * Represents the librarian who manages the library system.
 *
 * The Librarian is responsible for managing the book catalog and the list of
 * registered members. It also keeps a full record of all borrow and return
 * transactions. On creation, initial data is automatically loaded from the
 * Books and Member classes.
 *
 * Associations:
 *   - catalog[]      : array of all Books in the library
 *   - members[]      : array of all registered Members
 *   - borrowRecords[]: array of all BorrowRecord transactions
 *
 * Attributes:
 *   - librarianId  : unique identifier for the librarian (e.g. "L001")
 *   - name         : full name of the librarian
 *   - catalogCount : number of books currently in the catalog
 *   - memberCount  : number of members currently registered
 *   - recordCount  : number of borrow records logged
 */
public class Librarian {

    // librarianId is the unique code for this librarian, e.g. "L001"
    private String librarianId;

    // name stores the full name of the librarian
    private String name;

    // catalog is an array that holds all the Books in the library
    // This is an association: Librarian -> Books[]
    private Books[] catalog;

    // catalogCount tracks how many books are currently stored in the catalog array
    private int catalogCount;

    // members is an array that holds all registered Member objects
    // This is an association: Librarian -> Member[]
    private Member[] members;

    // memberCount tracks how many members are currently registered
    private int memberCount;

    // borrowRecords stores every borrow and return transaction as a BorrowRecord
    private BorrowRecord[] borrowRecords;

    // recordCount tracks how many borrow records have been created so far
    private int recordCount;

    // Maximum number of books the catalog array can hold
    private static final int MAX_BOOKS = 100;

    // Maximum number of members the members array can hold
    private static final int MAX_MEMBERS = 50;

    // Maximum number of borrow records the borrowRecords array can hold
    private static final int MAX_RECORDS = 200;

    /**
     * Creates a new Librarian and automatically loads the initial
     * book and member data from their respective classes.
     *
     * @param librarianId unique identifier for the librarian
     * @param name        full name of the librarian
     */
    public Librarian(String librarianId, String name) {
        // Save the librarian's unique ID
        this.librarianId = librarianId;

        // Save the librarian's name
        this.name = name;

        // Initialize the catalog array with MAX_BOOKS empty slots
        this.catalog = new Books[MAX_BOOKS];

        // No books have been added yet, so the count starts at 0
        this.catalogCount = 0;

        // Initialize the members array with MAX_MEMBERS empty slots
        this.members = new Member[MAX_MEMBERS];

        // No members registered yet, so the count starts at 0
        this.memberCount = 0;

        // Initialize the borrow records array with MAX_RECORDS empty slots
        this.borrowRecords = new BorrowRecord[MAX_RECORDS];

        // No records have been created yet, so the count starts at 0
        this.recordCount = 0;

        // Call the helper method to fill the arrays with starting data
        loadInitialData();
    }

    /**
     * Loads the starting data by calling getInitialBooks() from the Books class
     * and getInitialMembers() from the Member class.
     * Each class is responsible for defining its own default data.
     */
    private void loadInitialData() {
        // Ask the Books class for its pre-defined list of starting books
        Books[] initialBooks = Books.getInitialBooks();

        // Loop through the returned array and copy each book into the catalog
        for (int i = 0; i < initialBooks.length; i++) {
            catalog[catalogCount++] = initialBooks[i]; // add book and increase count
        }

        // Ask the Member class for its pre-defined list of starting members
        Member[] initialMembers = Member.getInitialMembers();

        // Loop through the returned array and copy each member into the members array
        for (int i = 0; i < initialMembers.length; i++) {
            members[memberCount++] = initialMembers[i]; // add member and increase count
        }
    }

    // ── Book CRUD ─────────────────────────────────────────────────────────────

    /**
     * Adds a new book to the library catalog.
     * Fails if the catalog is full or the book ID already exists.
     *
     * @param bookId unique ID for the new book
     * @param title  title of the new book
     * @param author author of the new book
     * @return the created Books object, or null if the operation failed
     */
    public Books addBook(String bookId, String title, String author) {
        // Check if the catalog has reached its maximum capacity
        if (catalogCount >= MAX_BOOKS) {
            System.out.println("  [FAILED] Catalog is full.");
            return null; // Stop here and report failure
        }

        // Check if a book with the same ID already exists to prevent duplicates
        if (findBookById(bookId) != null) {
            System.out.println("  [FAILED] Book ID \"" + bookId + "\" already exists.");
            return null; // Stop here and report failure
        }

        // Create a new Books object with the provided details
        Books book = new Books(bookId, title, author);

        // Add the new book to the next available slot in the catalog array
        // catalogCount++ places the book at index catalogCount, then increases it by 1
        catalog[catalogCount++] = book;

        // Print a confirmation message showing the book that was added
        System.out.println("  [ADDED] " + book);

        // Return the newly created book object
        return book;
    }

    /**
     * Removes a book from the catalog by its ID.
     * Fails if the book is not found or is currently borrowed.
     * Shifts the remaining entries left to fill the gap.
     *
     * @param bookId the ID of the book to remove
     * @return true if removed successfully, false otherwise
     */
    public boolean removeBook(String bookId) {
        // Loop through the catalog to find the book with the matching ID
        for (int i = 0; i < catalogCount; i++) {
            if (catalog[i].getBookId().equals(bookId)) {

                // Prevent removing a book that is currently borrowed by a member
                if (!catalog[i].isAvailable()) {
                    System.out.println("  [FAILED] Cannot remove a book that is currently borrowed.");
                    return false; // Stop here and report failure
                }

                // Shift all entries after index i one position to the left
                // This closes the gap left by the removed book
                for (int j = i; j < catalogCount - 1; j++) {
                    catalog[j] = catalog[j + 1];
                }

                // Decrease the count first (--catalogCount), then clear the last slot
                catalog[--catalogCount] = null;

                // Print a confirmation message
                System.out.println("  [REMOVED] Book ID \"" + bookId + "\" removed from catalog.");
                return true; // Report success
            }
        }

        // If the loop finishes without finding the book, report failure
        System.out.println("  [FAILED] Book ID \"" + bookId + "\" not found.");
        return false;
    }

    /**
     * Updates the title and/or author of an existing book.
     * Fields left blank (empty string) are not changed.
     * Fails if the book ID is not found in the catalog.
     *
     * @param bookId    the ID of the book to update
     * @param newTitle  new title (leave empty to keep current)
     * @param newAuthor new author (leave empty to keep current)
     * @return true if updated successfully, false otherwise
     */
    public boolean updateBook(String bookId, String newTitle, String newAuthor) {
        // Search for the book by its ID using the helper method
        Books book = findBookById(bookId);

        // If the book was not found, report failure
        if (book == null) {
            System.out.println("  [FAILED] Book ID \"" + bookId + "\" not found.");
            return false;
        }

        // Only update the title if the user provided a non-blank new title
        // trim() removes any leading or trailing spaces from the input
        if (!newTitle.trim().isEmpty()) book.setTitle(newTitle.trim());

        // Only update the author if the user provided a non-blank new author
        if (!newAuthor.trim().isEmpty()) book.setAuthor(newAuthor.trim());

        // Print a confirmation showing the book's updated details
        System.out.println("  [UPDATED] " + book);
        return true; // Report success
    }

    /**
     * Searches the catalog for a book by its ID.
     *
     * @param bookId the book ID to look for
     * @return the matching Books object, or null if not found
     */
    public Books findBookById(String bookId) {
        // Loop through all books in the catalog
        for (int i = 0; i < catalogCount; i++) {
            // Compare each book's ID with the one we are looking for
            if (catalog[i].getBookId().equals(bookId)) {
                return catalog[i]; // Found — return the book immediately
            }
        }
        // Reached the end without finding a match — return null
        return null;
    }

    // ── Member CRUD ───────────────────────────────────────────────────────────

    /**
     * Registers a new member into the system.
     * Fails if the member limit is reached or the member ID already exists.
     *
     * @param memberId unique ID for the new member
     * @param name     full name of the new member
     * @return the created Member object, or null if the operation failed
     */
    public Member registerMember(String memberId, String name) {
        // Check if the members array has reached its maximum capacity
        if (memberCount >= MAX_MEMBERS) {
            System.out.println("  [FAILED] Member limit reached.");
            return null; // Stop here and report failure
        }

        // Check if a member with the same ID already exists to prevent duplicates
        if (findMemberById(memberId) != null) {
            System.out.println("  [FAILED] Member ID \"" + memberId + "\" already exists.");
            return null; // Stop here and report failure
        }

        // Create a new Member object with the given ID and name
        Member member = new Member(memberId, name);

        // Add the new member to the next available slot in the members array
        // memberCount++ places the member at index memberCount, then increases it by 1
        members[memberCount++] = member;

        // Print a confirmation message
        System.out.println("  [REGISTERED] " + member);

        // Return the newly created member object
        return member;
    }

    /**
     * Removes a member from the system by their ID.
     * Fails if the member still has borrowed books or is not found.
     * Shifts the remaining entries left to fill the gap.
     *
     * @param memberId the ID of the member to remove
     * @return true if removed successfully, false otherwise
     */
    public boolean removeMember(String memberId) {
        // Loop through the members array to find the one with the matching ID
        for (int i = 0; i < memberCount; i++) {
            if (members[i].getMemberId().equals(memberId)) {

                // Do not allow removal if the member still has books checked out
                if (members[i].getBorrowCount() > 0) {
                    System.out.println("  [FAILED] Cannot remove a member who still has borrowed books.");
                    return false; // Stop here and report failure
                }

                // Shift all entries after index i one position to the left
                // This closes the gap left by the removed member
                for (int j = i; j < memberCount - 1; j++) {
                    members[j] = members[j + 1];
                }

                // Decrease the count first (--memberCount), then clear the last slot
                members[--memberCount] = null;

                // Print a confirmation message
                System.out.println("  [REMOVED] Member ID \"" + memberId + "\" removed.");
                return true; // Report success
            }
        }

        // If the loop finishes without finding the member, report failure
        System.out.println("  [FAILED] Member ID \"" + memberId + "\" not found.");
        return false;
    }

    /**
     * Updates the name of an existing member.
     * Fails if the member is not found or the new name is blank.
     *
     * @param memberId the ID of the member to update
     * @param newName  the new name to assign
     * @return true if updated successfully, false otherwise
     */
    public boolean updateMember(String memberId, String newName) {
        // Search for the member by their ID using the helper method
        Member member = findMemberById(memberId);

        // If the member was not found, report failure
        if (member == null) {
            System.out.println("  [FAILED] Member ID \"" + memberId + "\" not found.");
            return false;
        }

        // Only update if the new name is not blank
        if (!newName.trim().isEmpty()) {
            // Update the member's name using the setter
            member.setName(newName.trim());

            // Print a confirmation showing the updated member details
            System.out.println("  [UPDATED] " + member);
            return true; // Report success
        }

        // If the new name was blank, report failure
        System.out.println("  [FAILED] New name cannot be empty.");
        return false;
    }

    /**
     * Searches the members array for a member by their ID.
     *
     * @param memberId the member ID to look for
     * @return the matching Member object, or null if not found
     */
    public Member findMemberById(String memberId) {
        // Loop through all registered members
        for (int i = 0; i < memberCount; i++) {
            // Compare each member's ID with the one we are looking for
            if (members[i].getMemberId().equals(memberId)) {
                return members[i]; // Found — return the member immediately
            }
        }
        // Reached the end without finding a match — return null
        return null;
    }

    // ── Borrow Record Management ──────────────────────────────────────────────

    /**
     * Creates and stores a new borrow record when a member borrows a book.
     * Record IDs are generated automatically (e.g. REC001, REC002, ...).
     *
     * @param member the member who is borrowing
     * @param book   the book being borrowed
     */
    public void recordBorrow(Member member, Books book) {
        // If the records array is full, silently stop — no more records can be added
        if (recordCount >= MAX_RECORDS) return;

        // Generate a record ID by padding the record number with leading zeros
        // e.g. recordCount=0 produces "REC001", recordCount=1 produces "REC002"
        String recordId = "REC" + String.format("%03d", recordCount + 1);

        // Create a new BorrowRecord and store it in the next available slot
        // recordCount++ adds the record at index recordCount, then increases it by 1
        borrowRecords[recordCount++] = new BorrowRecord(recordId, member, book, "2026-04-28");
    }

    /**
     * Marks the open borrow record as returned by setting the return date
     * and flipping the returned flag to true.
     * Searches for the most recent open record matching the member and book.
     *
     * @param member the member who is returning the book
     * @param book   the book being returned
     */
    public void recordReturn(Member member, Books book) {
        // Loop through all borrow records to find the right open record
        for (int i = 0; i < recordCount; i++) {
            BorrowRecord r = borrowRecords[i];

            // Look for a record that:
            // 1. Has not been marked as returned yet (!r.isReturned())
            // 2. Belongs to the same member (matching member ID)
            // 3. Is for the same book (matching book ID)
            if (!r.isReturned()
                    && r.getMember().getMemberId().equals(member.getMemberId())
                    && r.getBook().getBookId().equals(book.getBookId())) {

                // Set the return date on the record
                r.setReturnDate("2026-04-28");

                // Mark the record as returned
                r.setReturned(true);

                // Stop searching — we only need to update the first matching open record
                return;
            }
        }
    }

    // ── Display Helpers ───────────────────────────────────────────────────────

    /**
     * Prints all books currently in the catalog to the console,
     * along with their availability status.
     */
    public void displayCatalog() {
        // Print a header showing how many books are in the catalog
        System.out.println("  ---- Book Catalog (" + catalogCount + " book(s)) ----");

        // If there are no books, print a placeholder message
        if (catalogCount == 0) {
            System.out.println("  (empty)");
        } else {
            // Loop through all books and print each one using its toString() method
            for (int i = 0; i < catalogCount; i++) {
                System.out.println("  " + catalog[i]);
            }
        }
    }

    /**
     * Prints all registered members to the console,
     * along with how many books each is currently borrowing.
     */
    public void displayMembers() {
        // Print a header showing how many members are registered
        System.out.println("  ---- Registered Members (" + memberCount + " member(s)) ----");

        // If there are no members, print a placeholder message
        if (memberCount == 0) {
            System.out.println("  (empty)");
        } else {
            // Loop through all members and print each one using its toString() method
            for (int i = 0; i < memberCount; i++) {
                System.out.println("  " + members[i]);
            }
        }
    }

    /**
     * Prints all borrow records to the console, including returned ones.
     */
    public void displayAllRecords() {
        // Print a header showing how many records exist in total
        System.out.println("  ---- Borrow Records (" + recordCount + " total) ----");

        // If no records exist yet, print a placeholder message
        if (recordCount == 0) {
            System.out.println("  (none yet)");
        } else {
            // Loop through all records and print each one using its toString() method
            for (int i = 0; i < recordCount; i++) {
                System.out.println("  " + borrowRecords[i]);
            }
        }
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────


    /** Returns the librarian's unique ID. */
    public String getLibrarianId() { return librarianId; }

    /** Updates the librarian's ID. */
    public void setLibrarianId(String librarianId) { this.librarianId = librarianId; }

    /** Returns the librarian's name. */
    public String getName() { return name; }

    /** Updates the librarian's name. */
    public void setName(String name) { this.name = name; }

    /** Returns the full book catalog array. */
    public Books[] getCatalog() { return catalog; }

    /** Returns the number of books in the catalog. */
    public int getCatalogCount() { return catalogCount; }

    /** Returns the full members array. */
    public Member[] getMembers() { return members; }

    /** Returns the number of registered members. */
    public int getMemberCount() { return memberCount; }

    /** Returns the full borrow records array. */
    public BorrowRecord[] getBorrowRecords() { return borrowRecords; }

    /** Returns the total number of borrow records logged. */
    public int getRecordCount() { return recordCount; }

    /**
     * Returns a readable summary of this librarian as a single String.
     * This is used whenever a Librarian object is printed to the console.
     * Example output: Librarian[L001] Mrs. Smith
     */
    public String toString() {
        // Build a formatted string showing the librarian ID and name
        return "Librarian[" + librarianId + "] " + name;
    }
}
