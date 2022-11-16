/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.controllers;

import com.vaccination.restapi.dtos.PatientCareDTO;
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
//    @GetMapping("/list")
//    public ResponseEntity<List<PatientCare>> getPatientCares() {
//        List<PatientCare> _patientCareList = patientCareService.getPatientCares();
//        return new ResponseEntity<>(_patientCareList, HttpStatus.OK);
//    }
    @GetMapping("/list")
    public ResponseEntity<List<PatientCareDTO>> getPatientCares() {
        List<PatientCareDTO> _patientCaresListDTO = patientCareService.getPatientCares();
        return new ResponseEntity<>(_patientCaresListDTO, HttpStatus.OK);
    }
    
    //Return a specific patient vaccinated
//    @GetMapping("/get/{patientCareId}")
//    public ResponseEntity<PatientCare> getPatientCare(@PathVariable("patientCareId") Integer id) {
//        PatientCare _patientCare = patientCareService.getPatientCare(id);
//        return new ResponseEntity<>(_patientCare, HttpStatus.OK);
//    }
    @GetMapping("/{patientCareId}")
    public ResponseEntity<PatientCareDTO> getPatientCare(@PathVariable("patientCareId") Integer id) {
        PatientCareDTO _patientCareDTO = patientCareService.getPatientCare(id);
        return new ResponseEntity<>(_patientCareDTO, HttpStatus.OK);
    }
    
    //Returns the patient cares of a specific patient vaccinated
//    @GetMapping("/list_patient_care/{patientId}")
//    public ResponseEntity<List<PatientCare>> getPCsByPatientId(@PathVariable("patientId") Integer id) {
//        List<PatientCare> _patientCareList = patientCareService.getPCsByPatientId(id);
//        return new ResponseEntity<>(_patientCareList, HttpStatus.OK);
//    }
    @GetMapping("/{patientId}/list_patient_care")
    public ResponseEntity<List<PatientCareDTO>> getPCsByPatientId(@PathVariable("patientId") Integer id) {
        List<PatientCareDTO> _patientCareListDTO = patientCareService.getPCsByPatientId(id);
        return new ResponseEntity<>(_patientCareListDTO, HttpStatus.OK);
    }
    
    //Returns a list of specific patient vaccinated
    @GetMapping("/{patientId}/last_patient_care")
    public ResponseEntity<PatientCareDTO> getLastPCByPatientId(@PathVariable("patientId") Integer id) {
        PatientCareDTO _patientCareDTO = patientCareService.getPatientCareByPatientId(id);
        return new ResponseEntity<>(_patientCareDTO, HttpStatus.OK);
    }
    
    //Returns a patient care list of a patient with the same vaccine
    @GetMapping("/{vaccineId}/list_vaccines")
    public ResponseEntity<List<PatientCareDTO>> getPCsByVaccineId(@PathVariable("vaccineId") Integer id) {
        List<PatientCareDTO> _patientCareListDTO = patientCareService.getPCsByVaccineId(id);
        return new ResponseEntity<>(_patientCareListDTO, HttpStatus.OK);
    }
    
    //Returns a list of vaccines by Dose
    @GetMapping("/{dose}/list_vaccines_dose")
    public ResponseEntity<List<PatientCareDTO>> getPCsByDose(@PathVariable("dose") Byte dose) {
        List<PatientCareDTO> _patientCareListDTO = patientCareService.getPCsByDose(dose);
        return new ResponseEntity<>(_patientCareListDTO, HttpStatus.OK);
    }
    
    //Add a new vaccinated patient
    @PostMapping()
    public ResponseEntity<?> addPatientCare(@Valid @RequestBody PatientCareDTO patientCareDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> _errors = new ArrayList<>();
            for (ObjectError allError : result.getAllErrors()) {
                _errors.add(allError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(new MessageResponse(String.format("%s", _errors)));
        } else {
        PatientCareDTO _patientCareDTO = patientCareService.addManagedPatientCare(patientCareDTO);
        //Another way to response
        return ResponseEntity.ok(new MessageResponse(
                "Patient care was successfully added", _patientCareDTO));
        }
    }
    
    //Update the patient care
    @PutMapping()
    public ResponseEntity<?> updatePatientCare(@RequestBody @Valid PatientCare pc, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError allError : result.getAllErrors()) {
                errors.add(allError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(new MessageResponse(String.format("%s", errors)));
        } else {
            PatientCareDTO _patientCareDTO = patientCareService.updatePatientCare(pc);
            return ResponseEntity.ok(new MessageResponse(
                    "Patient care was successfully updated", _patientCareDTO));
        }        
    }
    
    //Deletes a patient care
    //@param Integer id
    @DeleteMapping(path = "/{patientCareId}")
    //revisar si esta intentando eliminar un paciente que se vacunado
    public ResponseEntity<?> deletePatientCare(@PathVariable("patientCareId") Integer id) {
        patientCareService.deletePatienCare(id);
        //Another way to response
        return ResponseEntity.ok(new MessageResponse(
                "Patient care was successfully deleted"));
    }
}
