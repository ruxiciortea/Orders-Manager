package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class used to establish the initial connection with the database
 */
public class ConnectionFactory {

    private static final Logger logger = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/schooldb";
    private static final String user = "root";
    private static final String password = "pass";

    private static ConnectionFactory sharedInstance = new ConnectionFactory();

    private ConnectionFactory() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Public Methods
    public static Connection getConnection() {
        return sharedInstance.createConnection();
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.WARNING, "An error occurred while trying to close the connection");
            }
        }
    }

    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.log(Level.WARNING, "An error occurred while trying to close the statement");
            }
        }
    }

    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.log(Level.WARNING, "An error occurred while trying to close the ResultSet");
            }
        }
    }

    // Private Methods
    private Connection createConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbUrl, user, password);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "An error occurred while trying to connect to the database");
            e.printStackTrace();
        }
        return connection;
    }

}
