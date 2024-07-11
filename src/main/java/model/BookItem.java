package model;

import java.util.Date;

public class BookItem {
    private String barcode;
    private Date borrowed;
    private Date dueDate;
    private double price;
    private BookStatus status;
    private Date publicationDate;
    private Date dateOfPurchase;

    public BookItem(String barcode, Date borrowed, Date dueDate, double price, BookStatus status,
                    Date publicationDate, Date dateOfPurchase) {
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
