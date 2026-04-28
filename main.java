package LibraryBorrowingSystem;

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
        Scanner sc = new Scanner(System.in);

        // Create the librarian — initial book and member data is loaded inside the class
        Librarian librarian = new Librarian("L001", "Mrs. Smith");
        System.out.println("=== Welcome, " + librarian.getName() + "! ===\n");

        int choice = -1;

        // Keep showing the menu until the user chooses to exit
        while (choice != 0) {
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

            // Read the menu choice — reject non-numeric input gracefully
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // consume the leftover newline character
            } else {
                sc.nextLine(); // discard invalid input
                System.out.println("  Please enter a number from the menu.");
                continue;
            }

            switch (choice) {

                // ── 1. Add Book ───────────────────────────────────────────────
                case 1:
                    System.out.print("  Book ID   : ");
                    String newId = sc.nextLine().trim();
                    System.out.print("  Title     : ");
                    String newTitle = sc.nextLine().trim();
                    System.out.print("  Author    : ");
                    String newAuthor = sc.nextLine().trim();

                    // Validate that all fields are filled before adding
                    if (newId.isEmpty() || newTitle.isEmpty() || newAuthor.isEmpty()) {
                        System.out.println("  [FAILED] All fields are required.");
                    } else {
                        librarian.addBook(newId, newTitle, newAuthor);
                    }
                    break;

                // ── 2. Remove Book ────────────────────────────────────────────
                case 2:
                    librarian.displayCatalog();
                    System.out.print("  Enter Book ID to remove: ");
                    String removeBookId = sc.nextLine().trim();
                    librarian.removeBook(removeBookId);
                    break;

                // ── 3. Update Book ────────────────────────────────────────────
                case 3:
                    librarian.displayCatalog();
                    System.out.print("  Enter Book ID to update: ");
                    String updateBookId = sc.nextLine().trim();
                    System.out.print("  New Title  (press Enter to keep current): ");
                    String updatedTitle = sc.nextLine();
                    System.out.print("  New Author (press Enter to keep current): ");
                    String updatedAuthor = sc.nextLine();
                    librarian.updateBook(updateBookId, updatedTitle, updatedAuthor);
                    break;

                // ── 4. View All Books ─────────────────────────────────────────
                case 4:
                    librarian.displayCatalog();
                    break;

                // ── 5. Register Member ────────────────────────────────────────
                case 5:
                    System.out.print("  Member ID : ");
                    String newMemberId = sc.nextLine().trim();
                    System.out.print("  Name      : ");
                    String newMemberName = sc.nextLine().trim();

                    // Validate that all fields are filled before registering
                    if (newMemberId.isEmpty() || newMemberName.isEmpty()) {
                        System.out.println("  [FAILED] All fields are required.");
                    } else {
                        librarian.registerMember(newMemberId, newMemberName);
                    }
                    break;

                // ── 6. Remove Member ──────────────────────────────────────────
                case 6:
                    librarian.displayMembers();
                    System.out.print("  Enter Member ID to remove: ");
                    String removeMemberId = sc.nextLine().trim();
                    librarian.removeMember(removeMemberId);
                    break;

                // ── 7. Update Member Name ─────────────────────────────────────
                case 7:
                    librarian.displayMembers();
                    System.out.print("  Enter Member ID to update: ");
                    String updateMemberId = sc.nextLine().trim();
                    System.out.print("  New Name  : ");
                    String updatedName = sc.nextLine().trim();
                    librarian.updateMember(updateMemberId, updatedName);
                    break;

                // ── 8. View All Members ───────────────────────────────────────
                case 8:
                    librarian.displayMembers();
                    break;

                // ── 9. Borrow Book ────────────────────────────────────────────
                case 9:
                    librarian.displayMembers();
                    System.out.print("  Enter Member ID : ");
                    String borrowMemberId = sc.nextLine().trim();
                    Member borrower = librarian.findMemberById(borrowMemberId);

                    if (borrower == null) {
                        System.out.println("  [FAILED] Member not found.");
                    } else {
                        librarian.displayCatalog();
                        System.out.print("  Enter Book ID   : ");
                        String borrowBookId = sc.nextLine().trim();
                        Books bookToBorrow = librarian.findBookById(borrowBookId);

                        if (bookToBorrow == null) {
                            System.out.println("  [FAILED] Book not found.");
                        } else {
                            // Borrow the book and log the transaction if successful
                            if (borrower.borrowBook(bookToBorrow)) {
                                librarian.recordBorrow(borrower, bookToBorrow);
                            }
                        }
                    }
                    break;

                // ── 10. Return Book ───────────────────────────────────────────
                case 10:
                    librarian.displayMembers();
                    System.out.print("  Enter Member ID : ");
                    String returnMemberId = sc.nextLine().trim();
                    Member returner = librarian.findMemberById(returnMemberId);

                    if (returner == null) {
                        System.out.println("  [FAILED] Member not found.");
                    } else if (returner.getBorrowCount() == 0) {
                        // Member has nothing to return
                        System.out.println("  " + returner.getName() + " has no borrowed books.");
                    } else {
                        // Show which books this member currently has
                        System.out.println("  Books currently borrowed by " + returner.getName() + ":");
                        Books[] borrowed = returner.getBorrowedBooks();
                        for (int i = 0; i < returner.getBorrowCount(); i++) {
                            System.out.println("    " + borrowed[i]);
                        }
                        System.out.print("  Enter Book ID to return: ");
                        String returnBookId = sc.nextLine().trim();
                        Books bookToReturn = librarian.findBookById(returnBookId);

                        if (bookToReturn == null) {
                            System.out.println("  [FAILED] Book not found.");
                        } else {
                            // Return the book and update the borrow record
                            if (returner.returnBook(bookToReturn)) {
                                librarian.recordReturn(returner, bookToReturn);
                            }
                        }
                    }
                    break;

                // ── 11. Search Book by Title ──────────────────────────────────
                case 11:
                    librarian.displayMembers();
                    System.out.print("  Enter Member ID : ");
                    String searchMemberId = sc.nextLine().trim();
                    Member searcher = librarian.findMemberById(searchMemberId);

                    if (searcher == null) {
                        System.out.println("  [FAILED] Member not found.");
                    } else {
                        System.out.print("  Enter keyword   : ");
                        String keyword = sc.nextLine().trim();
                        // Delegate the search to the member using the full catalog
                        searcher.searchBook(librarian.getCatalog(), librarian.getCatalogCount(), keyword);
                    }
                    break;

                // ── 12. View All Borrow Records ───────────────────────────────
                case 12:
                    librarian.displayAllRecords();
                    break;

                // ── 0. Exit ───────────────────────────────────────────────────
                case 0:
                    System.out.println("\n  Goodbye! Library system closed.");
                    break;

                // ── Invalid Input ─────────────────────────────────────────────
                default:
                    System.out.println("  Invalid choice. Please enter a number from the menu.");
            }
        }

        sc.close();
    }
}
