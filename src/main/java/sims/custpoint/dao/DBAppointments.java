package sims.custpoint.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sims.custpoint.database.JDBC;
import sims.custpoint.model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * A class holding static methods for communication with the Appointments table in the database linked in the JDBC class.
 * Contains all CRUD actions.
 *
 * @author Peter Sims
 */
public class DBAppointments {

    /**
     * Inserts a new user into the Customers table with the provided fields.
     *
     * @param title       The new appointment's provided title.
     * @param description A description of the new appointment.
     * @param location    The location of the new appointment.
     * @param type        The new appointment's type.
     * @param start       The starting time and date of the new appointment.
     * @param end         The ending time and date of the new appointment.
     * @param customerID  The customer ID tying the new appointment to a Customers table entry.
     * @param userID      The user ID tying the new appointment to a Users table entry.
     * @param contactID   The contact ID tying the new appointment to a Contacts table entry.
     * @return A boolean representing a successful insertion of data into the Appointments table.
     */
    public static boolean createAppointment(String title, String description, String location,
                                            String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ? ,? ,? ,? ,? )";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, type);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(start));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(end));
            preparedStatement.setInt(7, customerID);
            preparedStatement.setInt(8, userID);
            preparedStatement.setInt(9, contactID);

            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns an ObservableList-type list of appointments queried from the database. Holds all appointment IDs, titles, descriptions, location, and start and end times, as well as corresponding user IDs, customer IDs, and contact IDs for each row of data.
     *
     * @return An ObservableList-type list of Appointment objects.
     */
    public static ObservableList<Appointment> getCustomers() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            iterateQueryResults(appointmentList, rs);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return appointmentList;
    }
    /**
     * Returns an ObservableList-type list of appointments queried from the database based upon a provided title.
     *
     * @return An ObservableList-type list of Appointment objects with a matching title field.
     */
    public static ObservableList<Appointment> getAppointmentByTitle(String title) {
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE LOWER(Title) LIKE CONCAT(LOWER(?), '%')";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            iterateQueryResults(appointmentsList, rs);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return appointmentsList;
    }

    /**
     * Returns an ObservableList-type list of Appointments queried from the database based upon a provided ID.
     *
     * @return An ObservableList-type list of Customer objects with a matching ID field.
     */
    public static ObservableList<Appointment> getAppointmentByID(int id) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE Appointment_ID=?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            iterateQueryResults(appointmentList, rs);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Iterates through a provided ResultSet and sets all items within to new additions in the provided appointmentsList. Operates via the mutable nature of the ObservableList.
     *
     * @param appointmentList The ObservableList object to contain all appointments returned from the database query.
     * @param rs              The result of the database query run against the Appointments table.
     * @throws SQLException Providing database access error information.
     */
    private static void iterateQueryResults(ObservableList<Appointment> appointmentList, ResultSet rs) throws SQLException {
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");

            appointmentList.add(new Appointment(appointmentID, title, description, location, type, start, end, customerID, userID, contactID));
        }
    }

    /**
     * Allows a user to change a specific appointment's information.
     *
     * @param appointmentID The appointment ID representing the entry in the database which will be updated.
     * @param title         The title of the updated appointment.
     * @param description   The description of the updated appointment.
     * @param location      The location of the updated appointment.
     * @param type          The updated appointment's type.
     * @param start         The start date and time of the updated appointment.
     * @param end           The end date and time of the updated appointment.
     * @param customerID    The customer ID linking the updated appointment to the Customers table.
     * @param userID        The user ID linking the updated appointment to the Users table.
     * @param contactID     The contact ID linking the updated appointment to the Contacts table.
     * @return A boolean representing the successful update of data.
     */
    public static boolean updateAppointment(int appointmentID, String title, String description, String location,
                                            String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location= ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ? ";

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, type);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(start));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(end));
            preparedStatement.setInt(7, customerID);
            preparedStatement.setInt(8, userID);
            preparedStatement.setInt(9, contactID);
            preparedStatement.setInt(10, appointmentID);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * Remove a specific appointment from the Appointments database based on the provided ID.
     *
     * @param appointmentID the ID of the appointment to be removed from the Appointments database.
     * @return A boolean representing a successful deletion of an appointment from the database.
     */
    public static void deleteAppointment(int appointmentID) {
        try {
            String sql = "DELETE from appointments WHERE Appointment_ID = ?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

            preparedStatement.setInt(1, appointmentID);
            preparedStatement.execute();
            System.out.println("Appointment with ID " + appointmentID + " deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
