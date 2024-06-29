package model;

import java.sql.*;

public class MYJDBC {

    public static void main(String[] args) {
        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/lms", "root", "1234"
            );
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from test");
            while(resultSet.next()){
                System.out.println(resultSet.getString("idtest"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }
}
