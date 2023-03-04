package sims.custpoint.model;

/**
 * A class representing the data model for the report of customers by division and country.
 */
public class DivisionReport {
    /**
     * The entry's country name.
     */
    private final String countryName;
    /**
     * The entry's division name.
     */
    private String divisionName;

    /**
     * The count of customers in that division.
     */
    private int count;

    /**
     * @param countryName  The name of the country represented in the report.
     * @param divisionName The name of the division represented in the report.
     * @param count        The count of customers in that division.
     */
    public DivisionReport(String countryName, String divisionName, int count) {
        this.countryName = countryName;
        this.divisionName = divisionName;
        this.count = count;
    }

    /**
     * Retrieve the country name associated with the entry.
     *
     * @return The entry's country name.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Retrieve the division name associated with the entry.
     *
     * @return The entry's division name.
     */
    public String getDivisionName() {
        return divisionName;
    }


    /**
     * Retrieve the count of customers within that division.
     *
     * @return The division's customer count.
     */
    public int getCount() {
        return count;
    }
}
