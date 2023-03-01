package sims.custpoint.model;

import java.time.LocalDateTime;

/**
 * A class which represents an entry into the Appointments table of the database, providing an attribute for each necessary field.
 *
 * @author Peter Sims
 */
public class Appointment {
    /**
     * The appointment entry's unique ID.
     */
    private int appointmentID;
    /**
     * The appointment entry's appointment title.
     */
    private String title;
    /**
     * The appointment's description.
     */
    private String description;
    /**
     * The location of the appointment.
     */
    private String location;
    /**
     * The type of appointment.
     */
    private String type;
    /**
     * The start date and time of the appointment.
     */
    private LocalDateTime start;
    /**
     * The ending date and time of the appointment.
     */
    private LocalDateTime end;

    /**
     * The ID tying the appointment to the Customers table.
     */
    private int customerID;

    /**
     * The ID typing the appointment to the Users table.
     */
    private int userID;

    /**
     * The ID tying the appointment to the Contacts table.
     */
    private int contactID;

    /**
     * @param appointmentID The entry's unique ID.
     * @param title         The entry's title.
     * @param description   The entry's description.
     * @param location      The entry's location.
     * @param type          The entry's appointment type.
     * @param start         The entry's starting date and time in LocalDateTime format.
     * @param end           The entry's ending date and time in LocalDateTime format.
     * @param customerID    The entry's customer ID tying it to a Customers table entry.
     * @param userID        The entry's user ID tying it to a Users table entry.
     * @param contactID     The entry's contact ID tying it to a Contacts table entry.
     */
    public Appointment(int appointmentID, String title, String description, String location,
                       String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Retrieve the unique ID tied to the appointment entry.
     *
     * @return The entry's unique ID.
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Retrieve the appointment entry's title.
     *
     * @return The appointment entry's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieve the appointment entry's description.
     *
     * @return The appointment entry's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieve the appointment entry's location.
     *
     * @return The appointment entry's location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Retrieve the appointment entry's specified type.
     *
     * @return The appointment entry's specified type.
     */
    public String getType() {
        return type;
    }

    /**
     * Retrieve the appointment entry's starting date and time.
     *
     * @return The appointment entry's starting date and time in LocalDateTime format.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Retrieve the appointment entry's ending date and time.
     *
     * @return The appointment entry's ending date and time in LocalDateTime format.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Retrieve the entry's ID linking it to the Customers table.
     *
     * @return The entry's ID linking it to the Customers table.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Retrieve the entry's ID linking it to the Users table.
     *
     * @return The entry's ID linking it to the Users table.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Retrieve the entry's ID linking it to the Contacts table.
     *
     * @return The entry's ID linking it to the Contacts table.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * A String representation of the appointment for displaying in the application.
     *
     * @return A String of the appointment's title and unique ID for display in the application.
     */
    @Override
    public String toString() {
        return getTitle() + " | [ID: " + getAppointmentID() + "]";
    }
}
