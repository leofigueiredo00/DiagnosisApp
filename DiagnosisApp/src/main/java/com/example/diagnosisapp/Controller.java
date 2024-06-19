package com.example.diagnosisapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private TextField patientIdField;
    @FXML
    private TextField symptomsField;
    @FXML
    private TextField diagnosisField;
    @FXML
    private TextField medicinesField;
    @FXML
    private CheckBox wardCheckBox;
    @FXML
    private TableView<Diagnosis> tableView;
    @FXML
    private TableColumn<Diagnosis, String> patientIdColumn;
    @FXML
    private TableColumn<Diagnosis, String> symptomsColumn;
    @FXML
    private TableColumn<Diagnosis, String> diagnosisColumn;
    @FXML
    private TableColumn<Diagnosis, String> medicinesColumn;
    @FXML
    private TableColumn<Diagnosis, Boolean> wardRequiredColumn;

    private Connection connection;

    public Controller() {
        initializeDatabaseConnection();
    }

    private void initializeDatabaseConnection() {
        try {
            // Load the SQL Server JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Connection URL for SQL Server
           // String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=DiagnosisDB";
            String url = "jdbc:sqlserver://127.0.0.1:3306";
            String user = "root";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                System.out.println("Connection established successfully.");
            } else {
                System.out.println("Failed to establish connection.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error initializing database connection: " + e.getMessage());
        }
    }

    @FXML
    private void initialize() {
        patientIdColumn.setCellValueFactory(cellData -> cellData.getValue().patientIdProperty());
        symptomsColumn.setCellValueFactory(cellData -> cellData.getValue().symptomsProperty());
        diagnosisColumn.setCellValueFactory(cellData -> cellData.getValue().diagnosisProperty());
        medicinesColumn.setCellValueFactory(cellData -> cellData.getValue().medicinesProperty());
        wardRequiredColumn.setCellValueFactory(cellData -> cellData.getValue().wardRequiredProperty().asObject());
    }

    @FXML
    private void handleSave() {
        if (connection == null) {
            System.out.println("Connection is null. Cannot save data.");
            return;
        }

        String patientId = patientIdField.getText();
        String symptoms = symptomsField.getText();
        String diagnosis = diagnosisField.getText();
        String medicines = medicinesField.getText();
        boolean wardRequired = wardCheckBox.isSelected();

        String query = "INSERT INTO Diagnosis (PatientID, Symptoms, Diagnosis, Medicines, WardRequired) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, patientId);
            stmt.setString(2, symptoms);
            stmt.setString(3, diagnosis);
            stmt.setString(4, medicines);
            stmt.setBoolean(5, wardRequired);
            stmt.executeUpdate();
            System.out.println("Data saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @FXML
    private void handleSearch() {
        if (connection == null) {
            System.out.println("Connection is null. Cannot perform search.");
            return;
        }

        String patientId = patientIdField.getText();

        String query = "SELECT * FROM Diagnosis WHERE PatientID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, patientId);
            ResultSet rs = stmt.executeQuery();
            tableView.getItems().setAll(FXCollections.observableArrayList(mapResultSet(rs)));
            System.out.println("Search completed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error performing search: " + e.getMessage());
        }
    }

    @FXML
    private void handleClose() {
        // Close the application
    }

    private List<Diagnosis> mapResultSet(ResultSet rs) throws SQLException {
        List<Diagnosis> list = new ArrayList<>();
        while (rs.next()) {
            Diagnosis diagnosis = new Diagnosis(
                    rs.getString("PatientID"),
                    rs.getString("Symptoms"),
                    rs.getString("Diagnosis"),
                    rs.getString("Medicines"),
                    rs.getBoolean("WardRequired")
            );
            list.add(diagnosis);
        }
        return list;
    }
}
