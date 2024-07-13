package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Book {
    private String ISBN;
    private String title;
    private String subject;
    private String publisher;
    private String language;
    private int numberOfPages;
    private String author;


    public Book(String ISBN, String title, String subject, String publisher,
                String language, int numberOfPages, String author) {
        this.ISBN = ISBN;
        this.title = title;
        this.subject = subject;
        this.publisher = publisher;
        this.language = language;
        this.numberOfPages = numberOfPages;
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getLanguage() {
        return language;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public String getAuthor() {
        return author;
    }

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/lms", "root", "1234"
            );

            Book book = new Book("978-0307409323", "A Short History of Nearly Everything", "Science", "Bill Bryson",
                    "English", 544, "Random House");
            BookItem bookItem = new BookItem("1234567890123", new java.sql.Date(2023 - 1900, 1, 1), new java.sql.Date(2023 - 1900, 1, 1), 10.99, BookStatus.Available, new java.sql.Date(2023 - 1900, 1, 1), new java.sql.Date(2023 - 1900, 1, 1));
           // Librarian.addBook(connection, book);
            Librarian.addBookItem(connection,book,bookItem);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
}
