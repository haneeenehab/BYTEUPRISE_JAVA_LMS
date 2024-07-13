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

    public String getStatus() {
        return status.toString();
    }

    public boolean isReserved(Connection connection) {
        boolean isCompleted = false;
        String SQL = "select status from bookreservation where bookBarcode like \"" + barcode + "\"";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                if (!resultSet.getString("status").equals("Completed")) {
                    isCompleted = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isCompleted;
    }


    public java.sql.Date getPublicationDate() {
        return (java.sql.Date) publicationDate;
    }

    public java.sql.Date getDateOfPurchase() {
        return (java.sql.Date) dateOfPurchase;
    }

    public boolean isWithinDueDate(Connection connection) {
        String SQL = "SELECT \n" +
                "  CASE\n" +
                "    WHEN current_date <= dueDate THEN 'Returned on Time'\n" +
                "    ELSE 'Returned Late'\n" +
                "  END AS return_status\n" +
                "  from lms.bookItem\n" +
                "  where barcode like \"" + getBarcode() + "\";\n";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                if (resultSet.getString("return_status").equals("Returned on Time")) {
                    return true;
                }
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //todo: editing due date

}
