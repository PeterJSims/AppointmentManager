package sims.softwareii.softwareii.database;


import java.sql.Connection;
import java.sql.Statement;

import java.sql.ResultSet;


/**
 * Starting structure taken from Carolyn Sher-Decusatis's lectures
 */
public class QueryHandler {
    private static String query;
    private static Statement statement;
    private static ResultSet resultSet;
    private static Connection connection = JDBC.connection;

    public static void makeQuery(String q) {
        query = q;
        try {
            statement = connection.createStatement();
            // determine query execution
            if (query.toLowerCase().startsWith("select"))
                resultSet = statement.executeQuery(q);
            if (query.toLowerCase().startsWith("delete") || query.toLowerCase().startsWith("insert") || query.toLowerCase().startsWith("update"))
                statement.executeUpdate(q);

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public static ResultSet getResult() {
        return resultSet;
    }
}
