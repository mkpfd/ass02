package library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {

    private static final int LOAN_PERIOD_DAYS = 14;
    private static final double FINE_PER_DAY = 20.0; 

    private final Book book;
    private final Member member;
    private final LocalDate issueDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;

    public Loan(Book book, Member member, LocalDate issueDate) {
        this.book = book;
        this.member = member;
        this.issueDate = issueDate;
        this.dueDate = issueDate.plusDays(LOAN_PERIOD_DAYS);
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public void markReturned(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /** Calculates the fine owed based on how late the book was (or currently is). */
    public double calculateFine() {
        LocalDate compareDate = isReturned() ? returnDate : LocalDate.now();
        long daysLate = ChronoUnit.DAYS.between(dueDate, compareDate);
        return daysLate > 0 ? daysLate * FINE_PER_DAY : 0.0;
    }

    @Override
    public String toString() {
        String status = isReturned() ? "Returned on " + returnDate : "Due on " + dueDate;
        double fine = calculateFine();
        String fineText = fine > 0 ? String.format(" | Fine: LKR %.2f", fine) : "";
        return String.format("Book #%d -> Member #%d | Issued: %s | %s%s",
                book.getId(), member.getId(), issueDate, status, fineText);
    }
}
