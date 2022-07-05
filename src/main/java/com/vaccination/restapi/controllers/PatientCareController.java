/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.controllers;

import com.vaccination.restapi.services.PatientCareService;
import com.vaccination.restapi.models.PatientCare;
import com.vaccination.restapi.payload.response.MessageResponse;
import com.vaccination.restapi.services.VaccineService;

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
@RequestMapping("/patient_cares")
public class PatientCareController {
    
    @Autowired
    PatientCareService patientCareService;
    
    @Autowired
    VaccineService vacccineService;
    
    //Return patients who are vaccinated
    @GetMapping("/list")
    public ResponseEntity<List<PatientCare>> getPatientCares() {
        List<PatientCare> _patientCareList = patientCareService.getPatientCares();
        return new ResponseEntity<>(_patientCareList, HttpStatus.OK);
    }
    
    //Return a specific patient vaccinated
    @GetMapping("/get/{patientCareId}")
    public ResponseEntity<PatientCare> getPatientCare(@PathVariable("patientCareId") Integer id) {
        PatientCare _patientCare = patientCareService.getPatientCare(id);
        return new ResponseEntity<>(_patientCare, HttpStatus.OK);
    }
    
    //Returns a list of specific patient vaccinated
    @GetMapping("/list_patient_care/{patientId}")
    public ResponseEntity<List<PatientCare>> getPCsByPatientId(@PathVariable("patientId") Integer id) {
        List<PatientCare> _patientCareList = patientCareService.getPCsByPatientId(id);
        return new ResponseEntity<>(_patientCareList, HttpStatus.OK);
    }
    
    //Returns a list of specific patient vaccinated
    @GetMapping("/get/last_patient_care_byPatientId/{patientId}")
    public ResponseEntity<PatientCare> getLastPCByPatientId(@PathVariable("patientId") Integer id) {
        PatientCare _patientCare = patientCareService.getPatientCareByPatientId(id);
        return new ResponseEntity<>(_patientCare, HttpStatus.OK);
    }
    
    //Returns a list of patients vaccinated with the same vaccine
    @GetMapping("/list_vaccines_patient_care/{vaccineId}")
    public ResponseEntity<List<PatientCare>> getPCsByVaccineId(@PathVariable("vaccineId") Integer id) {
        List<PatientCare> _patientCareList = patientCareService.getPCsByVaccineId(id);
        return new ResponseEntity<>(_patientCareList, HttpStatus.OK);
    }
    
    //
    @GetMapping("/list_vaccines_patient_care_byDose/{dose}")
    public ResponseEntity<List<PatientCare>> getPCsByDose(@PathVariable("dose") Byte dose) {
        List<PatientCare> _patientCareList = patientCareService.getPCsByDose(dose);
        return new ResponseEntity<>(_patientCareList, HttpStatus.OK);
    }
    
    //Add a new vaccinated patient
    @PostMapping("/save")
    public ResponseEntity<?> addPatientCare(@Valid @RequestBody PatientCare pc, BindingResult result) {
        if (result.hasErrors()) {
            List<String> _errors = new ArrayList<>();
            for (ObjectError allError : result.getAllErrors()) {
                _errors.add(allError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(new MessageResponse(String.format("%s", _errors)));
        } else {
        PatientCare _patientCare = patientCareService.addManagedPatientCare(pc);
        //Another way to response
        return ResponseEntity.ok(new MessageResponse(
                "Patient care was successfully added", _patientCare));
        }
    }
    
    //Update the patient care
    @PutMapping("/update")
    public ResponseEntity<?> updatePatientCare(@RequestBody @Valid PatientCare pc, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError allError : result.getAllErrors()) {
                errors.add(allError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(new MessageResponse(String.format("%s", errors)));
        } else {
            PatientCare _patientCare = patientCareService.updatePatientCare(pc);
            return ResponseEntity.ok(new MessageResponse(
                    "Patient care was successfully updated", _patientCare));
        }        
    }
    
    //Deletes a patient care
    //@param Integer id
    @DeleteMapping(path = "/delete/{patientCareId}")
    //revisar si esta intentando eliminar un paciente que se vacunado
    public ResponseEntity<?> deletePatientCare(@PathVariable("patientCareId") Integer id) {
        patientCareService.deletePatienCare(id);
        //Another way to response
        return ResponseEntity.ok(new MessageResponse(
                "Patient care was successfully deleted"));
    }
}
