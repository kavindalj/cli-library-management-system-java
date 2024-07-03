import java.util.List;

public class Book {
    int bookId;
    String title;
    String author;
    String publisher;
    int yearPublished;
    private Database database;

    public Book() {
        this.database = new Database(); // Create a new instance of Database
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void addBook(String title,String author,String publisher,int yearPublished){

        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.yearPublished = yearPublished;

        boolean inserted = database.insertBook(title, author, publisher, yearPublished);
        if (inserted) {
            System.out.println("Book inserted successfully into database.");
        } else {
            System.out.println("Failed to insert book into database.");
        }
    }

    public void deleteBook(int bookId) {
        boolean deleted = database.deleteBook(bookId);
        if (deleted) {
            System.out.println("Book deleted successfully from database.");
        } else {
            System.out.println("Failed to delete book from database.");
        }
    }

    public void updateBook(int bookId, String title, String author, String publisher, int yearPublished) {
        boolean updated = database.updateBook(bookId, title, author, publisher, yearPublished);
        if (updated) {
            System.out.println("Book updated successfully in the database.");
        } else {
            System.out.println("Failed to update book in the database.");
        }
    }

    public int searchBooks(String title, String author, Integer yearPublished) {
        List<Book> books = database.searchBooks(title, author, yearPublished);
        for (Book book : books) {
            System.out.println("Book ID: " + book.getBookId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Publisher: " + book.getPublisher());
            System.out.println("Year Published: " + book.getYearPublished());
            System.out.println();
            bookId = book.getBookId();
        }
        return bookId;
    }
}
