package com.example.diagnosisapp;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Diagnosis {
    private final SimpleStringProperty patientId;
    private final SimpleStringProperty symptoms;
    private final SimpleStringProperty diagnosis;
    private final SimpleStringProperty medicines;
    private final SimpleBooleanProperty wardRequired;

    public Diagnosis(String patientId, String symptoms, String diagnosis, String medicines, boolean wardRequired) {
        this.patientId = new SimpleStringProperty(patientId);
        this.symptoms = new SimpleStringProperty(symptoms);
        this.diagnosis = new SimpleStringProperty(diagnosis);
        this.medicines = new SimpleStringProperty(medicines);
        this.wardRequired = new SimpleBooleanProperty(wardRequired);
    }

    public String getPatientId() {
        return patientId.get();
    }

    public SimpleStringProperty patientIdProperty() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId.set(patientId);
    }

    public String getSymptoms() {
        return symptoms.get();
    }

    public SimpleStringProperty symptomsProperty() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms.set(symptoms);
    }

    public String getDiagnosis() {
        return diagnosis.get();
    }

    public SimpleStringProperty diagnosisProperty() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis.set(diagnosis);
    }

    public String getMedicines() {
        return medicines.get();
    }

    public SimpleStringProperty medicinesProperty() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines.set(medicines);
    }

    public boolean isWardRequired() {
        return wardRequired.get();
    }

    public SimpleBooleanProperty wardRequiredProperty() {
        return wardRequired;
    }

    public void setWardRequired(boolean wardRequired) {
        this.wardRequired.set(wardRequired);
    }
}
