package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Fine {
    private double amount;

    public static double calculateFine(Connection connection, BookItem bookItem) {
        String SQL = "SELECT DATEDIFF(current_date,c.dueDate) AS diff FROM bookcheckouttransaction c\n" +
                "WHERE bookbarcode like \"" + bookItem.getBarcode() + "\" ";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                return Double.parseDouble(resultSet.getString("diff"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.1;
    }
}
