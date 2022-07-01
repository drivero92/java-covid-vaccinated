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
import javax.validation.constraints.NotNull;
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
    private Integer id;
    
    @NotBlank(message = "El nombre de la vacuna no debe estar en blanco")
    @NotEmpty(message = "El nombre de la vacuna no debe estar vacía")
    private String name;

    @NotNull(message = "La cantidad de la vacuna no debe estar vacía")
    private Integer quantity;
    
    @NotNull(message = "Los dias de descanso de la vacuna no debe estar vacía")
    private Short restDays;
    
    @NotNull(message = "El numero de dosis para completar la vacuna no debe estar vacía")
    private Byte completeDose;
    
    //private VaccineList compatibleVaccines;

    public Vaccine() {
    }

    public Vaccine(String name, Integer quantity, Short days, Byte completeDose) {
        this.name = name;
        this.quantity = quantity;
        this.restDays = days;
        this.completeDose = completeDose;
    }
    
//    public Vaccine(String name, Integer quantity, Short restDays, Byte completeDose, VaccineList compatibleVaccines) {
//        this.name = name;
//        this.quantity = quantity;
//        this.restDays = restDays;
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

    public Short getRestDays() {
        return restDays;
    }

    public void setRestDays(Short restDays) {
        this.restDays = restDays;
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
