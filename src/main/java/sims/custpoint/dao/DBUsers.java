package sims.custpoint.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sims.custpoint.database.JDBC;
import sims.custpoint.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class holding static methods for communication with the User table in the database linked in the JDBC class.
 * Contains all CRUD actions as well as a user/password validation method.
 *
 * @author Peter Sims
 */
public class DBUsers {



    /**
     * Before validating the user's password, check if the database contains an entry for the username itself.
     *
     * @param userName Username provided by the user's input into the Login screen.
     * @return A boolean representing a successful fetch of a matching username in the database.
     */
    public static boolean findUser(String userName) {
        try {
            String sql = "SELECT * FROM users WHERE User_Name = ?";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, userName);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * @param userName Username provided by the user's input into the Login screen.
     * @param password Password provided by the user's input into the Login screen.
     * @return A boolean representing a successful fetch of a matching user from the database.
     */
    public static boolean validateUser(String userName, String password) {
        try {
            String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ? ";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Inserts a new user into the User table.
     *
     * @param userName The chosen username for the new user.
     * @param password The chosen password for the new user.
     * @return A boolean representing a successful insertion into the database.
     */
    public static boolean createUser(String userName, String password) {
        try {
            String sql = "INSERT INTO users (User_Name, Password) VALUES (?, ?)";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns an ObservableList-type list of Users queried from the database. Holds all User IDs, usernames, and passwords.
     *
     * @return An ObservableList-type list of User objects.
     */
    public static ObservableList<User> getUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM users";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                userList.add(new User(userID, userName, password));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return userList;
    }

    /**
     * Allows a user to change a given user's username and/or password.
     *
     * @param userID       The unique user ID tied to the particular user seeking to update their information.
     * @param userName     The desired username for the user ID to be tied to, whether changed or remaining the same.
     * @param userPassword The desired password for the user ID to be tied to, whether changed or remaining the same.
     * @return A boolean representing a successful insertion into the database.
     */
    public static boolean updateUser(int userID, String userName, String userPassword) {
        try {
            String sql = "UPDATE users SET User_Name = ?, Password = ? WHERE User_ID = ? ";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, userName);
            ps.setString(2, userPassword);
            ps.setInt(3, userID);

            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Allows a user to remove a given user from the User table based on the entry's unique ID.
     *
     * @param userID The unique user ID of the User table entry to be removed.
     * @return A boolean representing a successful deletion from the database.
     */
    public static boolean deleteUser(int userID) {
        try {
            String sql = "DELETE from users WHERE User_ID = ?";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(sql);

            statement.setInt(1, userID);
            statement.execute();
            System.out.println("User with ID " + userID + " deleted.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
