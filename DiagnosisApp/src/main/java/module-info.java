module com.example.diagnosisapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.diagnosisapp to javafx.fxml;
    exports com.example.diagnosisapp;
}