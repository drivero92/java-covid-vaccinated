/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.dtos;

import com.vaccination.restapi.models.Patient;
import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 *
 * @author daniel
 */
public class PatientCareDTO {
    private Integer id;
    private Integer patientId;
    private Patient patient;
    private Integer vaccineId;
    @Valid
    private FullVaccineDTO vaccine;
    @Positive(message = "Dose must be greater than zero")
    @NotNull(message = "Dose must be greater than zero")
    private Byte dose;
    @NotNull(message = "Dose date must not be null")
    private LocalDate doseDate;
    private boolean completeDose;

    public PatientCareDTO() {
    }

    public PatientCareDTO(  Integer id, 
                            Integer patientId, 
                            Patient patient, 
                            Integer vaccineId, 
                            FullVaccineDTO vaccine, 
                            Byte dose, 
                            LocalDate doseDate, 
                            boolean completeDose) {
        this.id = id;
        this.patientId = patientId;
        this.patient = patient;
        this.vaccineId = vaccineId;
        this.vaccine = vaccine;
        this.dose = dose;
        this.doseDate = doseDate;
        this.completeDose = completeDose;
    }   

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Integer getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(Integer vaccineId) {
        this.vaccineId = vaccineId;
    }

    public FullVaccineDTO getVaccine() {
        return vaccine;
    }

    public void setVaccine(FullVaccineDTO vaccine) {
        this.vaccine = vaccine;
    }

    public Byte getDose() {
        return dose;
    }

    public void setDose(Byte dose) {
        this.dose = dose;
    }

    public LocalDate getDoseDate() {
        return doseDate;
    }

    public void setDoseDate(LocalDate doseDate) {
        this.doseDate = doseDate;
    }

    public boolean isCompleteDose() {
        return completeDose;
    }

    public void setCompleteDose(boolean completeDose) {
        this.completeDose = completeDose;
    }
    
}
