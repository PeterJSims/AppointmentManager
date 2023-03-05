package sims.custpoint.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sims.custpoint.dao.DBAppointments;
import sims.custpoint.dao.DBContacts;
import sims.custpoint.dao.DBCustomers;
import sims.custpoint.dao.DBUsers;
import sims.custpoint.model.*;

import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * A class responsible for handling logic within the Appointment screen. Consists of parsing forms for updating or adding new appointments, empty field checks, logic checks, time checks, addition/update to the database, and routing back to the main menu.
 */
public class AppointmentController implements Initializable {
    private static boolean isAddMode;
    private static Appointment appointmentToModify;
    private static final DateTimeFormatter hourMinuteFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @FXML
    private ObservableList<Appointment> allAppointments;
    @FXML
    private ObservableList<String> startTimeStrings = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> endTimeStrings = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    @FXML
    private ObservableList<User> allUsers = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    @FXML
    private Label titleLabel, errorMessageLabel, errorMessageLabel2;
    @FXML
    private DatePicker appointmentDatePicker;
    @FXML
    private TextField idTextField, titleTextField, typeTextField, locationTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private ComboBox<String> startTimesComboBox, endTimesComboBox;
    @FXML
    private ComboBox<Contact> contactComboBox;
    @FXML
    private ComboBox<User> userComboBox;
    @FXML
    private ComboBox<Customer> customerComboBox;


