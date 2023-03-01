package sims.custpoint.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sims.custpoint.dao.DBUsers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginController implements Initializable {
    static ResourceBundle rb = ResourceBundle.getBundle("bundle/lang", Locale.getDefault());
    static String timeZoneName = TimeZone.getDefault().getID();

    @FXML
    Label title, prompt, errorMessageLabel, timezoneLabel, timezoneDisplay, usernameLabel, passwordLabel;
    @FXML
    Button submitButton, quitButton;
    @FXML
    TextField usernameTextField, passwordTextField;


    /**
     * A method that will determine if all fields are filled out, and if so it will query the database to see if a matching user can be found. If there is one, the user will be sent to the main menu, otherwise an error will display. Lastly it will call a method to log the attempt in a local text file.
     *
     * @param e
     */
    @FXML
    public void onClickSubmit(ActionEvent e)  {
        String userName = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (userName.isBlank() || password.isBlank()) {
            errorMessageLabel.setText(rb.getString("errorMissingFields"));
        } else {
            boolean userExists = DBUsers.findUser(userName);
            if (!userExists) {
                writeAccessLog(userName, false);
                errorMessageLabel.setText(rb.getString("errorBadUsername"));
                return;
            }

            boolean userMatches = DBUsers.validateUser(userName, password);
            if (userMatches) {
                writeAccessLog(userName, true);
                System.out.println("Access Granted");
                SceneController.switchToMainMenuScreen(e);
            } else {
                writeAccessLog(userName, false);
                errorMessageLabel.setText(rb.getString("errorBadPassword"));
            }
        }
    }

    /**
     * A method that clears any error messaging data upon changing text.
     */
    public void onEnteringDataClearWarnings() {
        errorMessageLabel.setText("");
    }

    /**
     * LAMBDA FUNCTION: the ifPresent() method on the Alert class takes a function as an argument, allowing for a lambda expression to be passed in. This can avoid an extra, unnecessary layer of functional decomposition by creating an anonymous function to be run on its value.
     * Allows a user to shut down the Scheduling application. The text's language is set by their location.
     */
    @FXML
    public void onClickQuitButton() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(rb.getString("quitTitle"));
        alert.setContentText(rb.getString("quitAlert"));
        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) SceneController.exitAllScenes();
        });

    }

    /**
     * Writes log information into a local file to track login attempts. Depending on the parameters, the file will mark the username as a failed or successful owner of an attempt.
     * @param userName The username of the user attempting to access the application.
     * @param isSuccess A boolean marking that the login attempt was successful or not.
     */
    private static void writeAccessLog(String userName, boolean isSuccess)  {
        ZonedDateTime currentTime = ZonedDateTime.now();
        try (FileWriter fr = new FileWriter("login_activity.txt", true)) {
            PrintWriter pw = new PrintWriter(fr);
            String successOrFail = isSuccess ? "Successful" : "Failed";
            pw.append(successOrFail + " login attempt by \"" + userName + "\" at " + currentTime + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Sets the various labels, buttons, and other displays to either English or French depending on the user's location.
     */
    @FXML
    private void setLabels() {
        title.setText(rb.getString("title"));
        prompt.setText(rb.getString("prompt"));
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        submitButton.setText(rb.getString("submit"));
        quitButton.setText(rb.getString("quit"));
        timezoneLabel.setText(rb.getString("timezoneLabel"));
        timezoneDisplay.setText(timeZoneName);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLabels();
    }

    public static void main(String[] args) {
        System.out.println(timeZoneName);
        System.out.println(rb.getString("username"));
        System.out.println(rb.getString("error"));
    }
}
