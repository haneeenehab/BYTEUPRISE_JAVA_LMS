package model;

import java.sql.*;
import java.util.Date;

public class Member extends Account {
    private Date dateOfMembership;
    private int totalBooksCheckedout;

    public Member(String accountId, String password, Person person, AccountStatus status, Date dateOfMembership, int totalBooksCheckedout) {
        super(accountId, password, person, status);
        this.dateOfMembership = dateOfMembership;
        this.totalBooksCheckedout = totalBooksCheckedout;
    }

    public void register(Connection connection) {
        super.register(connection, dateOfMembership, totalBooksCheckedout);
    }

    public int getTotalBooksCheckedout(Connection connection) {
        String SQL = "select totalBooksCheckedOut from account where idString like \"" + getID() + "\"";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                return Integer.parseInt(resultSet.getString("totalBooksCheckedOut"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void cancelMembership(Connection connection) {
        String SQL = "UPDATE `lms`.`account`\n" +
                "SET\n" +
                "status = \"Canceled\" where idString like \"" + getID() + "\"";
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

    public void setTotalBooksCheckedout(Connection connection, int num) {
        String SQL = "UPDATE `lms`.`account`\n" +
                "SET\n" +
                "totalBooksCheckedOut = " + num + " where idString like \"" + getID() + "\"";

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

    public void borrowBook(Connection connection, BookItem book) throws LibraryException {
        if (getTotalBooksCheckedout(connection) == Library.getMax_Books_Issued()) {
            throw new LibraryException("You borrowed maximum books allowed");
        } else {
            setTotalBooksCheckedout(connection, getTotalBooksCheckedout(connection) + 1);
        }
    }

    public void memberReturnBook(Connection connection, BookItem book) throws LibraryException {
        setTotalBooksCheckedout(connection, getTotalBooksCheckedout(connection) - 1);
        if (!book.isWithinDueDate(connection)) {
            String SQLInsert = "INSERT INTO `lms`.`finetransaction`\n" +
                    "`amount`,\n" +
                    "`memberId`,\n" +
                    "`bookBarcode`)" +
                    "VALUES (?,?,?)";
            try {
                connection.setAutoCommit(true);
                PreparedStatement pstmt = connection.prepareStatement(SQLInsert,
                        Statement.RETURN_GENERATED_KEYS);
                double fine = Fine.calculateFine(connection, book);
                pstmt.setDouble(1, fine);
                pstmt.setString(2, getID());
                pstmt.setString(3, book.getBarcode());
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/lms", "root", "1234"
            );

            Address address1 = new Address("street2", "city2", "state2", "zipcode2", "country2");
            Person person1 = new Person("eman", address1, "011", "e@gmail.com");
            Member account1 = new Member("2", "pass", person1, AccountStatus.Active, new Date(2024 - 1900, 4, 2), 0);
            Address address2 = new Address("street2", "city2", "state2", "zipcode2", "country2");
            Person person2 = new Person("ahmed", address2, "012", "a@gmail.com");
            Member account2 = new Member("3", "pass1", person2, AccountStatus.Active, new Date(2024 - 1900, 6, 7), 0);
            account2.register(connection, new Date(2024 - 1900, 8, 2), 0);
            account1.register(connection, new Date(2024, 4, 2), 0);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

}
