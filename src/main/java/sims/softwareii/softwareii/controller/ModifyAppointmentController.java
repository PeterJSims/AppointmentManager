package sims.softwareii.softwareii.controller;

import javafx.fxml.FXML;
import sims.softwareii.softwareii.model.Appointment;

public class ModifyAppointmentController {
    private static Appointment appointmentToModify;


    /**
     * Sets an Appointment object to the class that is received from the Main Menu view.
     *
     * @param appointment The part to be modified.
     */
    @FXML
    public static void receivePassedAppointment(Appointment appointment) {
        ModifyAppointmentController.appointmentToModify = appointment;
    }

}

