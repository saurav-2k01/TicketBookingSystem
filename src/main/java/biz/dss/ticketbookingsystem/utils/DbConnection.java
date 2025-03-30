package biz.dss.ticketbookingsystem.utils;

import biz.dss.ticketbookingsystem.enums.Gender;
import biz.dss.ticketbookingsystem.enums.UserType;
import biz.dss.ticketbookingsystem.models.User;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    private static Connection connection;
//    private static Properties properties;


//    public static void main(String[] args) {
//        Properties properties = new Properties();
//        try {
//            FileReader fileReader = new FileReader("src/main/resources/db.properties");
//            properties.load(fileReader);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        try {
//            Class.forName("org.postgresql.Driver");
//            Connection url = DriverManager.getConnection(properties.getProperty("url"), properties);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static Connection getConnection(){
        Properties properties = new Properties();
        try {
            FileReader fileReader = new FileReader("src/main/resources/db.properties");
            properties.load(fileReader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(properties.getProperty("url1"), properties.getProperty("user1"), properties.getProperty("password1"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

//    public static void main(String[] args) {
//        Connection connection1 = DbConnection.getConnection();
//        System.out.println(connection1);
//        try {
//            User user = User.builder().name("Saurav Sharma").userName("saurav2k01").age(23).gender(Gender.MALE)
//                    .email("saurav@gmail.com").password("Saurav@23").userType(UserType.REGISTERED_USER).build();
//            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.addUserQuery);
//            preparedStatement.setInt(1, user.getId());
//            preparedStatement.setString(2, user.getName());
//            preparedStatement.setString(3, user.getUserName());
//            preparedStatement.setInt(4, user.getAge());
//            preparedStatement.setString(5, user.getGender().toString());
//            preparedStatement.setString(6, user.getEmail());
//            preparedStatement.setString(7, user.getPassword());
//            preparedStatement.setString(8, user.getSeatNumber());
//            preparedStatement.setString(9, user.getUserType().toString());
//            preparedStatement.setBoolean(10, false);
//            int i = preparedStatement.executeUpdate();
//            System.out.printf("%d row affected.", i);
//            connection.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
