/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.services;

import com.vaccination.restapi.exception.ApiNoContentException;
import com.vaccination.restapi.exception.ApiNotFoundException;
import com.vaccination.restapi.models.Patient;
import com.vaccination.restapi.repository.PatientRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author daniel
 */
@Service
public class PatientService {
    
    @Autowired
    PatientRepository patientRepository;
    
    //Returns a list of patient from repository
    public List<Patient> getPatientsVaccinated() {
        List<Patient> _patientList = patientRepository.findAll();
        if (_patientList.isEmpty()) {
            throw new ApiNoContentException(
                    "The patient list has no content");
        } else {
            return _patientList;
        }        
    }
    
    //Returns a patient from repository
    public Patient getPatient(Integer id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException(
                        "The id patient: "+id+" is not found"));
    }
    
    //Adds a new patient and return with its own id
    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }
    
    //Returns a update patient
    public Patient updatePatient(Patient newPatient) {
        if (patientRepository.existsById(newPatient.getId())) {
            return patientRepository.save(newPatient);
        } else {
            throw new ApiNotFoundException(
                    "It does not exist the patient with id: "+ newPatient.getId());
        }
    }
    
    //Deletes the patient by id
    public void deletePatient(Integer id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
        } else {
            throw new ApiNotFoundException(
                    "It does not exist the patient with id: "+ id);
        }        
    }
}