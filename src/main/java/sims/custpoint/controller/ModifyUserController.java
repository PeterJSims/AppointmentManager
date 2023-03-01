package sims.custpoint.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sims.custpoint.model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyUserController implements Initializable {
    public static User currentUser;

    /**
     * Sets a User object to the class that is received from the Login view. This user can be shared across views to provide the current user's information in other fields.
     *
     * @param user The user that is logged into the application.
     */
    @FXML
    public static void receivePassedPart(User user) {
        currentUser = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
