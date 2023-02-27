package sims.softwareii.softwareii;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sims.softwareii.softwareii.dao.DBContacts;
import sims.softwareii.softwareii.dao.DBUsers;
import sims.softwareii.softwareii.database.JDBC;

import java.io.IOException;

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
        DBContacts.getContacts();
        JDBC.closeConnection();

        //        launch();
    }

//    // Testing queries
//    public static void main(String[] args) throws SQLException {
//        DatabaseConnectionManager.getConnection();
//
//        QueryHandler.makeQuery("SELECT * FROM customers");
//        ResultSet results = QueryHandler.getResult();
//        while(results.next()){
//            String customerName = results.getString("customer_name");
//            System.out.println(customerName);
//        }
//
//        DatabaseConnectionManager.closeConnection();
//
//        //        launch();
//    }
}