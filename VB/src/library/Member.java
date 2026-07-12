package library;

/**
 * Represents a library member who can borrow books.
 */
public class Member {

    private final int id;
    private final String name;
    private final String email;

    public Member(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s)", id, name, email);
    }

    public String toDataLine() {
        return id + "|" + name + "|" + email;
    }

    public static Member fromDataLine(String line) {
        String[] parts = line.split("\\|", -1);
        return new Member(Integer.parseInt(parts[0]), parts[1], parts[2]);
    }
}
