package model;

import java.sql.Connection;
import java.sql.SQLException;
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
}
