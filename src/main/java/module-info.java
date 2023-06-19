module appdieta {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens appdieta to javafx.fxml;

    exports appdieta;
}
