package sims.softwareii.softwareii.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sims.softwareii.softwareii.database.JDBC;
import sims.softwareii.softwareii.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * A class holding static methods for communication with the Customers table in the database linked in the JDBC class.
 * Contains all CRUD actions.
 *
 * @author Peter Sims
 */
public class DBCustomers {

    /**
     * Inserts a new user into the Customers table with the provided fields.
     *
     * @param name               The new customer's provided name.
     * @param customerAddress    The new customer's given address.
     * @param customerPostalCode The new customer's postal code.
     * @param customerPhone      The new customer's primary phone number.
     * @param divisionID         The ID of the division where the customer is located.
     * @return A boolean representing a successful insertion into the database.
     */
    public static boolean createCustomer(String name, String customerAddress, String customerPostalCode, String customerPhone, int divisionID) {
        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, customerAddress);
            preparedStatement.setString(3, customerPostalCode);
            preparedStatement.setString(4, customerPhone);
            preparedStatement.setInt(5, divisionID);

            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns an ObservableList-type list of Customers queried from the database. Holds all customer IDs, names, addresses, postal codes, phone numbers, and division IDs.
     *
     * @return An ObservableList-type list of Customer objects.
     */
    public static ObservableList<Customer> getCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM customers";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            iterateQueryResults(customerList, rs);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return customerList;
    }

    /**
     * Returns an ObservableList-type list of Customers queried from the database based upon a provided name. Holds all customer IDs, names, addresses, postal codes, phone numbers, and division IDs.
     *
     * @return An ObservableList-type list of Customer objects with a matching name field.
     */
    public static ObservableList<Customer> getCustomerByName(String name) {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM customers WHERE LOWER(Customer_Name) LIKE CONCAT(LOWER(?), '%')";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            iterateQueryResults(customerList, rs);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return customerList;
    }

    /**
     * Returns an ObservableList-type list of Customers queried from the database based upon a provided ID. Holds all customer IDs, names, addresses, postal codes, phone numbers, and division IDs.
     *
     * @return An ObservableList-type list of Customer objects with a matching ID field.
     */
    public static ObservableList<Customer> getCustomerByID(int id) {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM customers WHERE Customer_ID=?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            iterateQueryResults(customerList, rs);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return customerList;
    }

    /**
     * Iterates through a provided ResultSet and sets all items within to new additions in the provided customerList. Operates via the mutable nature of the ObservableList.
     *
     * @param customerList The ObservableList object to contain all customers returned from the database query.
     * @param rs           The result of the database query run against the Customers table.
     * @throws SQLException Providing database access error information.
     */
    private static void iterateQueryResults(ObservableList<Customer> customerList, ResultSet rs) throws SQLException {
        while (rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String customerPostalCode = rs.getString("Postal_Code");
            String customerPhone = rs.getString("Phone");
            int divisionID = rs.getInt("Division_ID");

            customerList.add(new Customer(customerID, customerName, customerAddress, customerPostalCode, customerPhone, divisionID));
        }
    }

    /**
     * Allows a user to change a customer's username and/or password.
     *
     * @param customerID         The unique customer ID tied to the particular customer having their information updated.
     * @param name               The name to be tied to the existing customer ID.
     * @param customerAddress    The address to be tied to the existing customer ID.
     * @param customerPostalCode The postal code to be tied to the existing customer ID.
     * @param customerPhone      The phone number to be tied to the existing customer ID.
     * @param divisionID         The regional division ID to be tied to the existing customer ID.
     * @return A boolean representing a successful update of the database.
     */
    public static boolean updateUser(int customerID, String name, String customerAddress, String customerPostalCode, String customerPhone, int divisionID) {
        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code= ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ? ";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, customerAddress);
            ps.setString(3, customerPostalCode);
            ps.setString(4, customerPhone);
            ps.setInt(5, divisionID);
            ps.setInt(6, customerID);

            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * Clear the customer's attached appointments in the Appointments table, then remove the customer from the Customer table, both of which done via referencing the Customer_ID field.
     *
     * @param customerID the ID of the customer to be removed from the Customer table, as well as the field to identify the customer's appointments to be removed.
     * @return A boolean representing a successful deletion of a user from the database (and their corresponding appointments).
     */
    public static boolean deleteUser(int customerID) {
        try {
            String sql = "DELETE FROM appointments WHERE Customer_ID = ? ";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(sql);
            statement.setInt(1, customerID);
            statement.execute();
            System.out.println("Appointments for Customer with ID " + customerID + " deleted.");


            String sql2 = "DELETE from customers WHERE Customer_ID = ?";
            PreparedStatement statement2 = JDBC.getConnection().prepareStatement(sql2);

            statement2.setInt(1, customerID);
            statement2.execute();
            System.out.println("Customer with ID " + customerID + " deleted.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
