/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.combinolas;

import com.vaccination.restapi.models.Vaccine;

import java.util.Set;

/**
 *
 * @author daniel
 */
public class CombinolaVaccine {
    private Integer id;
    private String name;
    private Integer quantity;
    private Short restDays;
    private Byte numberDoses;
    private Set<Vaccine> vaccines;

    public CombinolaVaccine() {
    }

    public CombinolaVaccine(Integer id, String name, Integer quantity, Short restDays, Byte numberDoses, Set<Vaccine> vaccines) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.restDays = restDays;
        this.numberDoses = numberDoses;
        this.vaccines = vaccines;
    }

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

    public Byte getNumberDoses() {
        return numberDoses;
    }

    public void setNumberDoses(Byte numberDoses) {
        this.numberDoses = numberDoses;
    }

    public Set<Vaccine> getVaccines() {
        return vaccines;
    }

    public void setVaccines(Set<Vaccine> vaccines) {
        this.vaccines = vaccines;
    }
    
    public void setVaccine(Vaccine vaccine) {
        this.id = vaccine.getId();
        this.name = vaccine.getName();
        this.quantity = vaccine.getQuantity();
        this.restDays = vaccine.getRestDays();
        this.numberDoses = vaccine.getNumberDoses();
    }
}
