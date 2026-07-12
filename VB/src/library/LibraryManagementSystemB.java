package library;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LibraryManagementSystemB {

    private static final Scanner sc = new Scanner(System.in);
    private static final Library library = new Library();

    public static void main(String[] args) {
        library.loadFromFile();
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");

            try {
                switch (choice) {
                    case 1 -> addBook();
                    case 2 -> addMember();
                    case 3 -> issueBook();
                    case 4 -> returnBook();
                    case 5 -> displayBooks();
                    case 6 -> displayMembers();
                    case 7 -> displayActiveLoans();
                    case 8 -> {
                        library.saveToFile();
                        System.out.println("Data saved. Exiting system. Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice. Please select 1-8.");
                }
            } catch (LibraryException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM (Version B - AI-Assisted) =====");
        System.out.println("1. Add Book");
        System.out.println("2. Add Member");
        System.out.println("3. Issue Book");
        System.out.println("4. Return Book");
        System.out.println("5. Display Books");
        System.out.println("6. Display Members");
        System.out.println("7. Display Active Loans");
        System.out.println("8. Save & Exit");
    }

    private static void addBook() throws LibraryException {
        int id = readInt("Book ID: ");
        String title = readNonEmptyLine("Title: ");
        String author = readNonEmptyLine("Author: ");
        String isbn = readNonEmptyLine("ISBN: ");

        library.addBook(new Book(id, title, author, isbn));
        System.out.println("Book added successfully.");
    }

    private static void addMember() throws LibraryException {
        int id = readInt("Member ID: ");
        String name = readNonEmptyLine("Name: ");
        String email = readNonEmptyLine("Email: ");

        library.addMember(new Member(id, name, email));
        System.out.println("Member added successfully.");
    }

    private static void issueBook() throws LibraryException {
        int bookId = readInt("Book ID to issue: ");
        int memberId = readInt("Member ID: ");
        Loan loan = library.issueBook(bookId, memberId);
        System.out.println("Book issued. Due date: " + loan.getDueDate());
    }

    private static void returnBook() throws LibraryException {
        int bookId = readInt("Book ID to return: ");
        Loan loan = library.returnBook(bookId);
        double fine = loan.calculateFine();
        System.out.println("Book returned.");
        if (fine > 0) {
            System.out.printf("This book was returned late. Fine due: LKR %.2f%n", fine);
        }
    }

    private static void displayBooks() {
        List<Book> books = library.getBooks();
        System.out.println("\n-- Book Catalogue (" + books.size() + ") --");
        if (books.isEmpty()) {
            System.out.println("No books registered yet.");
        }
        for (Book b : books) {
            System.out.println(b);
        }
    }

    private static void displayMembers() {
        List<Member> members = library.getMembers();
        System.out.println("\n-- Members (" + members.size() + ") --");
        if (members.isEmpty()) {
            System.out.println("No members registered yet.");
        }
        for (Member m : members) {
            System.out.println(m);
        }
    }

    private static void displayActiveLoans() {
        List<Loan> active = library.getActiveLoans();
        System.out.println("\n-- Active Loans (" + active.size() + ") --");
        if (active.isEmpty()) {
            System.out.println("No books are currently on loan.");
        }
        for (Loan l : active) {
            System.out.println(l);
        }
    }

    // ---------- Input helpers (defensive input handling) ----------

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(sc.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static String readNonEmptyLine(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = sc.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty.");
        }
    }
}
