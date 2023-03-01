package sims.softwareii.softwareii.controller;

import javafx.fxml.FXML;
import sims.softwareii.softwareii.model.User;

public class ModifyUserController {
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

}
