package sims.softwareii.softwareii.model;


/**
 * A class which represents an entry into the First-Level Divisions table of the database, providing an attribute for each necessary field.
 *
 * @author Peter Sims
 */
public class Division {
    /**
     * The division entry's immutable and unique ID.
     */
    private int divisionID;

    /**
     * The division entry's division name.
     */
    private String division;

    /**
     * The country to which the division is linked.
     */
    private int countryID;

    /**
     * @param divisionID The entry's unique division ID.
     * @param division   The entry's given division name.
     * @param countryID  The country to which the entry is linked.
     */
    public Division(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     * Retrieve the entry's unique ID.
     *
     * @return The entry's ID.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Retrieve the entry's provided division name.
     *
     * @return The name of the specific division.
     */
    public String getDivision() {
        return division;
    }

    /**
     * Retrieve the entry's linking country's ID.
     *
     * @return The country ID belonging to the country in which the division resides.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * A String representation of the class for user forms in the application.
     * @return A String object to represent the current division.
     */
    @Override
    public String toString() {
        return getDivision();
    }
}
