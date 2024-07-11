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

    public String getID(){
        return accountId;
    }

    public void register(Connection connection, Date dateOfMembership, int totalBooksCheckedout) {
        String SQL = "INSERT INTO `lms`.`account`" +
                "(`idString`," +
                "`password`," +
                "`status`," +
                "`name`," +
                "`email`," +
                "`phone`," +
                "`streetAddress`," +
                "`city`," +
                "`state`," +
                "`zipcode`," +
                "`country`," +
                "`isLibrarian`," +
                "`dateOfMembership`," +
                "`totalBooksCheckedOut`)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        long id = 0;
        try {
            connection.setAutoCommit(true);
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
                pstmt.setDate(13,null);
                pstmt.setInt(14,0);
            }
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
    public static void main(String [] args){
//        Address address = new Address("street1", "city1", "state1", "zipcode1", "country1");
//        Person person = new Person("haneen", address, "010", "h@gmail.com");
//        Account account = new Account("1", "pass", person, AccountStatus.Active);
//        account.register(connection, new Date(2024, 4, 2), 0);
//        Address address1 = new Address("street2", "city2", "state2", "zipcode2", "country2");
//        Person person1 = new Person("eman", address1, "011", "e@gmail.com");
//        Account account1 = new Account("2", "pass", person1, AccountStatus.Active);
    }
}
