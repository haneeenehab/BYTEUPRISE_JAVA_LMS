package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Account {
    private String accountId;
    private String password;
    private Person person;
    private AccountStatus status;

    public Account(String accountId, String password, Person person, AccountStatus status) {
        this.accountId = accountId;
        this.password = password;
        this.person = person;
        this.status = status;
    }
    public void register(Connection connection) {
        String SQL = "INSERT INTO `lms`.`account`\n" +
                "`idString`,\n" +
                "`password`,\n" +
                "`status`,\n" +
                "`name`,\n" +
                "`email`,\n" +
                "`phone`,\n" +
                "`streetAddress`,\n" +
                "`city`,\n" +
                "`state`,\n" +
                "`zipcode`,\n" +
                "`country`)\n"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?);";

        long id = 0;
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, accountId);
            pstmt.setString(2, password);
            pstmt.setString(3, String.valueOf(status));
            pstmt.setString(4, person.getName());
            pstmt.setString(5, person.getEmail());
            pstmt.setString(6, person.getPhone());
            pstmt.setString(7, person.getAddress().getStreetAddress());
            pstmt.setString(8, person.getAddress().getCity());
            pstmt.setString(9, person.getAddress().getState());
            pstmt.setString(10, person.getAddress().getZipCode());
            pstmt.setString(11, person.getAddress().getCountry());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
