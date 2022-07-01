/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.models;

/**
 *
 * @author daniel
 */
public class VaccineList {
    private Vaccine vaccine;

    public VaccineList(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }
    
    public boolean compatible(Vaccine vaccine) {
        boolean resp = false;
        if(this.vaccine.getId() == 6 && vaccine.getId() == 5) {
            resp = true;
        }
//        if(this.vaccine.getId() == 1 && vaccine.getId() == 5) {
//            resp = true;
//        }
//        if(this.vaccine.getId() == 1 && vaccine.getId() == 5) {
//            resp = true;
//        }
//        if(this.vaccine.getId() == 1 && vaccine.getId() == 5) {
//            resp = true;
//        }
        return resp;
    }
}
