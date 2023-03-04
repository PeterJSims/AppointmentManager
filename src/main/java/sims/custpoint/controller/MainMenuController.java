package sims.custpoint.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sims.custpoint.dao.DBCustomers;
import sims.custpoint.dao.DBAppointments;
import sims.custpoint.model.Appointment;
import sims.custpoint.model.Customer;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * A class responsible for handling logic within the Main Menu screen. Consists of generating displays for customers and inventory, adding, updating, and deleted said customers and appointments, checking for pending appointments, and report selection.
 */
public class MainMenuController implements Initializable {
    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    private TableColumn<Customer, Integer> customerIDColumn, customerDivisionIDColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn, customerAddressColumn, customerPostCodeColumn, customerPhoneNumberColumn;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIDColumn, appointmentCustomerIDColumn, appointmentUserIDColumn, appointmentContactIDColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentTitleColumn, appointmentDescriptionColumn, appointmentLocationColumn, appointmentTypeColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentStartTimeColumn, appointmentEndTimeColumn;
    @FXML
    private Label errorMessageLabel, fifteenMinuteLabel, pendingAppointmentLabel;
    @FXML
    private TextField customersSearchField, appointmentsSearchField;
    @FXML
    private RadioButton allDatesRadioButton, currentMonthRadioButton, currentWeekRadioButton;


    /**
     * LAMBDA: The filtering by month and week provided a chance to much more easily create a date checking system. The month allowed for a simple (though slightly long) one liner to filter out unnecessary items, where the week allowed for a statement within the lambda to filter out unnecessary items.
     * A method that handles the radio buttons allowing users to pick if they view all appointments, the appointments this week, or the appointments this month. Instead of using more DB calls, this will simply filter out what is shown based on a Java stream.
     * The month filter selects for the current calendar month and the week filter selects the current day and stops at the end of the week thereafter (i.e. Mon-Sun, Wed-Tues).
     */
    @FXML
    private void selectDateRange() {
        errorMessageLabel.setText("");

        if (allDatesRadioButton.isSelected()) {
            clearAndSetTable("appointments");
        } else if (currentMonthRadioButton.isSelected()) {
            ObservableList<Appointment> filteredByCurrentMonth =
                    DBAppointments.getAppointments().stream().filter(x -> Month.from(x.getStart()).equals(Month.from(LocalDateTime.now())))
                            .collect(Collectors.collectingAndThen(toList(), l -> FXCollections.observableArrayList(l)));
            appointmentsTable.getItems().clear();
            appointmentsTable.setItems(filteredByCurrentMonth);
        } else if (currentWeekRadioButton.isSelected()) {
            ObservableList<Appointment> filteredByCurrentWeek =
                    DBAppointments.getAppointments().stream().filter(x ->
                            {
                                LocalDateTime current = LocalDateTime.now();
                                return x.getStart().isAfter(current) && x.getStart().minusDays(6).isBefore(current);
                            })
                            .collect(Collectors.collectingAndThen(toList(), l -> FXCollections.observableArrayList(l)));
            appointmentsTable.getItems().clear();
            appointmentsTable.setItems(filteredByCurrentWeek);
        }
    }

    /**
     * Search for a customer by passing either its unique ID or the customer name. All items starting with the string or matching the exact ID will be displayed.
     *
     * @param e Event calling the method, either clicking the "Search" button or pressing the enter key in the search field.
     */
    @FXML
    public void onClickSearchCustomersFieldButton(ActionEvent e) {
        errorMessageLabel.setText("");

        String customersSearchText = customersSearchField.getText();
        if (customersSearchText.length() > 0) {
            ObservableList<Customer> identifiedCustomers = DBCustomers.getCustomerByName(customersSearchText);
            if (identifiedCustomers.size() > 0) {
                customersTable.getItems().clear();
                customersTable.setItems(identifiedCustomers);
            } else {
                // No matching Customers found by given name, switch to searching for the ID
                try {
                    ObservableList<Customer> identifiedCustomersByID = DBCustomers.getCustomerByID(Integer.valueOf(customersSearchText));
                    if (identifiedCustomersByID.size() == 0) {
                        errorMessageLabel.setText("No matching customers found");
                    } else {
                        customersTable.getItems().clear();
                        customersTable.setItems(identifiedCustomersByID);
                    }
                } catch (NumberFormatException err) {
                    clearAndSetTable("customers");
                    errorMessageLabel.setText("No matching customers found");
                }
            }
        } else {
            ObservableList<Customer> allCustomers = DBCustomers.getCustomers();
            customersTable.getItems().clear();
            customersTable.setItems(allCustomers);
        }
    }

