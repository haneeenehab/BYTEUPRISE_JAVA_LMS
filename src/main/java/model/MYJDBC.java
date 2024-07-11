package model;

import java.sql.*;
import java.util.Date;

public class MYJDBC {

    public static void init(Connection connection) {
        String SQLAccount =
                "CREATE TABLE account (" +
                        "idString varchar(255) not null PRIMARY KEY," +
                        "password VARCHAR(255) NOT NULL," +
                        "status VARCHAR(255)," +
                        "name VARCHAR(255)," +
                        "email VARCHAR(255) NOT NULL," +
                        "phone VARCHAR(255)," +
                        "streetAddress varchar(255)," +
                        "city VARCHAR(255)," +
                        "state varchar(255)," +
                        "zipcode VARCHAR(255)," +
                        "country VARCHAR(255)," +
                        "isLibrarian boolean," +
                        "dateOfMembership date," +
                        "totalBooksCheckedOut INT DEFAULT 0" +
                        ");";
        String SQLBook = "CREATE TABLE Book (\n" +
                "  ISBN VARCHAR(255) PRIMARY KEY,\n" +
                "  title VARCHAR(255) NOT NULL,\n" +
                "  subject VARCHAR(255),\n" +
                "  publisher VARCHAR(255),\n" +
                "  Language VARCHAR(255),\n" +
                "  numberOfPages INT,\n" +
                "  author VARCHAR(255)\n" +
                ");";
        try {
            connection.setAutoCommit(true);
            PreparedStatement pstmt = connection.prepareStatement(SQLAccount,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            PreparedStatement pstmt1 = connection.prepareStatement(SQLBook,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt1.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/lms", "root", "1234"
            );
            init(connection);
            Address address = new Address("street1", "city1", "state1", "zipcode1", "country1");
            Person person = new Person("haneen", address, "010", "h@gmail.com");
            Librarian account = new Librarian("1", "pass", person, AccountStatus.Active);
//            account.register(connection, null, 2);
//
            Address address1 = new Address("street2", "city2", "state2", "zipcode2", "country2");
            Person person1 = new Person("eman", address1, "011", "e@gmail.com");
            Member account1 = new Member("2", "pass", person1, AccountStatus.Active, new Date(2024, 4, 2), 0);
            //  account1.register(connection, new Date(2024, 4, 2), 0);

            // account.unblockMember(connection,account1 );
            // account1.cancelMembership(connection);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from account");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("country"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
