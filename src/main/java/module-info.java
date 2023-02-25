module sims.softwareii.softwareii {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens sims.softwareii.softwareii to javafx.fxml;
    exports sims.softwareii.softwareii;
}