    /**
     * Search for an appointment by passing either its unique ID or the title. All items starting with the string or matching the exact ID will be displayed.
     *
     * @param e Event calling the method, either clicking the "Search" button or pressing the enter key in the search field.
     */
    @FXML
    public void onClickSearchAppointmentsFieldButton(ActionEvent e) {
        errorMessageLabel.setText("");

        String appointmentsSearchText = appointmentsSearchField.getText();
        if (appointmentsSearchText.length() > 0) {
            ObservableList<Appointment> identifiedAppointments = DBAppointments.getAppointmentByTitle(appointmentsSearchText);
            if (identifiedAppointments.size() > 0) {
                appointmentsTable.getItems().clear();
                appointmentsTable.setItems(identifiedAppointments);
            } else {
                // No matching Appointments found by given name, switch to searching for the ID
                try {
                    ObservableList<Appointment> identifiedAppointmentsByID = DBAppointments.getAppointmentByID(Integer.valueOf(appointmentsSearchText));
                    if (identifiedAppointmentsByID.size() == 0) {
                        errorMessageLabel.setText("No matching appointments found");
                    } else {
                        appointmentsTable.getItems().clear();
                        appointmentsTable.setItems(identifiedAppointmentsByID);
                    }
                } catch (NumberFormatException err) {
                    clearAndSetTable("appointments");
                    errorMessageLabel.setText("No matching appointments found");
                }
            }
        } else {
            clearAndSetTable("appointments");

        }
    }


