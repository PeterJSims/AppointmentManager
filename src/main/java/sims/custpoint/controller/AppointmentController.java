package sims.custpoint.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sims.custpoint.dao.DBAppointments;
import sims.custpoint.model.Appointment;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {
    private static boolean isAddMode;
    private static Appointment appointmentToModify;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    @FXML
    private static ObservableList<Appointment> allAppointments;
    @FXML
    private static ObservableList<String> startTimeStrings;
    private static ObservableList<String> endTimeStrings;


    /**
     * A method for verifying whether an appointment overlaps with an existing appointment based on five conditions I identified.
     *
     * @param newApp The appointment to be checked against overlapping times in other appointments.
     * @return A boolean representing that the given appointment does not overlap with any previous entries.
     */
    @FXML
    private boolean isNotOverlappingAppointment(Appointment newApp) {

        for (Appointment app : allAppointments) {
            LocalDateTime newAppStart = newApp.getStart();
            LocalDateTime newAppEnd = newApp.getEnd();
            LocalDateTime appStart = app.getStart();
            LocalDateTime appEnd = app.getEnd();

            // 1 -- new appointment overlaps an appointment at its beginning
            if ((newAppStart.isBefore(appStart) && newAppEnd.isAfter(appStart))
                    // 2 -- new appointment overlaps an existing appointing on both time ends
                    || (newAppStart.isBefore(appStart) && newAppEnd.isAfter(appEnd))
                    // 3 -- new appointment overlaps an appointment over its end
                    || (newAppStart.isBefore(appEnd) && newAppEnd.isAfter(appEnd))
                    // 4 -- new appointment is in the middle of an existing appointment
                    || (newAppStart.isAfter(appStart) && newAppEnd.isBefore(appEnd))
                    // 5 -- new appointment starts at the same time as an existing or ends at the same time as an existing
                    || (newAppStart.equals(appStart) || newAppEnd.equals(appEnd))) {
                return false;
            }
        }
        return true;
    }


    /**
     * Generates times between 8AM EST and 10PM EST to be viewed by the user in their local time. The items are then saved to the ObservableList objects holding start and end times.
     * Taken in part from Mark Kinkead lecture.
     */
    @FXML
    private static void populateTimeFields() {
        // NOTE FOR ME: parsing the times generated below will maintain their local characteristic (ie parsing "08:00" is always 08:00).
        LocalDateTime startTime = LocalDateTime.now(ZoneId.of("America/New_York")).withHour(8).withMinute(0);
        LocalDateTime endTime = LocalDateTime.now(ZoneId.of("America/New_York")).withHour(22).withMinute(0);
        ZoneId localZone = ZoneId.systemDefault();

        while (startTime.isBefore(endTime.plusSeconds(1))) {
            ZonedDateTime localTime = startTime.atZone(ZoneId.of("America/New_York")).withZoneSameInstant(localZone);
            startTimeStrings.add(localTime.format(formatter));
            endTimeStrings.add(localTime.format(formatter));
            startTime = startTime.plusMinutes(15);
        }
        // Remove the last entry (10PM EST) from the start times and the first entry (8AM EST) from the end times.
        startTimeStrings.remove(startTimeStrings.size() - 1);
        endTimeStrings.remove(0);
    }


    /**
     * Sets an Appointment object to the class that is received from the Main Menu view.
     *
     * @param appointment The part to be modified.
     */
    @FXML
    public static void receivePassedAppointment(Appointment appointment) {
        AppointmentController.appointmentToModify = appointment;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isAddMode = appointmentToModify == null;

        allAppointments = DBAppointments.getAppointments();
        populateTimeFields();
    }
}

