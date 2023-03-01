package sims.softwareii.softwareii.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sims.softwareii.softwareii.database.JDBC;
import sims.softwareii.softwareii.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUsers {


    public static boolean validateUser(String userName, String password) {
        try {
            String sql = "SELECT * FROM Users WHERE User_Name = ? AND Password = ? ";

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

    public static void createUser(String userName, String password) {
        try {
            String sql = "INSERT INTO Users (User_Name, Password) VALUES (?, ?)";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<User> getUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Users";

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

    public static void updateUser(int userID, String userName, String userPassword) {
        try {
            String sql = "UPDATE Users SET User_Name = ?, Password = ? WHERE User_ID = ? ";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, userName);
            ps.setString(2, userPassword);
            ps.setInt(3, userID);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(int userID) {
        try {
            String sql = "DELETE from Users WHERE User_ID = ?";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(sql);

            statement.setInt(1, userID);
            statement.execute();
            System.out.println("User with ID " + userID + " deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JDBC.startConnection();
        System.out.println(DBUsers.validateUser("test", "test"));
        System.out.println(DBUsers.validateUser("asdf", "xcv"));
        JDBC.closeConnection();
    }
}
