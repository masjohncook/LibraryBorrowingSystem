package LibraryBorrowingSystem;

// Import the Scanner class from java.util so we can read keyboard input
import java.util.Scanner;

/**
 * Entry point for the Library Borrowing System.
 *
 * This class starts the application by creating a Librarian object (which
 * automatically loads initial book and member data) and then presents a
 * menu-driven interface for the user to interact with the system.
 *
 * Menu operations available:
 *   Book Management   : Add, Remove, Update, View books
 *   Member Management : Register, Remove, Update, View members
 *   Borrow & Return   : Borrow a book, Return a book, Search by title
 *   Records           : View all borrow/return records
 */
public class main {

    /**
     * Main method — launches the library system and runs the menu loop.
     * The loop continues until the user selects option 0 (Exit).
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        // Create a Scanner object to read input typed by the user on the keyboard
        Scanner sc = new Scanner(System.in);

        // Create the Librarian object — the constructor inside Librarian
        // automatically calls loadInitialData() to pre-fill books and members
        Librarian librarian = new Librarian("L001", "Mrs. Smith");

        // Print a welcome message using the librarian's name
        System.out.println("=== Welcome, " + librarian.getName() + "! ===\n");

        // choice stores the menu option the user picks each time
        // Start at -1 so the while loop begins immediately (since -1 != 0)
        int choice = -1;

        // Keep showing the menu and processing choices until the user types 0 (Exit)
        while (choice != 0) {

            // Print the full menu to the console
            System.out.println("\n============================================");
            System.out.println("          LIBRARY BORROWING SYSTEM         ");
            System.out.println("============================================");
            System.out.println(" --- Book Management ---");
            System.out.println("  1. Add Book");
            System.out.println("  2. Remove Book");
            System.out.println("  3. Update Book");
            System.out.println("  4. View All Books");
            System.out.println(" --- Member Management ---");
            System.out.println("  5. Register Member");
            System.out.println("  6. Remove Member");
            System.out.println("  7. Update Member Name");
            System.out.println("  8. View All Members");
            System.out.println(" --- Borrow & Return ---");
            System.out.println("  9. Borrow Book");
            System.out.println(" 10. Return Book");
            System.out.println(" 11. Search Book by Title");
            System.out.println(" 12. View All Borrow Records");
            System.out.println("--------------------------------------------");
            System.out.println("  0. Exit");
            System.out.println("============================================");
            System.out.print("Enter choice: ");

            // Try to read an integer from the user's input
            // hasNextInt() returns true only if the user typed a number
            if (sc.hasNextInt()) {
                choice = sc.nextInt();    // read the number the user typed
                sc.nextLine();            // consume the leftover newline character after the number
            } else {
                sc.nextLine();            // discard the invalid non-numeric input
                System.out.println("  Please enter a number from the menu.");
                continue;                 // skip the switch block and show the menu again
            }

            // Use switch-case to run the correct operation based on the user's choice
            switch (choice) {

                // ── 1. Add Book ───────────────────────────────────────────────
                case 1:
                    // Ask the user to enter the details for the new book
                    System.out.print("  Book ID   : ");
                    String newId = sc.nextLine().trim();     // trim() removes extra spaces

                    System.out.print("  Title     : ");
                    String newTitle = sc.nextLine().trim();

                    System.out.print("  Author    : ");
                    String newAuthor = sc.nextLine().trim();

                    // Use if-else to validate that all fields are filled in
                    if (newId.isEmpty() || newTitle.isEmpty() || newAuthor.isEmpty()) {
                        // isEmpty() returns true if the string has no characters
                        System.out.println("  [FAILED] All fields are required.");
                    } else {
                        // All fields are valid — ask the librarian to add the book
                        librarian.addBook(newId, newTitle, newAuthor);
                    }
                    break; // exit this case and go back to the menu

                // ── 2. Remove Book ────────────────────────────────────────────
                case 2:
                    // Show the current catalog so the user can see available book IDs
                    librarian.displayCatalog();

                    System.out.print("  Enter Book ID to remove: ");
                    String removeBookId = sc.nextLine().trim();

                    // Ask the librarian to remove the book with the given ID
                    librarian.removeBook(removeBookId);
                    break;

                // ── 3. Update Book ────────────────────────────────────────────
                case 3:
                    // Show the current catalog so the user can see which book to update
                    librarian.displayCatalog();

                    System.out.print("  Enter Book ID to update: ");
                    String updateBookId = sc.nextLine().trim();

                    // Ask for a new title — pressing Enter without typing keeps the current one
                    System.out.print("  New Title  (press Enter to keep current): ");
                    String updatedTitle = sc.nextLine(); // do not trim — blank is intentional

                    // Ask for a new author — pressing Enter without typing keeps the current one
                    System.out.print("  New Author (press Enter to keep current): ");
                    String updatedAuthor = sc.nextLine(); // do not trim — blank is intentional

                    // Ask the librarian to update the book — empty strings will be skipped inside
                    librarian.updateBook(updateBookId, updatedTitle, updatedAuthor);
                    break;

                // ── 4. View All Books ─────────────────────────────────────────
                case 4:
                    // Display every book in the catalog with its availability status
                    librarian.displayCatalog();
                    break;

                // ── 5. Register Member ────────────────────────────────────────
                case 5:
                    // Ask the user to enter details for the new member
                    System.out.print("  Member ID : ");
                    String newMemberId = sc.nextLine().trim();

                    System.out.print("  Name      : ");
                    String newMemberName = sc.nextLine().trim();

                    // Use if-else to validate that both fields are filled in
                    if (newMemberId.isEmpty() || newMemberName.isEmpty()) {
                        System.out.println("  [FAILED] All fields are required.");
                    } else {
                        // Both fields are valid — ask the librarian to register the member
                        librarian.registerMember(newMemberId, newMemberName);
                    }
                    break;

                // ── 6. Remove Member ──────────────────────────────────────────
                case 6:
                    // Show all members so the user can see available member IDs
                    librarian.displayMembers();

                    System.out.print("  Enter Member ID to remove: ");
                    String removeMemberId = sc.nextLine().trim();

                    // Ask the librarian to remove the member with the given ID
                    librarian.removeMember(removeMemberId);
                    break;

                // ── 7. Update Member Name ─────────────────────────────────────
                case 7:
                    // Show all members so the user can see which one to update
                    librarian.displayMembers();

                    System.out.print("  Enter Member ID to update: ");
                    String updateMemberId = sc.nextLine().trim();

                    System.out.print("  New Name  : ");
                    String updatedName = sc.nextLine().trim();

                    // Ask the librarian to update the member's name
                    librarian.updateMember(updateMemberId, updatedName);
                    break;

                // ── 8. View All Members ───────────────────────────────────────
                case 8:
                    // Display every registered member and their borrow count
                    librarian.displayMembers();
                    break;

                // ── 9. Borrow Book ────────────────────────────────────────────
                case 9:
                    // Show all members so the user can pick a member ID
                    librarian.displayMembers();

                    System.out.print("  Enter Member ID : ");
                    String borrowMemberId = sc.nextLine().trim();

                    // Search for the member by their ID — returns null if not found
                    Member borrower = librarian.findMemberById(borrowMemberId);

                    // Use if-else to check whether the member was found
                    if (borrower == null) {
                        System.out.println("  [FAILED] Member not found.");
                    } else {
                        // Member found — now show the catalog so the user can pick a book
                        librarian.displayCatalog();

                        System.out.print("  Enter Book ID   : ");
                        String borrowBookId = sc.nextLine().trim();

                        // Search for the book by its ID — returns null if not found
                        Books bookToBorrow = librarian.findBookById(borrowBookId);

                        if (bookToBorrow == null) {
                            System.out.println("  [FAILED] Book not found.");
                        } else {
                            // Both member and book exist — attempt to borrow
                            // borrowBook() returns true if successful
                            if (borrower.borrowBook(bookToBorrow)) {
                                // Only create a borrow record if the borrow was successful
                                librarian.recordBorrow(borrower, bookToBorrow);
                            }
                        }
                    }
                    break;

                // ── 10. Return Book ───────────────────────────────────────────
                case 10:
                    // Show all members so the user can pick a member ID
                    librarian.displayMembers();

                    System.out.print("  Enter Member ID : ");
                    String returnMemberId = sc.nextLine().trim();

                    // Search for the member by their ID
                    Member returner = librarian.findMemberById(returnMemberId);

                    if (returner == null) {
                        // Member does not exist in the system
                        System.out.println("  [FAILED] Member not found.");
                    } else if (returner.getBorrowCount() == 0) {
                        // Member exists but has no books to return
                        System.out.println("  " + returner.getName() + " has no borrowed books.");
                    } else {
                        // Member exists and has books — show what they currently have
                        System.out.println("  Books currently borrowed by " + returner.getName() + ":");

                        // Get the array of books this member currently holds
                        Books[] borrowed = returner.getBorrowedBooks();

                        // Loop through only the active borrow slots (up to borrowCount)
                        for (int i = 0; i < returner.getBorrowCount(); i++) {
                            System.out.println("    " + borrowed[i]);
                        }

                        System.out.print("  Enter Book ID to return: ");
                        String returnBookId = sc.nextLine().trim();

                        // Search for the book in the full catalog by its ID
                        Books bookToReturn = librarian.findBookById(returnBookId);

                        if (bookToReturn == null) {
                            System.out.println("  [FAILED] Book not found.");
                        } else {
                            // Attempt the return — returnBook() returns true if successful
                            if (returner.returnBook(bookToReturn)) {
                                // Only update the borrow record if the return was successful
                                librarian.recordReturn(returner, bookToReturn);
                            }
                        }
                    }
                    break;

                // ── 11. Search Book by Title ──────────────────────────────────
                case 11:
                    // Show all members so the user can pick who is searching
                    librarian.displayMembers();

                    System.out.print("  Enter Member ID : ");
                    String searchMemberId = sc.nextLine().trim();

                    // Search for the member by their ID
                    Member searcher = librarian.findMemberById(searchMemberId);

                    if (searcher == null) {
                        System.out.println("  [FAILED] Member not found.");
                    } else {
                        System.out.print("  Enter keyword   : ");
                        String keyword = sc.nextLine().trim();

                        // Delegate the search to the member — pass the full catalog and its size
                        // The member's searchBook() method loops through the catalog and prints matches
                        searcher.searchBook(librarian.getCatalog(), librarian.getCatalogCount(), keyword);
                    }
                    break;

                // ── 12. View All Borrow Records ───────────────────────────────
                case 12:
                    // Display all borrow/return records logged by the librarian
                    librarian.displayAllRecords();
                    break;

                // ── 0. Exit ───────────────────────────────────────────────────
                case 0:
                    // Print a goodbye message — the while loop will now stop because choice == 0
                    System.out.println("\n  Goodbye! Library system closed.");
                    break;

                // ── Invalid Input ─────────────────────────────────────────────
                default:
                    // The user typed a number that is not on the menu
                    System.out.println("  Invalid choice. Please enter a number from the menu.");
            }
        }

        // Close the Scanner to release the keyboard input resource
        sc.close();
    }
}
