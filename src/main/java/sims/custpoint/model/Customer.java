package sims.custpoint.model;


/**
 * A class which represents an entry into the Customers table of the database, providing an attribute for each necessary field.
 *
 * @author Peter Sims
 */
public class Customer {
    /**
     * The customer entry's immutable and unique ID.
     */
    private int customerID;
    /**
     * The customer's name.
     */
    private String customerName;
    /**
     * The customer's provided street address.
     */
    private String address;
    /**
     * The customer's provided postal code.
     */
    private String postalCode;
    /**
     * The customer's provided phone number.
     */
    private String phone;
    /**
     * The geographic division in which the customer resides.
     */
    private int divisionID;


    /**
     * @param customerID   The entry's unique ID.
     * @param customerName The entry's provided name.
     * @param address      The entry's provided street address.
     * @param postalCode   The entry's provided postal code.
     * @param phone        The entry's provided phone number.
     * @param divisionID   The division ID linking to an entry in the Divisions table, representing a geographic region.
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phone, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
    }

    /**
     * Retrieve the entry's unique customer ID.
     *
     * @return The entry's unique customer ID.
     */
    public int getCustomerID() {
        return customerID;
    }


    /**
     * Retrieve the name associated with the customer entry.
     *
     * @return The entry's provided name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Retrieve the street address associated with the customer entry.
     *
     * @return The entry's given street address.
     */
    public String getAddress() {
        return address;
    }


    /**
     * Retrieve the postal code associated with the customer entry.
     *
     * @return The entry's provided street address.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Retrieve the phone number associated with the customer entry.
     * @return The entry's provided phone number.
     */
    public String getPhone() {
        return phone;
    }


    /**
     * Retrieve the ID of the geographical region associated with the customer.
     * @return The customer's associated division ID.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * A String representation of the customer for display in the application.
     * @return A String representation of the customer's name with their customer ID.
     */
    @Override
    public String toString() {
        return getCustomerName() + " | [ID: " + getCustomerID() + "]";
    }
}
