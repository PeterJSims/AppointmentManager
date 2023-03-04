package sims.custpoint.dao;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sims.custpoint.database.JDBC;
import sims.custpoint.model.Division;
import sims.custpoint.model.DivisionReport;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class holding static methods for communication with the database to retrieve details necessary for reports.
 *
 * @author Peter Sims
 */
public class DBReport {

    /**
     * select countries.Country, first_level_divisions.Division, count(customers.Customer_ID)
     * from countries, first_level_divisions, customers
     * where first_level_divisions.Division_ID = customers.Division_ID AND countries.Country_ID = first_level_divisions.Country_ID
     * group by countries.Country, Division;
     */


    /**
     * Returns an ObservableList-type list of DivisionReport objects containing query results.
     *
     * @return An ObservableList-type list of DivisionReport objects.
     */
    public static ObservableList<DivisionReport> getDivisionsReport() {
        ObservableList<DivisionReport> reportList = FXCollections.observableArrayList();

        try {
            String sql = "select countries.Country, first_level_divisions.Division, count(customers.Customer_ID) as Count " +
                    "from countries, first_level_divisions, customers " +
                    "where first_level_divisions.Division_ID = customers.Division_ID AND countries.Country_ID = first_level_divisions.Country_ID " +
                    "group by countries.Country, Division";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String countryName = rs.getString("Country");
                String divisionName = rs.getString("Division");
                int count = rs.getInt("Count");
                reportList.add(new DivisionReport(countryName, divisionName, count));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return reportList;
    }

}
