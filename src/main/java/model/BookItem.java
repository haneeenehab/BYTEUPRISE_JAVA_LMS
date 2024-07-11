package model;

import java.util.Date;

public class BookItem {
    private String barcode;
    private java.sql.Date borrowed;
    private java.sql.Date dueDate;
    private double price;
    private BookStatus status;
    private java.sql.Date publicationDate;
    private java.sql.Date dateOfPurchase;

    public BookItem(String barcode, java.sql.Date borrowed, java.sql.Date dueDate, double price, BookStatus status,
                    java.sql.Date publicationDate, java.sql.Date dateOfPurchase) {
        this.barcode = barcode;
        this.borrowed = borrowed;
        this.dueDate = dueDate;
        this.price = price;
        this.status = status;
        this.publicationDate = publicationDate;
        this.dateOfPurchase = dateOfPurchase;
    }

    public String getBarcode() {
        return barcode;
    }

    public java.sql.Date getBorrowed() {
        return (java.sql.Date) borrowed;
    }

    public java.sql.Date getDueDate() {
        return (java.sql.Date) dueDate;
    }

    public double getPrice() {
        return price;
    }

    public BookStatus getStatus() {
        return status;
    }

    public java.sql.Date getPublicationDate() {
        return (java.sql.Date) publicationDate;
    }

    public java.sql.Date getDateOfPurchase() {
        return (java.sql.Date) dateOfPurchase;
    }
}
