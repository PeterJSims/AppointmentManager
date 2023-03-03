package sims.custpoint.model;

/**
 * A class which represents an entry into the Users table of the database, providing an attribute for each necessary field.
 *
 * @author Peter Sims
 */
public class User {
    public static User currentUser;
    /**
     * The user entry's immutable and unique ID.
     */
    private final int userID;

    /**
     * The username attached to the unique ID in the database.
     */
    private String userName;

    /**
     * The password attached to the unique ID in the database.
     */
    private String password;

    /**
     * @param userID   The entry's unique ID
     * @param userName The entry's username.
     * @param password The entry's password.
     */
    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Retrieve the entry's User ID.
     * @return The entry's unique ID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Retrieve the entry's username field.
     * @return The username for the given user.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Retrieve the entry's password field.
     * @return The password for the given user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get the current user logged into the application.
     * @return The current user logged into the application.
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Set the current user logged into the application.
     */
    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    /**
     * A String representation of the user for display in the application.
     * @return A String representation of the user with their user ID.
     */
    @Override
    public String toString() {
        return getUserName() + "  [ID: " + getUserID() + "]";
    }
}
