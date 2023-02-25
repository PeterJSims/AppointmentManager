module sims.softwareii.softwareii {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens sims.softwareii.softwareii to javafx.fxml;
    exports sims.softwareii.softwareii;
    exports sims.softwareii.softwareii.utils;
    opens sims.softwareii.softwareii.utils to javafx.fxml;
}