    /**
     * LAMBDA FUNCTION: the ifPresent() method on the Alert class takes a function as an argument, allowing for a lambda expression to be passed in. This can avoid an extra, unnecessary layer of functional decomposition by creating an anonymous function to be run on its value.
     * Allows a user to shut down the CustPoint management system.
     */
    @FXML
    private void onClickExitButton() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit Program?");
        alert.setContentText("Confirm to exit the program");
        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) SceneController.exitAllScenes();
        });
    }

    /**
     * Resets the provided tableName to the current information in the database. Used when items are edited without leaving the Main Menu view.
     *
     * @param tableName A String name of the table to be reset to the current DB state.
     */
    @FXML
    private void clearAndSetTable(String tableName) {
        if (tableName.equalsIgnoreCase("customers")) {
            ObservableList<Customer> allCustomers = DBCustomers.getCustomers();
            customersTable.getItems().clear();
            customersTable.setItems(allCustomers);
        } else if (tableName.equalsIgnoreCase("appointments")) {
            ObservableList<Appointment> allAppointments = DBAppointments.getAppointments();
            appointmentsTable.getItems().clear();
            appointmentsTable.setItems(allAppointments);
        }
    }

    /**
     * LAMBDA: A cleaner approach to the normal for loop was presented by the anyMatch method. This allowed me to create a stream and iterate through the list until a match is found (or not) and alter the display accordingly. The simple .stream().anyMatch() previously would have required more code than those two methods.
     * <p>
     * Iterates through a provided ObservableList of appointments and determines if any are within the next fifteen minutes. If so, relevant appointment information will be displayed on the screen. And if not, the user will be aware there are no pending appointments.
     *
     * @param appointments
     */
    @FXML
    private void displayPendingAppointment(ObservableList<Appointment> appointments) {
        final int SECONDS_FOR_ALERT = 900;
        appointments.stream().anyMatch(x ->
        {
            long timeToAppointment = Duration.between(LocalDateTime.now(), x.getStart()).toSeconds();
            if (timeToAppointment > 0 && timeToAppointment < SECONDS_FOR_ALERT) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
                fifteenMinuteLabel.setText("The following appointment is within 15 minutes:");
                pendingAppointmentLabel.setText("Appointment ID: " + x.getAppointmentID() + " | User ID: " + x.getUserID() + "  | Start Time: " + x.getStart().format(formatter));
                return true;
            }
            fifteenMinuteLabel.setText("No appointments within the next 15 minutes.");

            return false;
        });
    }

    /**
     * On clicking the add button, move to the Customer screen with the adding functionality enabled.
     *
     * @param e The button press event on the Add button under the Customers table.
     */
    @FXML
    private void onClickAddCustomerButton(ActionEvent e) {
        CustomerController.receivePassedCustomer(null);
        SceneController.switchToCustomerScreen(e);
    }

    /**
     * On clicking the modify button, move to the Customer screen with the modify functionality enabled. The method also passes the Customer entry to modify.
     *
     * @param e The button press event on the Modify button under the Customers table.
     */
    @FXML
    private void onClickModifyCustomerButton(ActionEvent e) {
        errorMessageLabel.setText("");
        Customer currentCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (currentCustomer == null) {
            errorMessageLabel.setText("No customer selected");
        } else {
            CustomerController.receivePassedCustomer(currentCustomer);
            SceneController.switchToCustomerScreen(e);
        }
    }

    /**
     * Deletes the selected customer from the database after confirmation. It also clears all appointments tied to the customer.
     *
     * @param e The button press event on the Delete button under the Customers table.
     */
    @FXML
    private void onClickDeleteCustomerButton(ActionEvent e) {
        errorMessageLabel.setText("");
        Customer currentCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (currentCustomer == null) {
            errorMessageLabel.setText("No customer selected");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer?");
            alert.setContentText("Confirm the deletion of customer \"" + currentCustomer + "\"?");
            alert.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    DBCustomers.deleteUser(currentCustomer.getCustomerID());
                    errorMessageLabel.setText("Customer \"" + currentCustomer + "\"  deleted.");
                }
            });
            clearAndSetTable("customers");
            clearAndSetTable("appointments");
        }
    }

    /**
     * On clicking the add button, move to the Appointment screen with the adding functionality enabled.
     *
     * @param e The button press event on the Add button under the Appointments table.
     */
    @FXML
    private void onClickAddAppointmentButton(ActionEvent e) {
        AppointmentController.receivePassedAppointment(null);
        SceneController.switchToAppointmentScreen(e);
    }

    /**
     * On clicking the modify button, move to the Appointment screen with the modify functionality enabled. The method also passes the Appointment entry to modify.
     *
     * @param e The button press event on the Modify button under the Appointments table.
     */
    @FXML
    private void onClickModifyAppointmentButton(ActionEvent e) {
        errorMessageLabel.setText("");
        Appointment currentAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        if (currentAppointment == null) {
            errorMessageLabel.setText("No appointment selected");
        } else {
            AppointmentController.receivePassedAppointment(currentAppointment);
            SceneController.switchToAppointmentScreen(e);
        }
    }

    /**
     * Removes the currently selected appointment from the database.
     *
     * @param e The button press event on the Delete button under the Appointments table.
     */
    @FXML
    private void onClickDeleteAppointmentButton(ActionEvent e) {
        errorMessageLabel.setText("");
        Appointment currentAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        if (currentAppointment == null) {
            errorMessageLabel.setText("No appointment selected");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Appointment?");
            alert.setContentText("Confirm the deletion of appointment \"" + currentAppointment + "\"?");
            alert.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    DBAppointments.deleteAppointment(currentAppointment.getAppointmentID());
                    errorMessageLabel.setText("Appointment ID: " + currentAppointment.getAppointmentID() + " Type: " + currentAppointment.getType() + "  canceled.");
                }
            });
            clearAndSetTable("appointments");
        }
    }

    /**
     * Initializes the main-menu screen by setting cell values to types and names and populating them.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customer> allCustomers = DBCustomers.getCustomers();
        ObservableList<Appointment> allAppointments = DBAppointments.getAppointments();

        displayPendingAppointment(allAppointments);

        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerDivisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appointmentContactIDColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));

        customersTable.setItems(allCustomers);
        appointmentsTable.setItems(allAppointments);

    }
}
