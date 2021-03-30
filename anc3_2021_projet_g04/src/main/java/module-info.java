module demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires java.sql;

    opens main to javafx.fxml;
    opens view to javafx.fxml;
    exports main;
    exports view;
}