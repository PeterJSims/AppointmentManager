package sims.softwareii.softwareii.controller;

import javafx.collections.ObservableList;
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

    /**
     * LAMBDA FUNCTION: the ifPresent() method on the Alert class takes a function as an argument, allowing for a lambda expression to be passed in. This can avoid an extra, unnecessary layer of functional decomposition by creating an anonymous function to be run on its value.
     * Allows a user to shut down the CustPoint management system.
     */
    @FXML
    public void onClickExitButton() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit Program?");
        alert.setContentText("Confirm to exit the program");
        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) SceneController.exitAllScenes();
        });

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
