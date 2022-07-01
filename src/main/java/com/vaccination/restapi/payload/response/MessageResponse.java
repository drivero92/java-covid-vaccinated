/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.payload.response;

import com.vaccination.restapi.models.Patient;
import com.vaccination.restapi.models.PatientCare;
import com.vaccination.restapi.models.Vaccine;

/**
 *
 * @author daniel
 */
public class MessageResponse {
    
    private String message;    
    private Patient patient;
    private Vaccine vaccine;
    private PatientCare patientCare;
    

    public MessageResponse(String message) {
        this.message = message;
    }
    
    public MessageResponse(String message, Patient patient) {
        this.message = message;
        this.patient = patient;
    }
    
    public MessageResponse(String message, Vaccine vaccine) {
        this.message = message;
        this.vaccine = vaccine;
    }

    public MessageResponse(String message, PatientCare patientCare) {
        this.message = message;
        this.patientCare = patientCare;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PatientCare getPatientCare() {
        return patientCare;
    }

    public void setPatientCare(PatientCare patientCare) {
        this.patientCare = patientCare;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }
}
