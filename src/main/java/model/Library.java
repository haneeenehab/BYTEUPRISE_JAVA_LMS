package model;

public class Library {
    private String name;
    private Address address;
    private static final int max_Books_Issued = 4;
    private static final int max_lending_days = 10;

    public String getName() {
        return name;
    }

    public static int getMax_Books_Issued() {
        return max_Books_Issued;
    }

    public Address getAddress() {
        return address;
    }

    public static int getMax_lending_days() {
        return max_lending_days;
    }
}