    /**
     * A method for verifying whether a customer's proposed appointment time overlaps with an existing appointment time based on five identified conditions.
     *
     * @param customerID  The customer who is avoiding an overlap of appointment times.
     * @param newAppStart The proposed new appointment's start time.
     * @param newAppEnd   The proposed new appointment's end time.
     * @return A boolean representing that the given appointment does not overlap with any previous entries.
     */
    @FXML
    private boolean isNotOverlappingAppointment(int customerID, LocalDateTime newAppStart, LocalDateTime newAppEnd) {
        for (Appointment app : allAppointments) {
            if (customerID == app.getCustomerID()) {

                // No need to check the appointment against itself.
                if (!isAddMode && (app.getAppointmentID() == appointmentToModify.getAppointmentID())) {
                    continue;
                }

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

                    errorMessageLabel.setText("This appoint conflicts with an existing appointment:");
                    errorMessageLabel2.setText("ID:" + app.getAppointmentID() + " " + app.getStart().format(hourMinuteFormatter) + " to " + app.getEnd().format(hourMinuteFormatter));
                    System.out.println(("ID:" + app.getAppointmentID() + " " + app.getStart().format(hourMinuteFormatter) + " to " + app.getEnd().format(hourMinuteFormatter)));
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Generates times between 8AM EST and 10PM EST to be viewed by the user in their local time. The items are then saved to the ObservableList objects holding start and end times.
     * Taken in part from Mark Kinkead lecture.
     */
    @FXML
    private void populateTimeFields() {
        LocalDateTime startTime = LocalDateTime.now(ZoneId.of("America/New_York")).withHour(8).withMinute(0);
        LocalDateTime endTime = LocalDateTime.now(ZoneId.of("America/New_York")).withHour(22).withMinute(0);
        ZoneId localZone = ZoneId.systemDefault();

        while (startTime.isBefore(endTime.plusSeconds(1))) {
            ZonedDateTime localTime = startTime.atZone(ZoneId.of("America/New_York")).withZoneSameInstant(localZone);
            startTimeStrings.add(localTime.format(hourMinuteFormatter));
            endTimeStrings.add(localTime.format(hourMinuteFormatter));
            startTime = startTime.plusMinutes(15);
        }
        // Remove the last entry (10PM EST) from the start times
        startTimeStrings.remove(startTimeStrings.size() - 1);

        startTimesComboBox.setItems(startTimeStrings);
        endTimesComboBox.setItems(endTimeStrings);
    }

    /**
     * Executes the method for creating/adding all time entries to their ComboBox elements, as well as setting users, contacts, and customer ComboBox values.
     */
    @FXML
    private void populateComboBoxes() {
        populateTimeFields();
        customerComboBox.setItems(allCustomers);
        userComboBox.setItems(allUsers);
        contactComboBox.setItems(allContacts);
    }


    /**
     * Exits the add/update process and reroutes the user to the main menu screen upon.
     *
     * @param e THe button click action event on the Cancel button.
     */
    @FXML
    private void onClickCancelButton(ActionEvent e) {
        SceneController.switchToMainMenuScreen(e);
    }

    /**
     * Sets the starting fields and labels for the screen depending on if the action is to be adding or updating an appointment.
     */
    @FXML
    private void setFields() {
        if (!isAddMode) {
            String startTime = appointmentToModify.getStart().format(hourMinuteFormatter);
            String endTime = appointmentToModify.getEnd().format(hourMinuteFormatter);

            titleLabel.setText("Modify Appointment");
            idTextField.setText(String.valueOf(appointmentToModify.getAppointmentID()));
            titleTextField.setText(appointmentToModify.getTitle());
            typeTextField.setText(appointmentToModify.getType());
            descriptionTextArea.setText(appointmentToModify.getDescription());
            locationTextField.setText(appointmentToModify.getLocation());

            startTimesComboBox.getSelectionModel().select(startTime);
            endTimesComboBox.getSelectionModel().select(endTime);
            appointmentDatePicker.setValue(appointmentToModify.getStart().toLocalDate());

            userComboBox.getSelectionModel().select(allUsers.stream().filter(x -> x.getUserID() == appointmentToModify.getUserID()).findFirst().orElse(null));
            contactComboBox.getSelectionModel().select(allContacts.stream().filter(x -> x.getContactID() == appointmentToModify.getContactID()).findFirst().orElse(null));
            customerComboBox.getSelectionModel().select(allCustomers.stream().filter(x -> x.getCustomerID() == appointmentToModify.getCustomerID()).findFirst().orElse(null));
        } else {
            titleLabel.setText("Add Appointment");
        }
    }

    /**
     * A method to continuously ensure selected appointment times meet both temporal and customer overlap logic.
     * @param e The combo box the action has come from, either the start time or end time.
     */
    @FXML
    private void onChangeAppointmentTime(ActionEvent e) {
        errorMessageLabel.setText("");
        errorMessageLabel2.setText("");
        if (startTimesComboBox.getValue() == null && endTimesComboBox.getValue() == null) {
            return;
        }
        if (startTimesComboBox.getValue() != null && e.getSource().equals(startTimesComboBox)) {
            // Set end times box to items > than index of startTimes box entry in start times
            int indexOfStartTime = startTimeStrings.indexOf(startTimesComboBox.getValue());
            if (endTimeStrings.indexOf(endTimesComboBox.getValue()) <= indexOfStartTime) {
                endTimesComboBox.getSelectionModel().select(indexOfStartTime + 1);
            }
        }
        if (startTimesComboBox.getValue() != null && endTimesComboBox.getValue() != null && appointmentDatePicker.getValue() != null && customerComboBox.getValue() != null) {
            ObservableList<LocalDateTime> dates = parseDates();
            int customerID = Integer.valueOf(customerComboBox.getValue().getCustomerID());
            isNotOverlappingAppointment(customerID, dates.get(0), dates.get(1));
        }

    }

    /**
     * Takes the text fields representing the day and hour/minute and turns them into LocalDateTime objects. In the case that the appointment goes over 24:00 hours on a day, also adds one to the end date.
     *
     * @return An ObservableList object containing parsed LocalDateTime appointment start and end times.
     */
    @FXML
    private ObservableList<LocalDateTime> parseDates() {
        ObservableList<LocalDateTime> startAndEndDates = FXCollections.observableArrayList();

        String startTime = startTimesComboBox.getValue();
        String endTime = endTimesComboBox.getValue();
        String day = appointmentDatePicker.getValue().toString();

        LocalDateTime startDateTime = LocalDate.parse(day, dayFormatter).atTime(LocalTime.parse(startTime));
        LocalDateTime endDateTime = LocalDate.parse(day, dayFormatter).atTime(LocalTime.parse(endTime));
        if (startDateTime.isBefore(endDateTime)) endDateTime.plusDays(1);

        startAndEndDates.add(startDateTime);
        startAndEndDates.add(endDateTime);

        return startAndEndDates;
    }

    /**
     * Upon clicking submission, the application will verify that all fields were filled out. If so, it will then parse data from the fields. If this is a new appointment, a new entry will be added into the database. Otherwise, an existing entry will be altered.
     *
     * @param e THe button click action event on the Submit button.
     */
    @FXML
    private void onClickSubmitButton(ActionEvent e) {
        if (allFieldsEntered()) {
            try {
                String title = titleTextField.getText();
                String type = typeTextField.getText();
                String description = descriptionTextArea.getText();
                String location = locationTextField.getText();
                int customerID = customerComboBox.getValue().getCustomerID();
                int userID = userComboBox.getValue().getUserID();
                int contactID = contactComboBox.getValue().getContactID();

                ObservableList<LocalDateTime> times = parseDates();


                if (isAddMode) {
                    DBAppointments.createAppointment(title, description, location, type, times.get(0), times.get(1), customerID, userID, contactID);
                    System.out.println("Appointment added");
                } else {
                    DBAppointments.updateAppointment(appointmentToModify.getAppointmentID(), title, description, location, type, times.get(0), times.get(1), customerID, userID, contactID);
                    System.out.println("Appointment updated.");
                }
                SceneController.switchToMainMenuScreen(e);
            } catch (Exception err) {
                errorMessageLabel.setText("Error communicating with server.");
                System.out.println(err);
            }

        }
    }

    /**
     * A method that checks each field to ensure that data has been written to it as no appointment field can contain empty data. Returns a boolean based on all fields passing.
     *
     * @return A boolean marking that the validation checks passed.
     */
    @FXML
    private boolean allFieldsEntered() {
        if (titleTextField.getText().equals("")) {
            errorMessageLabel.setText("Enter an appointment title before submitting.");
            return false;
        } else if (typeTextField.getText().equals("")) {
            errorMessageLabel.setText("Enter an appointment type before submitting.");
            return false;
        } else if (descriptionTextArea.getText().equals("")) {
            errorMessageLabel.setText("Enter an appointment description before submitting.");
            return false;
        } else if (locationTextField.getText().equals("")) {
            errorMessageLabel.setText("Enter an appointment location before submitting.");
            return false;
        } else if (startTimesComboBox.getValue() == null) {
            errorMessageLabel.setText("Select an appointment start time.");
            return false;
        } else if (endTimesComboBox.getValue() == null) {
            errorMessageLabel.setText("Select an appointment end time.");
            return false;
        } else if (appointmentDatePicker.getValue() == null) {
            errorMessageLabel.setText("Select an appointment date.");
            return false;
        } else if (customerComboBox.getValue() == null) {
            errorMessageLabel.setText("Select a customer for the appointment.");
            return false;
        } else if (contactComboBox.getValue() == null) {
            errorMessageLabel.setText("Select a contact for the appointment.");
            return false;
        } else if (userComboBox.getValue() == null) {
            errorMessageLabel.setText("Select a user.");
            return false;
        }
        return true;
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
        allCustomers = DBCustomers.getCustomers();
        allContacts = DBContacts.getContacts();
        allUsers = DBUsers.getUsers();

        populateComboBoxes();
        setFields();
    }
}

