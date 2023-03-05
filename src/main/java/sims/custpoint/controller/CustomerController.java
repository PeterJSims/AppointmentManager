package sims.custpoint.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sims.custpoint.dao.DBCountries;
import sims.custpoint.dao.DBCustomers;
import sims.custpoint.dao.DBDivisions;
import sims.custpoint.model.Country;
import sims.custpoint.model.Customer;
import sims.custpoint.model.Division;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * A class responsible for handling logic within the Customer screen. Consists of parsing forms for updating or adding new customers, empty field checks, logic checks, addition/update to the database, and routing back to the main menu.
 *
 * @author Peter Sims
 */
public class CustomerController implements Initializable {
    private static Customer customerToEdit;
    private boolean isAddMode;

    @FXML
    private ObservableList<Country> countries;
    @FXML
    private ObservableList<Division> divisions;
    @FXML
    private ComboBox<Country> countryComboBox;
    @FXML
    private ComboBox<Division> divisionComboBox;
    @FXML
    private Label titleLabel, errorMessageLabel;
    @FXML
    private TextField idTextField, nameTextField, addressTextField, postalCodeTextField, phoneNumberTextField;


    /**
     * Sets a Customer object to the Class that is received from the Main Menu view.
     *
     * @param customer The part to be modified.
     */
    @FXML
    public static void receivePassedCustomer(Customer customer) {
        CustomerController.customerToEdit = customer;
    }

    @FXML
    private void onChangeCountryComboBox() {
        Country selection = countryComboBox.getValue();
        divisionComboBox.setItems(divisions.filtered(x -> x.getCountryID() == selection.getCountryID()));
    }

    /**
     * Sets the starting fields and labels for the screen depending on if the action is to be adding or updating a customer.
     */
    @FXML
    private void setFields() {
        countryComboBox.setItems(countries);
        if (!isAddMode) {
            titleLabel.setText("Modify Customer");
            idTextField.setText(String.valueOf(customerToEdit.getCustomerID()));
            nameTextField.setText(customerToEdit.getCustomerName());
            addressTextField.setText(customerToEdit.getAddress());
            postalCodeTextField.setText(customerToEdit.getPostalCode());
            phoneNumberTextField.setText(customerToEdit.getPostalCode());
            Division currentDivision = divisions.stream().filter(x -> x.getDivisionID() == customerToEdit.getDivisionID()).findAny().orElse(null);
            Country country = countries.stream().filter(x -> x.getCountryID() == currentDivision.getCountryID()).findAny().orElse(null);
            divisionComboBox.setItems(FXCollections.observableArrayList(divisions.stream().filter(x -> x.getCountryID() == currentDivision.getCountryID()).toList()));

            countryComboBox.getSelectionModel().select(country);
            divisionComboBox.getSelectionModel().select(currentDivision);
        } else {
            titleLabel.setText("Add Customer");
            divisionComboBox.setItems(divisions);
        }

    }

    /**
     * A method that checks each field to ensure that data has been written to it as no customer field can contain empty data. Returns a boolean based on all fields passing.
     *
     * @return A boolean marking that the validation checks passed.
     */
    @FXML
    private boolean allFieldsEntered() {
        if (nameTextField.getText().equals("")) {
            errorMessageLabel.setText("Enter a customer name before submitting.");
            return false;
        } else if (addressTextField.getText().equals("")) {
            errorMessageLabel.setText("Please enter an address before submitting.");
            return false;
        } else if (postalCodeTextField.getText().equals("")) {
            errorMessageLabel.setText("Enter a postal code before submitting.");
            return false;
        } else if (phoneNumberTextField.getText().equals("")) {
            errorMessageLabel.setText("Enter a phone number before submitting.");
            return false;
        } else if (countryComboBox.getValue() == null) {
            errorMessageLabel.setText("Select a country before submitting.");
            return false;
        } else if (divisionComboBox.getValue() == null) {
            errorMessageLabel.setText("Select a division before submitting.");
            return false;
        }
        return true;
    }

    /**
     * Upon clicking submission, the application will verify that all fields were filled out. If so, it will then parse data from the fields and submit. If this is a new customer, a new entry will be added into the database. Otherwise, an existing entry will be altered.
     *
     * @param e THe button click action event on the Submit button.
     */
    @FXML
    private void onClickSubmitButton(ActionEvent e) {
        if (allFieldsEntered()) {
            try {
                String name = nameTextField.getText();
                String address = addressTextField.getText();
                String postalCode = postalCodeTextField.getText();
                String phone = postalCodeTextField.getText();
                int divisionId = divisionComboBox.getValue().getDivisionID();

                if (isAddMode) {
                    DBCustomers.createCustomer(name, address, postalCode, phone, divisionId);
                    System.out.println("Customer added");
                } else {
                    DBCustomers.updateUser(customerToEdit.getCustomerID(), name, address, postalCode, phone, divisionId);
                    System.out.println("Customer updated");
                }
                SceneController.switchToMainMenuScreen(e);
            } catch (Exception err) {
                errorMessageLabel.setText("Error communicating with server.");
                System.out.println(err);
            }

        }
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isAddMode = customerToEdit == null;
        countries = DBCountries.getCountries();
        divisions = DBDivisions.getDivisions();

        System.out.println(isAddMode);
        setFields();
    }

}
