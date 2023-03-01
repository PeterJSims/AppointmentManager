package sims.custpoint.model;

/**
 * A class which represents an entry into the Countries table of the database, providing an attribute for each necessary field.
 *
 * @author Peter Sims
 */
public class Country {
    /**
     * The country entry's immutable and unique ID.
     */
    private int countryID;

    /**
     * The country entry's name.
     */
    private String countryName;

    /**
     *
     * @param countryID The entry's unique ID.
     * @param countryName The entry's corresponding name.
     */
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * Retrieve the unique ID tied to the country.
     * @return The entry's unique ID.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Retrieve the name of the country.
     * @return The country's name.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * A String representation of the country for displaying in the application.
     * @return A String of the country's name for display in the application.
     */
    @Override
    public String toString() {
        return getCountryName();
    }
}
