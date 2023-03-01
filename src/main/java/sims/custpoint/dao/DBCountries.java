package sims.custpoint.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sims.custpoint.database.JDBC;
import sims.custpoint.model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class holding static methods for communication with the Countries table in the database linked in the JDBC class.
 * Contains a method for retrieving all countries.
 *
 * @author Peter Sims
 */
public class DBCountries {
    /**
     * Returns an ObservableList-type list of Country objects queried from the database based on a provided country ID.
     *
     * @return An ObservableList-type list of Country objects.
     */
    public static ObservableList<Country> getCountries() {
        ObservableList<Country> cList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM countries";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                cList.add(new Country(countryId, countryName));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return cList;
    }


}
