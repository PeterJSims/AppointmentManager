package sims.custpoint.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sims.custpoint.model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        if (customerToEdit == null){
//            do add stuff
//        } else {
//            do modify stuff
//        }
    }

}
