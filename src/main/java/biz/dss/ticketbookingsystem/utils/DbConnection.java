package biz.dss.ticketbookingsystem.utils;


import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class DbConnection {
    private static Connection connection;
    private static final Properties PROPERTIES;

    private DbConnection(){}

    static {
        PROPERTIES = new Properties();
        try (FileReader fileReader = new FileReader("src/main/resources/db.properties")) {
            PROPERTIES.load(fileReader);
        } catch (IOException e) {
            String message = "Error occurred while loading properties file.";
            log.error(message, e);
            System.err.println(message);
        }
    }

    public static Connection getConnection() {
        if (Objects.isNull(connection)) {
            synchronized (DbConnection.class) {
                if (Objects.isNull(connection)) {
                    try {
                        connection = DriverManager.getConnection(PROPERTIES.getProperty("url1"), PROPERTIES.getProperty("user1"), PROPERTIES.getProperty("password1"));
                        return connection;
                    } catch (SQLException e) {
                        String message = "Error occurred while connecting to database.";
                        log.error(message, e);
                        System.err.println(message);
                    }
                }
            }
        }
        return connection;
    }
}
