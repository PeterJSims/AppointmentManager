package sims.custpoint.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sims.custpoint.model.Appointment;

import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {
    private static Appointment appointmentToModify;


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
//        if (customerToEdit == null){
//            do add stuff
//        } else {
//            do modify stuff
//        }
    }
}

