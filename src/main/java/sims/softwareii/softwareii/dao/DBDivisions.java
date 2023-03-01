package sims.softwareii.softwareii.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sims.softwareii.softwareii.database.JDBC;
import sims.softwareii.softwareii.model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * A class holding static methods for communication with the First-Level Divisions table in the database linked in the JDBC class.
 * Contains methods for retrieving all divisions or the ones specified by a linking country ID.
 *
 * @author Peter Sims
 */
public class DBDivisions {

    /**
     * Returns an ObservableList-type list of Divisions queried from the database based on a provided country ID.
     *
     * @param countryID The linking ID from the Countries table entry to which the division belongs.
     * @return An ObservableList-type list of Division objects.
     */
    public static ObservableList<Division> getDivisionsByCountryID(int countryID) {

        ObservableList<Division> dListByCountry = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ? ";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int country = rs.getInt("Country_ID");
                dListByCountry.add(new Division(divisionID, divisionName, country));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return dListByCountry;
    }

    /**
     * Returns an ObservableList-type list of Divisions queried from the database. Holds all division IDs, division names, and tying country ids.
     *
     * @return An ObservableList-type list of User objects.
     */
    public static ObservableList<Division> getDivisions() {
        ObservableList<Division> dList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                dList.add(new Division(divisionID, divisionName, countryID));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return dList;
    }
}
