package sims.softwareii.softwareii.temp;

import sims.softwareii.softwareii.database.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TempMethods {

    /**
     * Provides a method for converting stored time entries from the countries table (creation date) to the user's local time, providing a checking method for time differences. Taken from a Mark Kinkead lecture.
     */
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
