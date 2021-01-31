module demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;

    opens main to javafx.fxml;
    opens view to javafx.fxml;
    opens mvvm to javafx.fxml;
    opens model to javafx.fxml;
    exports main;
    exports mvvm;
    exports view;
    exports model;
}