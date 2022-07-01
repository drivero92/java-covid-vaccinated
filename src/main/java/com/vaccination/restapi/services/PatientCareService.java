/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.services;

import com.vaccination.restapi.exception.ApiNoContentException;
import com.vaccination.restapi.exception.ApiNotFoundException;
import com.vaccination.restapi.exception.ApiRequestException;
import com.vaccination.restapi.models.Vaccine;
import com.vaccination.restapi.repository.PatientCareRepository;
import com.vaccination.restapi.models.PatientCare;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author daniel
 */
@Service
public class PatientCareService{
    
    @Autowired
    PatientCareRepository patientCareRepository;
    
    @Autowired
    VaccineService vacccineService;
    
    //Returns a patients care list from repository
    public List<PatientCare> getPatientCares() {
        List<PatientCare> _patientCareList = patientCareRepository.findAll();
        if (_patientCareList.isEmpty()) {
            throw new ApiNoContentException("The patient care list has no content");
        } else {
            return _patientCareList;
        }
    }
    
    //Returns a patient care by id from repository
    public PatientCare getPatientCare(Integer id) {
        return patientCareRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException(
                        "The patient care "+id+" is not found"));
    }
    
    //Returns a list of patient cares (PCs) by the patient id and the doses
    public List<PatientCare> getPCsByIdPatient(Integer id) {
        List<PatientCare> _patientCare = patientCareRepository.findPCsByIdPatient(id);
        if (_patientCare.isEmpty()) {
            throw new ApiNoContentException(
                    "The list of patient cares of the patient id: "+ id +" has no content");
        } else {
            return _patientCare;
        }
    }
    
    //Returns a patient care by the patient id
    public PatientCare getPatientCareByIdPatient(Integer id) {
        return patientCareRepository.findByIdPatient(id)
                .orElse(null);
    }
    
    //Returns a patient care by the vaccine id
    public List<PatientCare> getPCsByIdVaccine(Integer id) {
        List<PatientCare> _patientCare = patientCareRepository.findPCsByIdVaccine(id);
        if (_patientCare.isEmpty()) {
            throw new ApiNoContentException(
                    "It doesnot exist the patient cares list with vaccine id: "+ id);
        } else {
            return _patientCare;
        }
    }
    
    //
    public List<PatientCare> getPCsByDose(Byte dose) {
        List<PatientCare> _patientCare = patientCareRepository.findPCsByDose(dose);
        if (_patientCare.isEmpty()) {
            throw new ApiNoContentException(
                    "It doesnot exist the patient cares list with dose: "+ dose);
        } else {
            return _patientCare;
        }
    }
    
    //Update a patient's care and return it
    public PatientCare updatePatientCare(PatientCare pc) {
        if (patientCareRepository.existsById(pc.getId())) {
            return patientCareRepository.save(pc);
        } else {
            throw new ApiNotFoundException(
                    "It does not exist the patient care with id: "+ pc.getId());
        }
    }

    public PatientCare addPatientCare(PatientCare pc) {
        return patientCareRepository.save(pc);
    }
    
    public PatientCare addManagedPatientCare(PatientCare pc) {        
        PatientCare _pc = null;
        LocalDate comeBackDate = null;
        Vaccine _vaccine = vacccineService.getVaccine(pc.getIdVaccine());
        PatientCare _patientCare = this.getPatientCareByIdPatient(pc.getIdPatient());
        if(_patientCare!=null) {
        comeBackDate = LocalDate.of(_patientCare.getDoseDate().getYear(),
                                                      _patientCare.getDoseDate().getMonth(),
                                                      _patientCare.getDoseDate().getDayOfMonth()
                                            ).plusDays(_vaccine.getDays());
        }
        //Checks if is the first time for vaccinated
        if(pc.getDose() != 1) {
            //Checks if the patient vaccinated has the complete dose
            if (!_patientCare.isCompleteDose()) {
                //Check if the patient has taken enough rest
                if(pc.getDoseDate().isAfter(comeBackDate)) {
                    //Checks if is the same vaccine brand
                    if(_patientCare.getVaccine().getName() == null ? _vaccine.getName() == null : 
                            _patientCare.getVaccine().getName().equals(_vaccine.getName())) {
                        if(pc.getDose().equals(_vaccine.getCompleteDose())) {
                            pc.setCompleteDose(true);
                        }
                        vacccineService.reduceQuantity(_vaccine);
                        _pc = addPatientCare(pc);
                    } else {
                        throw new ApiRequestException(
                                "No es compatible con la primera vacuna: "+_patientCare.getVaccine().getName());
                    }
                } else {
                    throw new ApiRequestException(
                            "El paciente "+_patientCare.getPatient().getName()+" le faltan días de descanso para la siguiente dosis");
                }
            } else {
                throw new ApiRequestException("La dosis de la vacuna "+_patientCare.getVaccine().getName()+" esta completa");
            }            
        } else {
            if(_patientCare != null) {
                if(pc.getDose()==1 && _patientCare.getIdVaccine()==pc.getIdVaccine()) {
                    throw new ApiRequestException("El paciente ya se vacuno con la primera dosis");
                } else {
                    if (_patientCare.isCompleteDose()) {
                        if(compatibleVaccines(pc.getIdVaccine(),_patientCare.getVaccine())) {
                            if(pc.getDoseDate().isAfter(comeBackDate)) {
                                pc.setCompleteDose(true);
                                vacccineService.reduceQuantity(_vaccine);
                                _pc = addPatientCare(pc);
                            } else {
                                throw new ApiRequestException(
                                        "El paciente "+_patientCare.getPatient().getName()+
                                                " le faltan días de descanso para la siguiente dosis de refuerzo");
                            }
                        } else {
                            throw new ApiRequestException("No son compatibles las vacunas");
                        }                        
                    } else {
                        throw new ApiRequestException("No tiene la dosis completa");
                    }
                }
            } else {
                vacccineService.reduceQuantity(_vaccine);
                _pc = addPatientCare(pc);
            }                
        }
        return _pc;
    }
    
    //Deletes a patient care by id from repository
    public void deletePatienCare(Integer id) {
        if (patientCareRepository.existsById(id)) {
            patientCareRepository.deleteById(id);
        } else {
            throw new ApiNotFoundException(
                    "It does not exist the patient care with id: "+ id);
        }
    }
    
    public boolean compatibleVaccines(Integer vaccine1, Vaccine vaccine2) {
        boolean resp = false;
        //First vaccine Sputnik: 6 and booster dose is AstraZeneca: 5
        if (vaccine1 == 5 && vaccine2.getId() == 6) {
            resp = true;
        }
        //First vaccine AstraZeneca: 5 and booster dose is Moderna: 1
        if (vaccine1 == 1 && vaccine2.getId() == 5) {
            resp = true;
        }
        //First vaccine Sinopharm: 7 and booster dose is Pfizer: 2
        if (vaccine1 == 2 && vaccine2.getId() == 7) {
            resp = true;
        }
        return resp;
    }
}
