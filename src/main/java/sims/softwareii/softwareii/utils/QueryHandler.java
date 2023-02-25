package sims.softwareii.softwareii.utils;


import java.sql.Statement;

import java.sql.ResultSet;

import static sims.softwareii.softwareii.utils.DatabaseConnectionManager.connection;

/**
 * Starting structure taken from Carolyn Sher-Decusatis's lectures
 */
public class QueryHandler {
    private static String query;
    private static Statement statement;
    private static ResultSet resultSet;

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
