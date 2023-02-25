package sims.softwareii.softwareii;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;

class DatabaseConnectionManager {
    private final String url;
    private final Properties properties;

    public DatabaseConnectionManager(String host, String databaseName, String userName, String password){
        // check how this works for MySQL
        this.url = "jdbc:mysql//" + host + "/" + databaseName;
        this.properties = new Properties();
        this.properties.setProperty("user", userName);
        this.properties.setProperty("password", password);
    }

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(this.url, this.properties);
    }

}