/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.controllers;

import com.vaccination.restapi.dtos.FullVaccineDTO;
import com.vaccination.restapi.exception.ApiRequestException;
import com.vaccination.restapi.models.Vaccine;
import com.vaccination.restapi.models.FullVaccine;
import com.vaccination.restapi.payload.response.MessageResponse;
import com.vaccination.restapi.services.VaccineService;
import java.util.ArrayList;
import java.util.Collection;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
    
    //Returns a vaccine list
    @GetMapping("/list")
    public ResponseEntity<List<Vaccine>> getVaccines() {
        List<Vaccine> _vaccineList = vaccineService.getVaccines();
        return new ResponseEntity<>(_vaccineList, HttpStatus.OK);
    }
    
    //Returns a vaccine from service
    @GetMapping("/{id}")
    public ResponseEntity<Vaccine> getVaccineById(@PathVariable Integer id) {
        Vaccine _vaccine = vaccineService.getVaccine(id);
        return new ResponseEntity<>(_vaccine, HttpStatus.OK);
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<CombinolaVaccine> getVaccineById(@PathVariable Integer id) {
//        FullVaccineDTO _vaccine = vaccineService.getVaccine(id);
//        return new ResponseEntity<>(_vaccine, HttpStatus.OK);
//    }
    
    //Returns a vaccine from service
    @GetMapping("/full_vaccines/{id}")
    public ResponseEntity<Collection<FullVaccine>> getFullVaccinesById(@PathVariable Integer id) {
        Vaccine _vaccine = vaccineService.getVaccine(id);
        return new ResponseEntity<>(_vaccine.getFullVaccines(), HttpStatus.OK);
    }
    
    //Adds a new vaccine
    @PostMapping()
    public ResponseEntity<?> addVaccine(@Valid @RequestBody Vaccine vaccine, BindingResult result) {
        if (result.hasErrors()) {
            List<String> _errors = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                _errors.add(error.getDefaultMessage());
            }
            String message = String.format("%s", _errors);
            return ResponseEntity.badRequest().body(new MessageResponse(message));
        } else {
            Vaccine _vaccine = vaccineService.addVaccine(vaccine);
//            return ResponseEntity.ok(new MessageResponse(
//                "Vaccine was successfully added", _vaccine));
            return new ResponseEntity<>(_vaccine, HttpStatus.OK);
        }        
    }
    
    //Updates a vaccine
    @PutMapping()
    public ResponseEntity<?> updateVaccine(@Valid @RequestBody Vaccine vaccine, BindingResult result) {
        if (result.hasErrors()) {
            List<String> _errors = new ArrayList<>();
            for (ObjectError allError : result.getAllErrors()) {
                _errors.add(allError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(new MessageResponse(String.format("%s", _errors)));
        } else {
            Vaccine _vaccine = vaccineService.updateVaccine(vaccine);
//            return ResponseEntity.ok(new MessageResponse(
//                    "Vaccine was successfully updated", _vaccine));
            return new ResponseEntity<>(_vaccine, HttpStatus.OK);
        }
    }
    
    //Deletes a vaccine
    //@param Integer id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVaccineById(@PathVariable Integer id) {
        vaccineService.deleteVaccine(id);
        return ResponseEntity.ok(new MessageResponse(
                "Vaccine was successfully deleted"));
    }
    
}
