/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.models;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "patient_vaccine")
public class PatientCare {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    @NonNull
    private Integer id;
    
    @Column(name = "fk_patient")
    @NonNull
    private Integer idPatient;
    
    @JoinColumn(name = "fk_patient", insertable = false, updatable = false)
    @OneToOne(cascade = CascadeType.MERGE)//Merge funciona para actualizar los otros objetos vacuna y paciente pero baja la seguridad al actualizar en pc
    @NonNull
    private Patient patient;
    
    @Column(name = "fk_vaccine")
    @NonNull
    private Integer idVaccine;    
    
    @JoinColumn(name = "fk_vaccine", insertable = false, updatable = false)
    @OneToOne(cascade = CascadeType.MERGE)
    @NonNull
    private Vaccine vaccine;
    
    @NonNull
    private Byte dose;
    
    @NonNull
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
        this.idPatient = idPatient;
        this.vaccine = vaccine;
        this.idVaccine = idVaccine;
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

    public Integer getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(Integer idPatient) {
        this.idPatient = idPatient;
    }

    public Integer getIdVaccine() {
        return idVaccine;
    }

    public void setIdVaccine(Integer idVaccine) {
        this.idVaccine = idVaccine;
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
