package biz.dss.ticketbookingsystem.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    private Connection connection;
    private Properties properties;


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

    public Connection getConnection(){
//        Properties properties = new Properties();
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
            connection = DriverManager.getConnection(properties.getProperty("url"), properties);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }
}
