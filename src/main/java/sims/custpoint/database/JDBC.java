package sims.custpoint.database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * A class for establishing, retrieving, and closing the connection to the client_schedule database.
 * Taken from Malcolm Wabara's lectures
 */
public abstract class JDBC {
    /**
     * The type of DB connection protocol to be used in the application.
     */
    private static final String protocol = "jdbc";
    /**
     * The database's vendor/flavor type.
     */
    private static final String vendor = ":mysql:";
    /**
     * The IP location of the database. Localhost will translate to 127.0.0.1.
     */
    private static final String location = "//localhost/";
    /**
     * The name of the database to which a connection will be established.
     */
    private static final String databaseName = "client_schedule";
    /**
     * A concatenated string representing the full path for connection via the DriverManager class.
     */
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    /**
     * The driver's full package name to be used in the startConnection() method.
     */
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    /**
     * A username that the database will accept.
     */
    private static final String userName = "sqlUser";
    /**
     * The password corresponding to the above username.
     */
    private static String password = "Passw0rd!";
    /**
     * A Connection object representing the connection to the database and which will have actions called upon it.
     */
    public static Connection connection;  // Connection Interface

    /**
     * Establish a connection to the database with the class's provided parameters via the SQL DriverManager class.
     */
    public static void startConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * Return the current SQL Connection type connection.
     * @return The class's current static Connection object.
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Closes the current connection if it is still open.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

}
