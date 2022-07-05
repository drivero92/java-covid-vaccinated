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
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.springframework.lang.NonNull;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "patients")
public class Patient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Integer id;

    @Positive(message = "El carnet de identidad del paciente debe ser mayor a cero")
    @NotNull(message = "El carnet de identidad del paciente no debe estar vacía")
    private Integer ci;
    
    @Pattern(regexp = "^[a-zA-Z ]+$",message = "El nombre del paciente solo debe contener texto")
    @NotBlank(message = "El nombre del paciente no debe quedar en blanco")
    private String name;
    
    @Pattern(regexp = "^[a-zA-Z ]+$",message = "El apellido del paciente solo debe contener texto")
    @NotBlank(message = "El apellido del paciente no debe quedar en blanco")
    private String lastName;

    @Positive(message = "La edad del paciente debe ser mayor a cero")
    @NotNull(message = "La edad del paciente no debe estar vacía")
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
