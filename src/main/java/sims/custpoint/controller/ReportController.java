package sims.custpoint.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sims.custpoint.dao.DBAppointments;
import sims.custpoint.dao.DBContacts;
import sims.custpoint.dao.DBReport;
import sims.custpoint.model.Appointment;
import sims.custpoint.model.Contact;
import sims.custpoint.model.DivisionReport;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.ResourceBundle;

/**
 * A class to handle the logic of populating fields dynamically in the three reports. The first report is how many types of an event happened in a given month. The second displays appointments by the contact assigned. The third displays the number of customers in each first-level division.
 */
public class ReportController implements Initializable {
    private ObservableList<Appointment> allAppointments;
    private ObservableList<Contact> allContacts;
    private ObservableList<DivisionReport> divisionReports;

    @FXML
    private ComboBox<Month> monthComboBox;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private ComboBox<Contact> contactComboBox;
    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    private TableView<DivisionReport> divisionCountTable;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIDColumn, appointmentCustomerIDColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentTitleColumn, appointmentDescriptionColumn, appointmentLocationColumn, appointmentTypeColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentStartTimeColumn, appointmentEndTimeColumn;
    @FXML
    private TableColumn<DivisionReport, String> divisionCountCountryColumn, divisionCountRegionNameColumn;
    @FXML
    private TableColumn<DivisionReport, Integer> divisionCountCustomersColumn;
    @FXML
    private Label countLabel;

    /**
     * Populate values for the three combo boxes in the Report view.
     */
    @FXML
    private void populateComboBoxes() {
        ObservableList<Month> monthsList = FXCollections.observableArrayList();
        for (Month month : Month.values()) monthsList.add(month);
        monthComboBox.setItems(monthsList);

        HashSet<String> typesSet = new HashSet<>();
        for (Appointment a : allAppointments) typesSet.add(a.getType().toUpperCase());
        typeComboBox.setItems(FXCollections.observableList(typesSet.stream().toList()));

        contactComboBox.setItems(allContacts);
    }

    /**
     * Depending on the month and type selected, this method calculates the number of matching appointments.
     */
    @FXML
    private void onChangeFirstReportComboBoxes() {
        if (monthComboBox.getValue() != null && typeComboBox.getValue() != null) {
            Month month = monthComboBox.getValue();
            String type = typeComboBox.getValue();
            long count = allAppointments.stream().filter(x ->
                    x.getType().toUpperCase().equals(type) && x.getStart().getMonth().equals(month)
            ).count();
            countLabel.setText(String.valueOf(count));
        }
    }

    /**
     * On selection of a contact, view only the appointments that list that certain contact.
     */
    @FXML
    private void onChangeContactComboBox() {
        if (contactComboBox.getValue() != null) {
            appointmentsTable.setItems(allAppointments.filtered(x -> x.getContactID() == contactComboBox.getValue().getContactID()));
        }
    }

    /**
     * Clears the memory of list items and returns to the main menu.
     *
     * @param e The mouse click on the Back button.
     */
    @FXML
    private void onClickBackButton(ActionEvent e) {
        allAppointments = null;
        allContacts = null;
        divisionReports = null;
        SceneController.switchToMainMenuScreen(e);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allAppointments = DBAppointments.getAppointments();
        allContacts = DBContacts.getContacts();
        divisionReports = DBReport.getDivisionsReport();


        populateComboBoxes();

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        divisionCountCountryColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        divisionCountRegionNameColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        divisionCountCustomersColumn.setCellValueFactory(new PropertyValueFactory<>("count"));

        divisionCountTable.setItems(divisionReports);

    }
}
