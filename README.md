# Library Borrowing System

A simple console-based library management system written in Java. The system allows a **Librarian** to manage books and members, while **Members** can borrow, return, and search for books.

---

## Features

### Book Management (Librarian)
- Add a new book to the catalog
- Remove a book from the catalog
- Update a book's title or author
- View all books with their availability status

### Member Management (Librarian)
- Register a new member
- Remove a member
- Update a member's name
- View all registered members

### Borrow & Return (Member)
- Borrow a book (up to 5 books at a time)
- Return a borrowed book
- Search for a book by title keyword

### Records
- View all borrow and return transactions

---

## Project Structure

```
LibraryBorrowingSystem/
├── src/
│   └── LibraryBorrowingSystem/       # Package folder (matches package declaration)
│       ├── Books.java                # Represents a book; holds initial book data
│       ├── Member.java               # Represents a member; holds initial member data
│       ├── BorrowRecord.java         # Records a single borrow/return transaction
│       ├── Librarian.java            # Manages catalog, members, and borrow records
│       └── main.java                 # Entry point; runs the menu-driven interface
├── out/                              # Compiled .class files (gitignored)
├── .gitignore
└── README.md
```

---

## Class Overview

### `Books`
Stores the details of a single book.

| Attribute   | Type    | Access  | Description                        |
|-------------|---------|---------|------------------------------------|
| bookId      | String  | private | Unique book identifier (e.g. B001) |
| title       | String  | private | Title of the book                  |
| author      | String  | private | Author of the book                 |
| available   | boolean | private | True if the book is on the shelf   |

Key methods: `getInitialBooks()`, `borrowBook()`, `toString()`

---

### `Member`
Represents a library member who can borrow and return books.

| Attribute     | Type     | Access  | Description                          |
|---------------|----------|---------|--------------------------------------|
| memberId      | String   | private | Unique member identifier (e.g. M001) |
| name          | String   | private | Full name of the member              |
| borrowedBooks | Books[]  | private | Array of currently borrowed books    |
| borrowCount   | int      | private | Number of books currently borrowed   |

Key methods: `getInitialMembers()`, `borrowBook()`, `returnBook()`, `searchBook()`

---

### `BorrowRecord`
Tracks a single borrow or return transaction.

| Attribute   | Type    | Access  | Description                              |
|-------------|---------|---------|------------------------------------------|
| recordId    | String  | private | Unique record ID (e.g. REC001)           |
| member      | Member  | private | The member who borrowed the book         |
| book        | Books   | private | The book that was borrowed               |
| borrowDate  | String  | private | Date the book was borrowed               |
| returnDate  | String  | private | Date the book was returned ("-" if not)  |
| returned    | boolean | private | True if the book has been returned       |

---

### `Librarian`
Manages the library catalog, members, and all borrow records.

| Attribute     | Type            | Access  | Description                      |
|---------------|-----------------|---------|----------------------------------|
| librarianId   | String          | private | Unique librarian ID (e.g. L001)  |
| name          | String          | private | Full name of the librarian       |
| catalog       | Books[]         | private | Array of all books in the system |
| members       | Member[]        | private | Array of all registered members  |
| borrowRecords | BorrowRecord[]  | private | Array of all borrow transactions |

Key methods: `addBook()`, `removeBook()`, `updateBook()`, `registerMember()`, `removeMember()`, `updateMember()`, `recordBorrow()`, `recordReturn()`

---

## Class Associations

```
Librarian ──manages──> Books[]
Librarian ──manages──> Member[]
Librarian ──manages──> BorrowRecord[]

BorrowRecord ──links──> Member
BorrowRecord ──links──> Books
```

---

## Initial Data

The system starts with pre-loaded data defined inside each class:

**Books** (defined in `Books.getInitialBooks()`):
| ID   | Title                    | Author                |
|------|--------------------------|-----------------------|
| B001 | The Great Gatsby         | F. Scott Fitzgerald   |
| B002 | To Kill a Mockingbird    | Harper Lee            |
| B003 | 1984                     | George Orwell         |
| B004 | Brave New World          | Aldous Huxley         |
| B005 | The Catcher in the Rye   | J.D. Salinger         |

**Members** (defined in `Member.getInitialMembers()`):
| ID   | Name    |
|------|---------|
| M001 | Alice   |
| M002 | Bob     |
| M003 | Charlie |

---

## How to Run

### Requirements
- Java JDK 8 or higher

### Compile
```bash
cd /path/to/LibraryBorrowingSystem
javac -d out src/LibraryBorrowingSystem/*.java
```

### Run
```bash
java -cp out LibraryBorrowingSystem.main
```

---

## Menu Options

```
============================================
          LIBRARY BORROWING SYSTEM
============================================
 --- Book Management ---
  1. Add Book
  2. Remove Book
  3. Update Book
  4. View All Books
 --- Member Management ---
  5. Register Member
  6. Remove Member
  7. Update Member Name
  8. View All Members
 --- Borrow & Return ---
  9. Borrow Book
 10. Return Book
 11. Search Book by Title
 12. View All Borrow Records
--------------------------------------------
  0. Exit
============================================
```

---

## Package

All classes are organized under the `LibraryBorrowingSystem` package.

```java
package LibraryBorrowingSystem;
```
