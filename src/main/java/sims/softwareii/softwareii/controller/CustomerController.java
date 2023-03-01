package sims.softwareii.softwareii.controller;

import javafx.fxml.FXML;
import sims.softwareii.softwareii.model.Customer;

public class CustomerController {
    private static Customer customerToEdit;


    /**
     * Sets a Customer object to the Class that is received from the Main Menu view.
     *
     * @param customer The part to be modified.
     */
    @FXML
    public static void receivePassedCustomer(Customer customer) {
        CustomerController.customerToEdit = customer;
    }

}
