/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "patients")
public class Patient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, name = "patient_id")
    private Integer id;

    @Positive(message = "Patient's ID must be greater than zero.")
    @NotNull(message = "The patient's ID card must not be empty.")
    private Integer ci;
    
    @Pattern(regexp = "^[a-zA-Z ]+$",message = "The patient's name must contain text only.")
    @NotBlank(message = "The patient's name must not be left blank.")
    private String name;
    
    @Pattern(regexp = "^[a-zA-Z ]+$",message = "The patient's last name must contain text only.")
    @NotBlank(message = "The patient's last name must not be left blank.")
    private String lastName;

    @Positive(message = "Patient's age must be greater than zero.")
    @NotNull(message = "The patient's age must not be empty.")
    private Byte age;
    
    public Patient() {
    }

    public Patient(Integer ci, String name, String lastName, Byte age) {
        this.ci = ci;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCi() {
        return ci;
    }

    public void setCi(Integer ci) {
        this.ci = ci;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }
    
}
