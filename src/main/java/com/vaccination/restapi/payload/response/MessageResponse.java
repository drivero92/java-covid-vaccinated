/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.payload.response;

import com.vaccination.restapi.models.PatientCare;

/**
 *
 * @author daniel
 */
public class MessageResponse {
    
    private String message;
    private PatientCare patientCare;

    public MessageResponse(String message) {
        this.message = message;
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
}
