package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Member extends Account{
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
}
