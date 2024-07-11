package model;

import java.sql.*;
import java.util.Date;

public class MYJDBC {

    public static void main(String[] args) {
        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/lms", "root", "1234"
            );

            Address address = new Address("street1", "city1", "state1", "zipcode1", "country1" );
            Person person = new Person("haneen", address, "010", "h@gmail.com");
            Account account = new Account("1", "pass",person, AccountStatus.Active);
            account.register(connection,new Date(2024, 4, 2),0);
            Address address1 = new Address("street2", "city2", "state2", "zipcode2", "country2" );
            Person person1= new Person("eman", address1, "012", "e@gmail.com");
            Account account1 = new Account("2", "pass",person1, AccountStatus.Active);
            account1.register(connection,new Date(2024, 5, 2),1);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from account");
            while(resultSet.next()){
                System.out.println(resultSet.getString("country"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }
}
