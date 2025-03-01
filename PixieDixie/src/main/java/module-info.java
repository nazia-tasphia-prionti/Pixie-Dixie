module com.example.pixiedixie {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pixiedixie to javafx.fxml;
    exports com.example.pixiedixie;
}