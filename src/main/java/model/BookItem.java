package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public String getStatus(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select bookStatus from bookItem where barcode = '" + barcode + "'");
            while (resultSet.next()) {
               return resultSet.getString("bookStatus");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    public boolean isReserved(Connection connection){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select resMember from bookItem where barcode = '" + barcode + "'");
            while (resultSet.next()) {
                if (resultSet.getString("resMember") != null){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public java.sql.Date getPublicationDate() {
        return (java.sql.Date) publicationDate;
    }

    public java.sql.Date getDateOfPurchase() {
        return (java.sql.Date) dateOfPurchase;
    }

}
