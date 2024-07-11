package model;

public class Library {
    private String name;
    private Address address;
    private final int max_Books_Issued = 4;
    private final int max_lending_days = 10;

    public String getName() {
        return name;
    }

    public int getMax_Books_Issued() {
        return max_Books_Issued;
    }

    public Address getAddress() {
        return address;
    }

    public int getMax_lending_days() {
        return max_lending_days;
    }
}
