package sims.softwareii.softwareii.model;

/**
 * A class which represents an entry into the Contacts table of the database, providing an attribute for each necessary field.
 *
 * @author Peter Sims
 */
public class Contact {
    /**
     * The contact entry's unique ID.
     */
    private int contactID;
    /**
     * The contact entry's name.
     */
    private String contactName;
    /**
     * The contact entry's email address.
     */
    private String email;

    /**
     * @param id          The entry's unique ID.
     * @param contactName The entry's name.
     * @param email       The entry's email address.
     */
    public Contact(int id, String contactName, String email) {
        this.contactID = id;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Retrieve the entry's unique ID.
     *
     * @return The entry's unique ID.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Retrieve the entry's name.
     *
     * @return The entry's name.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Retrieve the entry's email address.
     *
     * @return The entry's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * A String representation of the contact for displaying in the application.
     *
     * @return A String of the contact's name and unique ID for display in the application.
     */
    @Override
    public String toString() {
        return getContactName() + " | [ID: " + getContactID() + "]";
    }
}
