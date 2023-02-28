package sims.softwareii.softwareii;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sims.softwareii.softwareii.database.JDBC;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        JDBC.startConnection();
        Locale.setDefault(new Locale("fr"));
        ResourceBundle rb = ResourceBundle.getBundle("bundle/lang", Locale.getDefault());
        System.out.println(rb.getString("error"));

        JDBC.closeConnection();

        //        launch();
    }
}