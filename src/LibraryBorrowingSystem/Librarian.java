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
    private String         librarianId;
    private String         name;
    private Books[]        catalog;         // association -> Books array
    private int            catalogCount;
    private Member[]       members;         // association -> Member array
    private int            memberCount;
    private BorrowRecord[] borrowRecords;
    private int            recordCount;

    private static final int MAX_BOOKS   = 100;
    private static final int MAX_MEMBERS = 50;
    private static final int MAX_RECORDS = 200;

    /**
     * Creates a new Librarian and automatically loads the initial
     * book and member data from their respective classes.
     *
     * @param librarianId unique identifier for the librarian
     * @param name        full name of the librarian
     */
    public Librarian(String librarianId, String name) {
        this.librarianId   = librarianId;
        this.name          = name;
        this.catalog       = new Books[MAX_BOOKS];
        this.catalogCount  = 0;
        this.members       = new Member[MAX_MEMBERS];
        this.memberCount   = 0;
        this.borrowRecords = new BorrowRecord[MAX_RECORDS];
        this.recordCount   = 0;
        loadInitialData();
    }

    /**
     * Loads the starting data by calling getInitialBooks() from the Books class
     * and getInitialMembers() from the Member class.
     * Each class is responsible for defining its own default data.
     */
    private void loadInitialData() {
        Books[] initialBooks = Books.getInitialBooks();
        for (int i = 0; i < initialBooks.length; i++) {
            catalog[catalogCount++] = initialBooks[i];
        }

        Member[] initialMembers = Member.getInitialMembers();
        for (int i = 0; i < initialMembers.length; i++) {
            members[memberCount++] = initialMembers[i];
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
        if (catalogCount >= MAX_BOOKS) {
            System.out.println("  [FAILED] Catalog is full.");
            return null;
        }
        if (findBookById(bookId) != null) {
            System.out.println("  [FAILED] Book ID \"" + bookId + "\" already exists.");
            return null;
        }
        Books book = new Books(bookId, title, author);
        catalog[catalogCount++] = book;
        System.out.println("  [ADDED] " + book);
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
        for (int i = 0; i < catalogCount; i++) {
            if (catalog[i].getBookId().equals(bookId)) {
                if (!catalog[i].isAvailable()) {
                    System.out.println("  [FAILED] Cannot remove a book that is currently borrowed.");
                    return false;
                }
                // Shift array left to close the gap
                for (int j = i; j < catalogCount - 1; j++) {
                    catalog[j] = catalog[j + 1];
                }
                catalog[--catalogCount] = null;
                System.out.println("  [REMOVED] Book ID \"" + bookId + "\" removed from catalog.");
                return true;
            }
        }
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
        Books book = findBookById(bookId);
        if (book == null) {
            System.out.println("  [FAILED] Book ID \"" + bookId + "\" not found.");
            return false;
        }
        if (!newTitle.trim().isEmpty())  book.setTitle(newTitle.trim());
        if (!newAuthor.trim().isEmpty()) book.setAuthor(newAuthor.trim());
        System.out.println("  [UPDATED] " + book);
        return true;
    }

    /**
     * Searches the catalog for a book by its ID.
     *
     * @param bookId the book ID to look for
     * @return the matching Books object, or null if not found
     */
    public Books findBookById(String bookId) {
        for (int i = 0; i < catalogCount; i++) {
            if (catalog[i].getBookId().equals(bookId)) {
                return catalog[i];
            }
        }
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
        if (memberCount >= MAX_MEMBERS) {
            System.out.println("  [FAILED] Member limit reached.");
            return null;
        }
        if (findMemberById(memberId) != null) {
            System.out.println("  [FAILED] Member ID \"" + memberId + "\" already exists.");
            return null;
        }
        Member member = new Member(memberId, name);
        members[memberCount++] = member;
        System.out.println("  [REGISTERED] " + member);
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
        for (int i = 0; i < memberCount; i++) {
            if (members[i].getMemberId().equals(memberId)) {
                if (members[i].getBorrowCount() > 0) {
                    System.out.println("  [FAILED] Cannot remove a member who still has borrowed books.");
                    return false;
                }
                // Shift array left to close the gap
                for (int j = i; j < memberCount - 1; j++) {
                    members[j] = members[j + 1];
                }
                members[--memberCount] = null;
                System.out.println("  [REMOVED] Member ID \"" + memberId + "\" removed.");
                return true;
            }
        }
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
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("  [FAILED] Member ID \"" + memberId + "\" not found.");
            return false;
        }
        if (!newName.trim().isEmpty()) {
            member.setName(newName.trim());
            System.out.println("  [UPDATED] " + member);
            return true;
        }
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
        for (int i = 0; i < memberCount; i++) {
            if (members[i].getMemberId().equals(memberId)) {
                return members[i];
            }
        }
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
        if (recordCount >= MAX_RECORDS) return;
        String recordId = "REC" + String.format("%03d", recordCount + 1);
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
        for (int i = 0; i < recordCount; i++) {
            BorrowRecord r = borrowRecords[i];
            if (!r.isReturned()
                    && r.getMember().getMemberId().equals(member.getMemberId())
                    && r.getBook().getBookId().equals(book.getBookId())) {
                r.setReturnDate("2026-04-28");
                r.setReturned(true);
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
        System.out.println("  ---- Book Catalog (" + catalogCount + " book(s)) ----");
        if (catalogCount == 0) {
            System.out.println("  (empty)");
        } else {
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
        System.out.println("  ---- Registered Members (" + memberCount + " member(s)) ----");
        if (memberCount == 0) {
            System.out.println("  (empty)");
        } else {
            for (int i = 0; i < memberCount; i++) {
                System.out.println("  " + members[i]);
            }
        }
    }

    /**
     * Prints all borrow records to the console, including returned ones.
     */
    public void displayAllRecords() {
        System.out.println("  ---- Borrow Records (" + recordCount + " total) ----");
        if (recordCount == 0) {
            System.out.println("  (none yet)");
        } else {
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
     * Returns a readable summary of this librarian.
     * Example: Librarian[L001] Mrs. Smith
     */
    public String toString() {
        return "Librarian[" + librarianId + "] " + name;
    }
}
