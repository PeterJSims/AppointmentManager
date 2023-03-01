module sims.custpoint {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens sims.custpoint to javafx.fxml;
    exports sims.custpoint;
    exports sims.custpoint.database;
    opens sims.custpoint.database to javafx.fxml;
    exports sims.custpoint.model;
    opens sims.custpoint.model to javafx.fxml;
    exports sims.custpoint.controller;
    opens sims.custpoint.controller to javafx.fxml;
}