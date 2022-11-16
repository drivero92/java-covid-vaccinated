/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "full_vaccine")
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class FullVaccine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "full_vaccine_id", unique = true)
    private Integer id;
    
    @Column(name = "vaccine_id", insertable = false, updatable = false)
    private Integer vaccineId;//CHECK, maybe never used
    
    @JoinColumn(name = "vaccine_id")
    @OneToOne(cascade = CascadeType.MERGE)
    private Vaccine vaccine;
    
    @ManyToMany(targetEntity=Vaccine.class, 
                cascade = {CascadeType.MERGE, CascadeType.PERSIST}, 
                fetch=FetchType.LAZY)
    @JsonBackReference
    @JoinTable( name = "compatible_vaccines", 
                joinColumns = @JoinColumn(name = "full_vaccine_id", referencedColumnName = "full_vaccine_id"), 
                inverseJoinColumns = @JoinColumn(name = "vaccine_id", referencedColumnName = "vaccine_id"))
    private Set<Vaccine> vaccines = new HashSet<>();

    public FullVaccine() {
    }

    public FullVaccine(Integer vaccineId, Vaccine vaccine) {
        this.vaccineId = vaccineId;
        this.vaccine = vaccine;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(Integer vaccineId) {
        this.vaccineId = vaccineId;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public Set<Vaccine> getVaccines() {
        return vaccines;
    }

    public void setVaccines(Set<Vaccine> vaccines) {
        this.vaccines = vaccines;
    }
    
    public void removeVaccine(Integer vaccineId) {
        Vaccine _vaccine = this.vaccines.stream()
                                        .filter(t -> Objects.equals(t.getId(), vaccineId))
                                        .findFirst()
                                        .orElse(null);
        if (_vaccine != null) {
            this.vaccines.remove(_vaccine);
            _vaccine.getFullVaccines().remove(this);
        }
    }
}
