/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "vaccines")
public class Vaccine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Integer id;
    
    @NotBlank
    @NotEmpty(message = "Vaccine name cannot be empty or null")
    private String name;

    @NonNull
    private Integer quantity;
    
    @NonNull
    private Short days;
    
    private Byte completeDose;
    
    //private VaccineList compatibleVaccines;

    public Vaccine() {
    }

    public Vaccine(String name, Integer quantity, Short days, Byte completeDose) {
        this.name = name;
        this.quantity = quantity;
        this.days = days;
        this.completeDose = completeDose;
    }
    
//    public Vaccine(String name, Integer quantity, Short days, Byte completeDose, VaccineList compatibleVaccines) {
//        this.name = name;
//        this.quantity = quantity;
//        this.days = days;
//        this.completeDose = completeDose;
//        this.compatibleVaccines = compatibleVaccines;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Short getDays() {
        return days;
    }

    public void setDays(Short days) {
        this.days = days;
    }

    public Byte getCompleteDose() {
        return completeDose;
    }

    public void setCompleteDose(Byte completeDose) {
        this.completeDose = completeDose;
    }

//    public VaccineList getCompatibleVaccines() {
//        return compatibleVaccines;
//    }
//
//    public void setCompatibleVaccines(VaccineList compatibleVaccines) {
//        this.compatibleVaccines = compatibleVaccines;
//    }
}
