package model;

public class Librarian extends Account{
    public Librarian(String accountId, String password, Person person, AccountStatus status) {
        super(accountId, password, person, status);
    }

    public void addBookItem(BookItem bookItem) {}
    public void blockMember(Member member) {}
    public void unblockMember(Member member) {}
}
