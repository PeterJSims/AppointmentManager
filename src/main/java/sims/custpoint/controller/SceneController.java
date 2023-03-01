package sims.custpoint.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sims.custpoint.database.JDBC;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author Peter Sims
 * <p>
 * A class for delegating the work of switching  scenes within other controllers.
 */
public class SceneController implements Initializable {


    /**
     * The method actually performing the scene switching. Performed via creation of new Stage and Scene objects and passing in relevant outside objects to determine new scene.
     *
     * @param sceneFileName The full path to the new scene's fxml file.
     * @param event         The event initially calling the action to switch scenes.
     */
    @FXML
    private static void switchScene(String sceneFileName, ActionEvent event) {
        Stage stage;
        Scene scene;

        try {
            Parent root = FXMLLoader.load(SceneController.class.getResource(sceneFileName));
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Could not load " + sceneFileName);
        }

    }

    /**
     * Switches the current scene to the Main Menu screen when called.
     *
     * @param event The event initially calling the action to switch scenes.
     */
    @FXML
    public static void switchToMainMenuScreen(ActionEvent event) {
        switchScene("/sims/custpoint/main-menu.fxml", event);
    }


    /**
     * Closes all scenes and thus the program itself. Because all scenes are closed and the application is finished, the database connection is also closed.
     */
    @FXML
    public static void exitAllScenes() {

        JDBC.closeConnection();
        Platform.exit();
    }

    /**
     * The method called upon the initializing of the controller.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
