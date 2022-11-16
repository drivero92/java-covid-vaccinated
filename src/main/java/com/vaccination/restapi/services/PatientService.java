/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.services;

import com.vaccination.restapi.exception.ApiNoContentException;
import com.vaccination.restapi.exception.ApiNotFoundException;
import com.vaccination.restapi.exception.ApiRequestException;
import com.vaccination.restapi.models.Patient;
import com.vaccination.restapi.repository.PatientCareRepository;
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
    
    @Autowired
    PatientCareRepository patientCareRepository;
    
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
                        "Patient id : "+id+" is not found"));
    }
    
    //Adds a new patient and return with its own id
    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }
    
    //Returns a update patient
    public Patient updatePatient(Patient patient) {
        if (patient.getId() != null) {
            if (patientRepository.existsById(patient.getId())) {
                return patientRepository.save(patient);
            } else {
                throw new ApiNotFoundException(
                        "It does not exist the patient with id: " + patient.getId());
            }
        } else {
            throw new ApiRequestException(
                    "The given patient id must not be null");
        }
    }
    
    //Deletes the patient by id
    public void deletePatient(Integer id) {
        if (patientRepository.existsById(id)) {
            if (patientCareRepository.existsByPatientId(id)) {
                patientCareRepository.removePatientCaresByPatientId(id);
            }
            patientRepository.deleteById(id);
        } else {
            throw new ApiNotFoundException(
                    "It does not exist the patient with id: "+ id);
        }
    }
}