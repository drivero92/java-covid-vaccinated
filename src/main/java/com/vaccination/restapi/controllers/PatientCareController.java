/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.controllers;

import com.vaccination.restapi.services.PatientCareService;
import com.vaccination.restapi.models.PatientCare;
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
    public ResponseEntity<List<PatientCare>> getPCsByIdPatient(@PathVariable("patientId") Integer id) {
        List<PatientCare> _patientCareList = patientCareService.getPCsByIdPatient(id);
        return new ResponseEntity<>(_patientCareList, HttpStatus.OK);
    }
    
    //Returns a list of specific patient vaccinated
    @GetMapping("/get/last_patient_care_byPatientId/{patientId}")
    public ResponseEntity<PatientCare> getLastPCByIdPatient(@PathVariable("patientId") Integer id) {
        PatientCare _patientCare = patientCareService.getPatientCareByIdPatient(id);
        return new ResponseEntity<>(_patientCare, HttpStatus.OK);
    }
    
    //Returns a list of patients vaccinated with the same vaccine
    @GetMapping("/list_vaccines_patient_care/{vaccineId}")
    public ResponseEntity<List<PatientCare>> getPCsByIdVaccine(@PathVariable("vaccineId") Integer id) {
        List<PatientCare> _patientCareList = patientCareService.getPCsByIdVaccine(id);
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
    public ResponseEntity<?> addPatientCare(@Valid @RequestBody PatientCare pc) {
        PatientCare _patientCare = patientCareService.addManagedPatientCare(pc);
        //Another way to response
        return ResponseEntity.ok(new MessageResponse(
                "Patient care was successfully added", _patientCare));
    }
    
    //Update the patient care
    @PutMapping("/update")
    public ResponseEntity<PatientCare> updatePatientCare(@RequestBody @Valid PatientCare pc) {
        PatientCare _patientCare = patientCareService.updatePatientCare(pc);
        return new ResponseEntity<>(_patientCare, HttpStatus.OK);
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
