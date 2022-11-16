/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "vaccines")
public class Vaccine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccine_id", unique = true)
    private Integer id;
    
    @NotBlank(message = "Vaccine name should not be blank.")
    @NotEmpty(message = "The name of the vaccine must not be empty.")
    private String name;

    @PositiveOrZero(message = "The amount of the vaccine must be greater than zero.")
    @NotNull(message = "The amount of the vaccine must not be empty.")
    private Integer quantity;
    
    @Positive(message = "Vaccine rest days must be greater than zero.")
    @NotNull(message = "Vaccine rest days should not be empty.")
    private Short restDays;
    
    @Positive(message = "The number of doses to complete the vaccine must be greater than zero.")
    @NotNull(message = "The number of doses to complete the vaccine must not be empty.")
    private Byte numberDoses;
    
    @ManyToMany(targetEntity = FullVaccine.class, 
                cascade = {CascadeType.MERGE, CascadeType.PERSIST}, 
                fetch=FetchType.LAZY, 
                mappedBy = "vaccines")
    @JsonBackReference
    private Set<FullVaccine> fullVaccines = new HashSet<>();

    public Vaccine() {
    }

    public Vaccine(String name, Integer quantity, Short restDays, Byte numberDoses) {
        this.name = name;
        this.quantity = quantity;
        this.restDays = restDays;
        this.numberDoses = numberDoses;
    }

    public Vaccine(Integer id) {
        this.id = id;
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

    public Set<FullVaccine> getFullVaccines() {
        return fullVaccines;
    }

    public void setFullVaccines(Set<FullVaccine> fullVaccines) {
        this.fullVaccines = fullVaccines;
    }
    
    public void removeFullVaccines(Integer fullVaccineId) {
        FullVaccine _fullVaccine = this.fullVaccines.stream().filter(fv -> fv.getId() == fullVaccineId).findFirst().orElse(null);
        if (_fullVaccine != null) {
            this.fullVaccines.remove(_fullVaccine);
            _fullVaccine.getVaccines().remove(this);
        }
    }
}
