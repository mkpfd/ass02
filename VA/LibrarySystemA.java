import java.util.Scanner;

class Book {

    int id;
    String title;
    String author;
    boolean available;

    Book(int a, String b, String c) {
        id = a;
        title = b;
        author = c;
        available = true;
    }
}

class Member {

    int id;
    String name;

    Member(int a, String b) {
        id = a;
        name = b;
    }
}

public class LibrarySystemA {

    static Scanner sc = new Scanner(System.in);

    static Book[] books = new Book[50];
    static Member[] members = new Member[50];

    static int[] issueBook = new int[50];
    static int[] issueMember = new int[50];

    static int bookCount = 0;
    static int memberCount = 0;
    static int issueCount = 0;

    public static void main(String[] args) {

        int choice = 0;

        while (choice != 7) {

            System.out.println();
            System.out.println("===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Display Books");
            System.out.println("6. Display Members");
            System.out.println("7. Exit");
            System.out.print("Enter Choice : ");

            choice = sc.nextInt();

            if (choice == 1) {

                sc.nextLine();

                System.out.print("Book ID : ");
                int x = sc.nextInt();
                sc.nextLine();

                System.out.print("Book Title : ");
                String y = sc.nextLine();

                System.out.print("Author : ");
                String z = sc.nextLine();

                books[bookCount] = new Book(x, y, z);
                bookCount = bookCount + 1;

                System.out.println("Book Added");

            } else if (choice == 2) {

                sc.nextLine();

                System.out.print("Member ID : ");
                int x = sc.nextInt();
                sc.nextLine();

                System.out.print("Member Name : ");
                String y = sc.nextLine();

                members[memberCount] = new Member(x, y);
                memberCount = memberCount + 1;

                System.out.println("Member Added");

            } else if (choice == 3) {

                System.out.print("Book ID : ");
                int b = sc.nextInt();

                System.out.print("Member ID : ");
                int m = sc.nextInt();

                boolean found = false;

                for (int i = 0; i < bookCount; i = i + 1) {

                    if (books[i].id == b) {

                        found = true;

                        if (books[i].available == true) {

                            books[i].available = false;

                            issueBook[issueCount] = b;
                            issueMember[issueCount] = m;
                            issueCount = issueCount + 1;

                            System.out.println("Book Issued");

                        } else {

                            System.out.println("Book Already Issued");
                        }
                    }
                }

                if (found == false) {
                    System.out.println("Book Not Found");
                }

            } else if (choice == 4) {

                System.out.print("Book ID : ");
                int b = sc.nextInt();

                boolean found = false;

                for (int i = 0; i < bookCount; i = i + 1) {

                    if (books[i].id == b) {

                        books[i].available = true;
                        found = true;
                    }
                }

                if (found == true) {
                    System.out.println("Book Returned");
                } else {
                    System.out.println("Book Not Found");
                }

            } else if (choice == 5) {

                System.out.println();
                System.out.println("----- Book List -----");

                if (bookCount == 0) {

                    System.out.println("No Books");

                } else {

                    for (int i = 0; i < bookCount; i = i + 1) {

                        System.out.println("Book ID : " + books[i].id);
                        System.out.println("Title : " + books[i].title);
                        System.out.println("Author : " + books[i].author);
                        System.out.println("Available : " + books[i].available);
                        System.out.println();
                    }
                }

            } else if (choice == 6) {

                System.out.println();
                System.out.println("----- Member List -----");

                if (memberCount == 0) {

                    System.out.println("No Members");

                } else {

                    for (int i = 0; i < memberCount; i = i + 1) {

                        System.out.println("Member ID : " + members[i].id);
                        System.out.println("Member Name : " + members[i].name);
                        System.out.println();
                    }
                }

            } else if (choice == 7) {

                System.out.println("Program End");

            } else {

                System.out.println("Wrong Choice");
            }
        }

        sc.close();
    }
}