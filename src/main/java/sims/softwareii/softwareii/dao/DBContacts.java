package sims.softwareii.softwareii.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sims.softwareii.softwareii.database.JDBC;
import sims.softwareii.softwareii.model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class holding static methods for communication with the Contacts table in the database linked in the JDBC class.
 * Contains all CRUD actions.
 *
 * @author Peter Sims
 */
public class DBContacts {

    /**
     * Inserts a new user into the Contacts table with the provided fields.
     *
     * @param name  The name to attach to the new contact entry.
     * @param email The email address to attach to the new contact entry.
     * @return A boolean representing a successful insertion of a new Contact entry.
     */
    public static boolean createContact(String name, String email) {
        try {
            String sql = "INSERT INTO contacts (Contact_Name, Email) VALUES (?, ?)";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns an ObservableList-type list of Contact queried from the database. Holds all contact IDs, names, and email addresses for each row of data.
     *
     * @return An ObservableList-type list of Contact objects.
     */
    public static ObservableList<Contact> getContacts() {
        ObservableList<Contact> contactList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM contacts";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                contactList.add(new Contact(contactID, contactName, email));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return contactList;
    }

    /**
     * Allows a user to change a customer's username and/or password.
     *
     * @param contactID   The unique contact ID tied to the particular contact having their information updated.
     * @param contactName The name to be tied to the existing contact ID.
     * @param email       The email address to be tied to the existing contact ID.
     * @return A boolean representing a successful deletion of a contact from the database.
     */
    public static boolean updateContact(int contactID, String contactName, String email) {
        try {
            String sql = "UPDATE contacts SET Contact_Name = ?, Email = ? WHERE Contact_ID = ? ";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, contactName);
            ps.setString(2, email);
            ps.setInt(3, contactID);

            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Removes a contact from the Contacts table based on the provided contact ID.
     *
     * @param contactID the ID of the contact to be removed from the Contacts table.
     * @return A boolean representing a successful deletion of a contact from the database.
     */
    public static boolean deleteContact(int contactID) {
        try {
            String sql = "DELETE from contacts WHERE Contact_Id = ?";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(sql);

            statement.setInt(1, contactID);
            statement.execute();
            System.out.println("Contact with ID " + contactID + " deleted.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
