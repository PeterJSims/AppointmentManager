package sims.softwareii.softwareii.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sims.softwareii.softwareii.database.JDBC;
import sims.softwareii.softwareii.model.Customer;
import sims.softwareii.softwareii.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCustomers {

    public void createCustomer(String name, String customerAddress, String customerPostalCode, String customerPhone, int divisionID) {
        try {
            String sql = "INSERT INTO Customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, customerAddress);
            preparedStatement.setString(3, customerPostalCode);
            preparedStatement.setString(4, customerPhone);
            preparedStatement.setInt(5, divisionID);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Customer> getCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Customers";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");

                customerList.add(new Customer(customerID, customerName, customerAddress, customerPostalCode, customerPhone, divisionID));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return customerList;
    }

    public static void updateUser(int customerID, String name, String customerAddress, String customerPostalCode, String customerPhone, int divisionID) {
        try {
            String sql = "UPDATE Customers SET Customer_Name = ?, Address = ?, Postal_Code= ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ? ";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, customerAddress);
            ps.setString(3, customerPostalCode);
            ps.setString(4, customerPhone);
            ps.setInt(5, divisionID);
            ps.setInt(6, customerID);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(int customerID) {
        try {
            String sql = "DELETE from Customers WHERE Customer_ID = ?";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(sql);

            statement.setInt(1, customerID);
            statement.execute();
            System.out.println("Customer with ID " + customerID + " deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
