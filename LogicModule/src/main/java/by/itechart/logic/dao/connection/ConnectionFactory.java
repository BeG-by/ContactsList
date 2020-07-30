package by.itechart.logic.dao.connection;

import by.itechart.logic.exception.LoadPropertiesException;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static String url;
    private static String username;
    private static String password;
    private static String driver;

    private static String minConnection;
    private static String maxConnection;
    private static String maxStatement;

    private static final String PROPERTIES_PATH = "/db.properties";

    private static final Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

    private static BasicDataSource dataSource = new BasicDataSource();

    static {
        loadProperties();

        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        dataSource.setMinIdle(Integer.parseInt(minConnection));
        dataSource.setMaxIdle(Integer.parseInt(maxConnection));
        dataSource.setMaxOpenPreparedStatements(Integer.parseInt(maxStatement));

    }


    public static Connection createConnection() throws SQLException, LoadPropertiesException {
        if (url == null || username == null || password == null || driver == null) {
            throw new LoadPropertiesException();
        }
        return dataSource.getConnection();
    }


    private static void loadProperties() {

        Properties properties = new Properties();
        try {
            InputStream in = ConnectionFactory.class.getResourceAsStream(PROPERTIES_PATH);
            properties.load(in);

            url = properties.getProperty("jdbc.url");
            username = properties.getProperty("jdbc.username");
            password = properties.getProperty("jdbc.password");
            driver = properties.getProperty("jdbc.driver");
            minConnection = properties.getProperty("pool.min.connection");
            maxConnection = properties.getProperty("pool.max.connection");
            maxStatement = properties.getProperty("pool.max.prepare.statement");

        } catch (IOException e) {
            logger.error("Database properties haven't been loaded", e);
        }

    }

}
