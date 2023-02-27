package sims.softwareii.softwareii.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sims.softwareii.softwareii.database.JDBC;
import sims.softwareii.softwareii.model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContacts {
    // Check what/if this DB file is needed -- For now, use the below for creating all others (CRUD where needed)

    public void createContact(String name, String email) {
        try {
            String sql = "INSERT INTO Contacts (Contact_Name, Email) VALUES (?, ?)";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public static void updateContact(int contactID, String contactName, String email) {
        try {
            String sql = "UPDATE Contact SET Contact_Name = ?, Email = ? WHERE Contact_ID = ? ";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, contactName);
            ps.setString(2, email);
            ps.setInt(3, contactID);

            ps.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteContact(int contactID) {
        try {
            String sql = "DELETE from Contacts WHERE Contact_Id = ?";
            PreparedStatement statement = JDBC.getConnection().prepareStatement(sql);

            statement.setInt(1, contactID);
            statement.execute();
            System.out.println("Contact with ID " + contactID + " deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
