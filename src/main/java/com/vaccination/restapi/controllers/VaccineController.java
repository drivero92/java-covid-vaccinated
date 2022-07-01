/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.controllers;

import com.vaccination.restapi.models.Vaccine;
import com.vaccination.restapi.payload.response.MessageResponse;
import com.vaccination.restapi.services.VaccineService;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author daniel
 */
@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/vaccines")
public class VaccineController {
    
    @Autowired
    VaccineService vaccineService;
    
    //Returns the vaccine list
    @GetMapping("/list")
    public ResponseEntity<List<Vaccine>> getVaccines() {
        List<Vaccine> _vaccineList = vaccineService.getVaccines();
        return new ResponseEntity<>(_vaccineList, HttpStatus.OK);
    }
    
    //Returns the vaccine from service
    @GetMapping("/get/{id}")
    public ResponseEntity<Vaccine> getVaccine(@PathVariable Integer id) {
        Vaccine _vaccine = vaccineService.getVaccine(id);
        return new ResponseEntity<>(_vaccine, HttpStatus.OK);
    }
    
    //Adds a new vaccine
    @PostMapping("/save")
    public ResponseEntity<?> addVaccine(@Valid @RequestBody Vaccine vaccine) {
        vaccineService.addVaccine(vaccine);
        return ResponseEntity.ok(new MessageResponse(
                "Vaccine was successfully added"));
    }
    
    //Updates the vaccine
    @PutMapping("/update")
    public ResponseEntity<Vaccine> updateVaccine(@Valid @RequestBody Vaccine vaccine) {
        Vaccine _vaccine = vaccineService.updateVaccine(vaccine);
        return new ResponseEntity<>(_vaccine, HttpStatus.OK);
    }
    
    //Deletes a vaccine
    //@param Integer id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVaccine(@PathVariable Integer id) {
        vaccineService.deleteVaccine(id);
        return ResponseEntity.ok(new MessageResponse(
                "Vaccine was successfully deleted"));
    }
    
}
