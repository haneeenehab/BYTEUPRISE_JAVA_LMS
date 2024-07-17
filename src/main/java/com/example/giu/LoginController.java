package com.example.giu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    public static boolean verifyLogin(Connection connection, String id, String password) {
        String SQL = "select status from account where idString like \"" + id + "\" and password like \"" + password + "\"";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                if (resultSet.getString("status").equals("Active"))
                    return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
