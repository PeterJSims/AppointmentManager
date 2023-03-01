package sims.softwareii.softwareii.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sims.softwareii.softwareii.dao.DBAppointments;
import sims.softwareii.softwareii.dao.DBCustomers;
import sims.softwareii.softwareii.model.Appointment;
import sims.softwareii.softwareii.model.Customer;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

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
    private Label errorMessageLabel;
    @FXML
    private TextField customersSearchField, appointmentsSearchField;


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
            ObservableList<Appointment> allAppointments = DBAppointments.getCustomers();
            appointmentsTable.getItems().clear();
            appointmentsTable.setItems(allAppointments);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customer> allCustomers = DBCustomers.getCustomers();
        ObservableList<Appointment> allAppointments = DBAppointments.getCustomers();

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
