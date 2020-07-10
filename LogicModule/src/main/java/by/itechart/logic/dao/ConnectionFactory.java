package by.itechart.logic.dao;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static String url;
    private static String username;
    private static String password;

    private static final Logger logger = Logger.getLogger(ConnectionFactory.class);

    static {
        loadProperties();
    }


    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }


    private static void loadProperties() {

        Properties properties = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/db.properties"))) {
            properties.load(reader);
        } catch (IOException e) {
            logger.error("Database properties haven't been loaded");
            logger.error(e);
        }

        url = properties.getProperty("jdbc.url");
        username = properties.getProperty("jdbc.username");
        password = properties.getProperty("jdbc.password");

    }

}
