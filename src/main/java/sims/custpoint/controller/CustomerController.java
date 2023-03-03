package sims.custpoint.controller;


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
     * LAMBDA: Instead of making iterative loops through the Divisions and Countries to find matching items, I could simply run a filter on a stream and get the first item returned.
     */
    @FXML
    private void setFields() {
        if (!isAddMode) {
            titleLabel.setText("Modify Customer");
            idTextField.setText(String.valueOf(customerToEdit.getCustomerID()));
            nameTextField.setText(customerToEdit.getCustomerName());
            addressTextField.setText(customerToEdit.getAddress());
            postalCodeTextField.setText(customerToEdit.getPostalCode());
            phoneNumberTextField.setText(customerToEdit.getPostalCode());
            Division currentDivision = divisions.stream().filter(x -> x.getDivisionID() == customerToEdit.getDivisionID()).findAny().orElse(null);
            Country country = countries.stream().filter(x -> x.getCountryID() == currentDivision.getCountryID()).findAny().orElse(null);
            countryComboBox.getSelectionModel().select(country);
            divisionComboBox.getSelectionModel().select(currentDivision);
        } else {
            titleLabel.setText("Add Customer");
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
                } else {
                    DBCustomers.updateUser(customerToEdit.getCustomerID(), name, address, postalCode, phone, divisionId);
                }
                System.out.println("Customer added");
                SceneController.switchToMainMenuScreen(e);
            } catch (Exception err) {
                System.out.println("Error communicating with server.");
            }

        }
    }

    @FXML
    private void onClickCancelButton(ActionEvent e) {
        SceneController.switchToMainMenuScreen(e);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isAddMode = customerToEdit == null;
        countries = DBCountries.getCountries();
        divisions = DBDivisions.getDivisions();
        countryComboBox.setItems(countries);
        divisionComboBox.setItems(divisions);
        System.out.println(isAddMode);
        setFields();
    }

}
