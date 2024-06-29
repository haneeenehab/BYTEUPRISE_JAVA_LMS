package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

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

    public void register(Connection connection, Date dateOfMembership, int totalBooksCheckedout) {
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
                "`country`)\n" +
                "`isLibrarian`,\n" +
                "`dateOfMembership`)\n" +
                "`totalBooksCheckedOut`)\n"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

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
            if(dateOfMembership != null) {
                pstmt.setBoolean(12,false);
                pstmt.setDate(13,new java.sql.Date(dateOfMembership.getTime()));
                pstmt.setInt(14,totalBooksCheckedout);
            }
            else{
                pstmt.setBoolean(12,true);
            }
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
