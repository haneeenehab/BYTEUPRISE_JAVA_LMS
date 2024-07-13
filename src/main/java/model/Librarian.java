package model;

import java.sql.*;

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

    public static void addBookItem(Connection connection, Book book, BookItem bookItem) {
        String SQL = "INSERT INTO `lms`.`bookitem`\n" +
                "(`barcode`,\n" +
                "`publicationDate`,\n" +
                "`price`,\n" +
                "`bookStatus`,\n" +
                "`dateOfPurchase`,\n" +
                "`ISBN`)\n" +
                "VALUES (?,?,?,?,?,?)";
        try {
            connection.setAutoCommit(true);
            PreparedStatement pstmt = connection.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, bookItem.getBarcode());
            pstmt.setDate(2, bookItem.getPublicationDate());
            pstmt.setDouble(3, bookItem.getPrice());
            pstmt.setString(4, bookItem.getStatus());
            pstmt.setDate(5, bookItem.getDateOfPurchase());
            pstmt.setString(6, book.getISBN());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void deleteBookItem(Connection connection, BookItem bookItem) {
        String SQL = "DELETE FROM `lms`.`bookItem`\n" +
                "WHERE barcode like \"" + bookItem.getBarcode() + "\";";
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

    public static void returnBook(Connection connection, BookItem bookItem, Member member) throws LibraryException {
        member.memberReturnBook(connection, bookItem);
        String state = bookItem.isReserved(connection) ? "Reserved" : "Available";
        String SQL = "UPDATE `lms`.`bookItem`\n" +
                "SET\n" +
                "bookStatus = \"" + state + "\", issToMember = null where barcode like \"" + bookItem.getBarcode() + "\"; ";

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

    public static void reserveBook(Connection connection, BookItem bookItem, Member member) throws LibraryException {
        String SQLInsert = "INSERT INTO `lms`.`bookreservation`\n" +
                "(`status`,\n" +
                "`bookBarcode`,\n" +
                "`memberId`)" +
                "VALUES (?,?,?)";
        try {
            connection.setAutoCommit(true);
            PreparedStatement pstmt = connection.prepareStatement(SQLInsert,
                    Statement.RETURN_GENERATED_KEYS);
            //todo: date
            pstmt.setString(1, ReservationStatus.Pending.toString());
            pstmt.setString(2, bookItem.getBarcode());
            pstmt.setString(3, member.getID());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void issueBook(Connection connection, BookItem bookItem, Member member, java.sql.Date date) throws LibraryException {
        if (bookItem.isReserved(connection) && !resMemberCheck(connection, bookItem).equals(member.getID())) {
            throw new LibraryException("Book is reserved by another member!");
        }
        if (bookItem.getStatus(connection).equals("Loaned")) {
            throw new LibraryException("Book already lent to another member");
        }
        member.borrowBook(connection, bookItem);
        String SQL = "update bookitem \n" +
                "set bookStatus = \"Loaned\", issToMember = \"" + member.getID() +
                "\" where barcode = \"" + bookItem.getBarcode() + "\";";
        String SQL2 = "update bookreservation \n" +
                "set status = \"Completed\" where bookbarcode = \"" + bookItem.getBarcode() + "\" and memberid like \"" + member.getID() + "\"";
        try {
            connection.setAutoCommit(true);
            PreparedStatement pstmt = connection.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            PreparedStatement pstmt2 = connection.prepareStatement(SQL2,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt2.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        String SQLInsert = "INSERT INTO `lms`.`bookcheckouttransaction`\n" +
                "(`bookBarcode`,\n" +
                "`borrowerId`,\n" +
                "`dueDate`)" +
                "VALUES (?,?,?)";
        try {
            connection.setAutoCommit(true);
            PreparedStatement pstmt = connection.prepareStatement(SQLInsert,
                    Statement.RETURN_GENERATED_KEYS);
            //todo: date
            pstmt.setString(1, bookItem.getBarcode());
            pstmt.setString(2, member.getID());
            pstmt.setDate(3, date);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
}

public static String resMemberCheck(Connection connection, BookItem bookItem) {
    String SQL = "select memberId from bookreservation\n" +
            "where bookbarcode like \"" + bookItem.getBarcode() + "\" ";
    try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()) {
            return resultSet.getString("memberId");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return "";

}


}
