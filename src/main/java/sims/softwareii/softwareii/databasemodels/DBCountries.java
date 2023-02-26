package sims.softwareii.softwareii.databasemodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sims.softwareii.softwareii.database.JDBC;
import sims.softwareii.softwareii.model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DBCountries {

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

    public static void checkDateConversion() {
        System.out.println("Create Date Test:");
        String sql = "select Create_Date from countries";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("Create_Date");
                System.out.println("CD: " + ts.toLocalDateTime().toString());
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
