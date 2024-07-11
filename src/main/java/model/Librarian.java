package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Librarian extends Account {
    public Librarian(String accountId, String password, Person person, AccountStatus status) {
        super(accountId, password, person, status);
    }

    public void addBookItem(BookItem bookItem) {
    }

    public void blockMember(Connection connection, Member member) {
        String SQL = "UPDATE `lms`.`account`\n" +
                "SET\n" +
                "status = \"Blacklisted\" where idString like \"" + member.getID() + "\"";
        try {
            connection.setAutoCommit(true);
            PreparedStatement pstmt = connection.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void unblockMember(Connection connection, Member member) {
        String SQL = "UPDATE `lms`.`account`\n" +
                "SET\n" +
                "status = \"None\" where idString like \"" + member.getID() + "\"";
        try {
            connection.setAutoCommit(true);
            PreparedStatement pstmt = connection.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void addBook(Connection connection, Book book) {
        String SQL = "INSERT INTO `lms`.`book`\n" +
                "(`ISBN`,\n" +
                "`title`,\n" +
                "`subject`,\n" +
                "`publisher`,\n" +
                "`Language`,\n" +
                "`numberOfPages`,\n" +
                "`author`)\n" +
                "VALUES (?,?,?,?,?,?,?)";
        try {
            connection.setAutoCommit(true);
            PreparedStatement pstmt = connection.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, book.getISBN());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getSubject());
            pstmt.setString(4, book.getPublisher());
            pstmt.setString(5, book.getLanguage());
            pstmt.setInt(6, book.getNumberOfPages());
            pstmt.setString(7, book.getAuthor());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void deleteBook(Connection connection, Book book) {
        String SQL = "DELETE FROM `lms`.`book`\n" +
                "WHERE ISBN like \"" + book.getISBN() + "\";";
        try {
            connection.setAutoCommit(true);
            PreparedStatement pstmt = connection.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }
}
