package library;


public class Book {

    private final int id;
    private final String title;
    private final String author;
    private final String isbn;
    private boolean available;

    public Book(int id, String title, String author, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = true;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return String.format("[%d] \"%s\" by %s (ISBN: %s) - %s",
                id, title, author, isbn, available ? "Available" : "On Loan");
    }

    /** Serializes this book to a single pipe-delimited line for file persistence. */
    public String toDataLine() {
        return id + "|" + title + "|" + author + "|" + isbn + "|" + available;
    }

    public static Book fromDataLine(String line) {
        String[] parts = line.split("\\|", -1);
        Book book = new Book(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
        book.setAvailable(Boolean.parseBoolean(parts[4]));
        return book;
    }
}
