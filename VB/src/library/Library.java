package library;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Library {

    private final List<Book> books = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();
    private final List<Loan> loans = new ArrayList<>();

    private static final String BOOKS_FILE = "books.txt";
    private static final String MEMBERS_FILE = "members.txt";

    // ---------- Book management ----------

    public void addBook(Book book) throws LibraryException {
        if (findBookById(book.getId()).isPresent()) {
            throw new LibraryException("A book with ID " + book.getId() + " already exists.");
        }
        books.add(book);
    }

    public Optional<Book> findBookById(int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst();
    }

    public List<Book> getBooks() {
        return books;
    }

    // ---------- Member management ----------

    public void addMember(Member member) throws LibraryException {
        if (findMemberById(member.getId()).isPresent()) {
            throw new LibraryException("A member with ID " + member.getId() + " already exists.");
        }
        members.add(member);
    }

    public Optional<Member> findMemberById(int id) {
        return members.stream().filter(m -> m.getId() == id).findFirst();
    }

    public List<Member> getMembers() {
        return members;
    }

    // ---------- Loan operations ----------

    public Loan issueBook(int bookId, int memberId) throws LibraryException {
        Book book = findBookById(bookId)
                .orElseThrow(() -> new LibraryException("Book ID " + bookId + " not found."));
        Member member = findMemberById(memberId)
                .orElseThrow(() -> new LibraryException("Member ID " + memberId + " not found."));

        if (!book.isAvailable()) {
            throw new LibraryException("Book \"" + book.getTitle() + "\" is already on loan.");
        }

        book.setAvailable(false);
        Loan loan = new Loan(book, member, LocalDate.now());
        loans.add(loan);
        return loan;
    }

    public Loan returnBook(int bookId) throws LibraryException {
        Book book = findBookById(bookId)
                .orElseThrow(() -> new LibraryException("Book ID " + bookId + " not found."));

        Loan activeLoan = loans.stream()
                .filter(l -> l.getBook().getId() == bookId && !l.isReturned())
                .findFirst()
                .orElseThrow(() -> new LibraryException("No active loan found for book ID " + bookId + "."));

        activeLoan.markReturned(LocalDate.now());
        book.setAvailable(true);
        return activeLoan;
    }

    public List<Loan> getActiveLoans() {
        List<Loan> active = new ArrayList<>();
        for (Loan loan : loans) {
            if (!loan.isReturned()) {
                active.add(loan);
            }
        }
        return active;
    }

    // ---------- Persistence ----------

    public void saveToFile() {
        try (PrintWriter bw = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            for (Book b : books) {
                bw.println(b.toDataLine());
            }
        } catch (IOException e) {
            System.out.println("Warning: could not save books (" + e.getMessage() + ")");
        }

        try (PrintWriter mw = new PrintWriter(new FileWriter(MEMBERS_FILE))) {
            for (Member m : members) {
                mw.println(m.toDataLine());
            }
        } catch (IOException e) {
            System.out.println("Warning: could not save members (" + e.getMessage() + ")");
        }
    }

    public void loadFromFile() {
        loadBooks();
        loadMembers();
    }

    private void loadBooks() {
        File file = new File(BOOKS_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) {
                    books.add(Book.fromDataLine(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: could not load books (" + e.getMessage() + ")");
        }
    }

    private void loadMembers() {
        File file = new File(MEMBERS_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) {
                    members.add(Member.fromDataLine(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: could not load members (" + e.getMessage() + ")");
        }
    }
}
