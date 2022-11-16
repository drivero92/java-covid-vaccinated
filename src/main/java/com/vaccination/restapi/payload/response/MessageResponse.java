/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.payload.response;

import com.vaccination.restapi.dtos.FullVaccineDTO;
import com.vaccination.restapi.dtos.PatientCareDTO;
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
    private FullVaccineDTO vaccine;
    private PatientCareDTO patientCare;
    

    public MessageResponse(String message) {
        this.message = message;
    }
    
    public MessageResponse(String message, Patient patient) {
        this.message = message;
        this.patient = patient;
    }
    
    public MessageResponse(String message, FullVaccineDTO vaccine) {
        this.message = message;
        this.vaccine = vaccine;
    }

    public MessageResponse(String message, PatientCareDTO patientCare) {
        this.message = message;
        this.patientCare = patientCare;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PatientCareDTO getPatientCare() {
        return patientCare;
    }

    public void setPatientCare(PatientCareDTO patientCare) {
        this.patientCare = patientCare;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public FullVaccineDTO getVaccine() {
        return vaccine;
    }

    public void setVaccine(FullVaccineDTO vaccine) {
        this.vaccine = vaccine;
    }
}
