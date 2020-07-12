package by.itechart.logic.dao;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static String url;
    private static String username;
    private static String password;
    private static String driver;

    private static final String PROPERTIES_PATH = "/db.properties";

    private static final Logger logger = Logger.getLogger(ConnectionFactory.class);

    static {
        loadProperties();
    }


    public static Connection createConnection() throws SQLException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            logger.error("JDBC driver not found");
            logger.error(e);
        }
        return DriverManager.getConnection(url, username, password);
    }


    private static void loadProperties() {

        Properties properties = new Properties();
        try  {
            InputStream in = ConnectionFactory.class.getResourceAsStream(PROPERTIES_PATH);
            properties.load(in);
        } catch (IOException e) {
            logger.error("Database properties haven't been loaded");
            logger.error(e);
        }

        url = properties.getProperty("jdbc.url");
        username = properties.getProperty("jdbc.username");
        password = properties.getProperty("jdbc.password");
        driver = properties.getProperty("jdbc.driver");

    }

}
