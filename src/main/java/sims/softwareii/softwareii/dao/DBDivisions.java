package sims.softwareii.softwareii.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sims.softwareii.softwareii.database.JDBC;
import sims.softwareii.softwareii.model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBDivisions {


    public static ObservableList<Division> getCountries() {
        ObservableList<Division> dList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM divisions";

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
