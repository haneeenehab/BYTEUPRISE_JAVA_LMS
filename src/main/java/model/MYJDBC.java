package model;

import java.sql.*;
import java.util.Date;

public class MYJDBC {

    public static void init(Connection connection) {
        String SQLAccount =
                "CREATE TABLE account (" +
                        "idString varchar(255) not null PRIMARY KEY," +
                        "password VARCHAR(255) NOT NULL," +
                        "status VARCHAR(255)," +
                        "name VARCHAR(255)," +
                        "email VARCHAR(255) NOT NULL," +
                        "phone VARCHAR(255)," +
                        "streetAddress varchar(255)," +
                        "city VARCHAR(255)," +
                        "state varchar(255)," +
                        "zipcode VARCHAR(255)," +
                        "country VARCHAR(255)," +
                        "isLibrarian boolean," +
                        "dateOfMembership date," +
                        "totalBooksCheckedOut INT DEFAULT 0" +
                        ");";
        String SQLBook = "CREATE TABLE Book (\n" +
                "  ISBN VARCHAR(255) PRIMARY KEY,\n" +
                "  title VARCHAR(255) NOT NULL,\n" +
                "  subject VARCHAR(255),\n" +
                "  publisher VARCHAR(255),\n" +
                "  Language VARCHAR(255),\n" +
                "  numberOfPages INT,\n" +
                "  author VARCHAR(255)\n" +
                ");";
        String SQLBookItem = "CREATE TABLE BookItem (\n" +
                "  barcode VARCHAR(255) PRIMARY KEY,\n" +
                "  publicationDate DATE,\n" +
                "  price DOUBLE,\n" +
                "  bookStatus VARCHAR(255),\n" +
                "  dateOfPurchase DATE,\n" +
                "  borrowed DATE,\n" +
                "  dueDate DATE,\n" +
                "  ISBN VARCHAR(255),\n" +
                "  issToMember VARCHAR(255),\n" +
                "  FOREIGN KEY (ISBN) REFERENCES Book(ISBN) ON DELETE CASCADE \n" +
                ");";
        String SQLBookReservations = "CREATE TABLE bookReservation (\n" +
                "  creationDate DATE NOT NULL,\n" +
                "  status VARCHAR(255) NOT NULL,\n" +
                "  bookBarcode VARCHAR(255) NOT NULL,\n" +
                "  memberId INT NOT NULL,\n" +
                "  PRIMARY KEY (bookBarcode, memberId) \n" +
                ");";
        try {
            connection.setAutoCommit(true);
            PreparedStatement pstmt = connection.prepareStatement(SQLAccount,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            PreparedStatement pstmt1 = connection.prepareStatement(SQLBook,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt1.executeUpdate();
            PreparedStatement pstmt2 = connection.prepareStatement(SQLBookItem);
            pstmt2.executeUpdate();
            PreparedStatement pstmt3 = connection.prepareStatement(SQLBookReservations);
            pstmt3.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/lms", "root", "1234"
            );

            //init(connection);
            Book book = new Book("978-0307409323", "A Short History of Nearly Everything", "Science", "Bill Bryson",
                    "English", 544, "Random House");
            BookItem bookItem = new BookItem("1234567890123", new java.sql.Date(2023-1900, 1, 1), new java.sql.Date(2023-1900, 1, 1), 10.99, BookStatus.Available, new java.sql.Date(2023-1900, 1, 1), new java.sql.Date(2023-1900, 1, 1));
//            Librarian.addBook(connection, book);
//            Librarian.addBookItem(connection,book,bookItem);
            Address address1 = new Address("street2", "city2", "state2", "zipcode2", "country2");
            Person person1 = new Person("eman", address1, "011", "e@gmail.com");
            Member account1 = new Member("2", "pass", person1, AccountStatus.Active, new Date(2024-1900, 4, 2), 0);
            //account1.register(connection, new Date(2024, 4, 2), 0);
           // Librarian.returnBook(connection,bookItem);
            //Librarian.reserveBook(connection,bookItem,account1);
            //Librarian.deleteBookItem(connection,bookItem);
            Librarian.issueBook(connection,bookItem,account1);
            System.out.println(bookItem.isReserved(connection));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (LibraryException e) {
            throw new RuntimeException(e);
        }
    }
}
