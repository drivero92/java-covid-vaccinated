/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "patient_vaccine")
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientCare {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Integer id;
    
    @Column(name = "fk_patient")
    //@NotNull(message = "El campo del id del paciente no debe ser nulo")//Patient id field must not be null
    private Integer patientId;
    
    @JoinColumn(name = "fk_patient", insertable = false, updatable = false)
    @OneToOne(cascade = CascadeType.MERGE)//Merge funciona para actualizar los otros objetos vacuna y paciente pero baja la seguridad al actualizar en pc
    //@NotNull(message = "El campo del id del paciente no debe ser nulo")
    private Patient patient;
    
    @Column(name = "fk_vaccine")
    //@NotNull(message = "El campo del id de la vacuna no debe ser nulo")
    private Integer vaccineId;
    
    @JoinColumn(name = "fk_vaccine", insertable = false, updatable = false)
    @OneToOne(cascade = CascadeType.MERGE)
    //@NotNull(message = "El campo del id de la vacuna no debe ser nulo")
    private Vaccine vaccine;
    
    @Positive(message = "La dosis debe ser mayor a cero")
    @NotNull(message = "La dosis no deber ser nulo")
    private Byte dose;
    
    @NotNull(message = "La fecha de la dosis no deber ser nulo")
    private LocalDate doseDate;
    
    private boolean completeDose;

    public PatientCare() {
    }

    public PatientCare( Patient patient, 
                        Integer idPatient, 
                        Vaccine vaccine, 
                        Integer idVaccine, 
                        Byte dose, 
                        LocalDate doseDate,
                        boolean completeDose) {
        this.patient = patient;
        this.patientId = idPatient;
        this.vaccine = vaccine;
        this.vaccineId = idVaccine;
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

    public Integer getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(Integer vaccineId) {
        this.vaccineId = vaccineId;
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

    public PatientCare get() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
