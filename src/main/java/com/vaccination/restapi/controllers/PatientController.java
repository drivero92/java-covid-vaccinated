/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.controllers;

import com.vaccination.restapi.services.PatientService;
import com.vaccination.restapi.models.Patient;
import com.vaccination.restapi.payload.response.MessageResponse;

import java.util.ArrayList;
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
@RequestMapping("/patients")
public class PatientController {
    
    @Autowired
    PatientService patientService;
    
    //Returns the patients list from service
    @GetMapping("/list")
    public ResponseEntity<List<Patient>> getPatientsVaccinated() {
        List<Patient> _patientList = patientService.getPatientsVaccinated();
        return new ResponseEntity<>(_patientList, HttpStatus.OK);
    }
    
    //Returns one patient
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable Integer id) {
        Patient _patient = patientService.getPatient(id);
        return new ResponseEntity<>(_patient, HttpStatus.OK);
    }
    
    //Adds a new patient
    @PostMapping()
    public ResponseEntity<?> addPatient(@Valid @RequestBody Patient patient, BindingResult result) {
        if (result.hasErrors()) {
            List<String> _errors = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                _errors.add(error.getDefaultMessage());
            }
            String message = String.format("%s", _errors);
            return ResponseEntity.badRequest().body(new MessageResponse(message));
        } else {
            Patient _patient = patientService.addPatient(patient);
            return ResponseEntity.ok(new MessageResponse(
                    "Patient was successfully added", _patient));
        }        
    }
    
    //Updates the patient
    @PutMapping()
    public ResponseEntity<?> updatePatient(@Valid @RequestBody Patient patient, BindingResult result) {
        if (result.hasErrors()) {
            List<String> _errors = new ArrayList<>();
            for (ObjectError allError : result.getAllErrors()) {
                _errors.add(allError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(new MessageResponse(String.format("%s", _errors)));
        } else {
            Patient _patient = patientService.updatePatient(patient);
            return ResponseEntity.ok(new MessageResponse(
                    "Patient was successfully updated", _patient));
        }        
    }
    
    //Deletes a patient
    //@param Integer id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Integer id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok(new MessageResponse(
                "Patient was successfully deleted"));
    }
}
