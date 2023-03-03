package sims.custpoint;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sims.custpoint.database.JDBC;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


/**
 * The main entrypoint into the CustPoint customer/appointment scheduling application.
 */
public class Main extends Application {

    /**
     * A necessary method for classes extending JavaFX's Application abstract class. Provides initializing of the view's components.
     *
     * @param stage stage passed automatically into method via main method's Application.load().
     * @throws IOException Triggered upon error of input/output.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CustPoint - Customer/Appointment Management");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Launch the application via the automatically executed main() method. This application's method will open the database connection, process all screen interactions, and then close the database once the screens are exited.
     *
     * @param args command-line arguments passed into the application.
     */
    public static void main(String[] args) {
        JDBC.startConnection();

//        Locale.setDefault(new Locale("fr"));

        launch();
        JDBC.closeConnection();

    }
}