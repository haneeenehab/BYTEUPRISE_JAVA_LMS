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
                "  bookStatus VARCHAR(255),  -- Corrected typo (status instead of satus)\n" +
                "  dateOfPurchase DATE,\n" +
                "  borrowed DATE,\n" +
                "  dueDate DATE,\n" +
                "  ISBN VARCHAR(255),\n" +
                "  FOREIGN KEY (ISBN) REFERENCES Book(ISBN) ON DELETE CASCADE \n" +
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

            init(connection);
            Book book = new Book("978-0307409323", "A Short History of Nearly Everything", "Science", "Bill Bryson",
                    "English", 544, "Random House");
            BookItem bookItem = new BookItem("1234567890123", new java.sql.Date(2023,1,1), new java.sql.Date(2023,1,1), 10.99, BookStatus.Available, new java.sql.Date(2023,1,1),new java.sql.Date(2023,1,1));
              Librarian.addBook(connection,book);
              Librarian.addBookItem(connection,book,bookItem);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from account");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("country"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
