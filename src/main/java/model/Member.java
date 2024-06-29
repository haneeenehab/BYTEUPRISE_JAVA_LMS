package model;

import java.util.Date;

public class Member extends Account{
    private Date dateOfMembership;
    private int totalBooksCheckedout;

    public Member(String accountId, String password, Person person, AccountStatus status, Date dateOfMembership, int totalBooksCheckedout) {
        super(accountId, password, person, status);
        this.dateOfMembership = dateOfMembership;
        this.totalBooksCheckedout = totalBooksCheckedout;
    }
}
