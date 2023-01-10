/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.services;

import com.vaccination.restapi.dtos.FullVaccineDTO;
import com.vaccination.restapi.dtos.PatientCareDTO;
import com.vaccination.restapi.exception.ApiNoContentException;
import com.vaccination.restapi.exception.ApiNotFoundException;
import com.vaccination.restapi.exception.ApiRequestException;
import com.vaccination.restapi.mappers.PatientCareConverter;
import com.vaccination.restapi.repository.PatientCareRepository;
import com.vaccination.restapi.models.PatientCare;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    VaccineService vaccineService;
    
    @Lazy
    @Autowired
    FullVaccineService fullVaccineService;
    
    @Autowired
    private PatientCareConverter patientCareConverter;
    
    //Returns a patients care list from repository
    public List<PatientCareDTO> getPatientCares() {
        List<PatientCare> _patientCares = patientCareRepository.findAll();
        if (_patientCares.isEmpty()) {
            throw new ApiNoContentException("The patient care list has no content");
        } else {
            return patientCareConverter.entitiesToDTOs(_patientCares);
        }
    }
    
    //Returns a patient care by id from repository
    public PatientCareDTO getPatientCare(Integer id) {
        PatientCare _patientCare = patientCareRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException(
                        "The patient care id: "+id+" is not found"));
        return patientCareConverter.entityToDTO(_patientCare);
    }
    
    //Returns a list of patient cares (PCs) by the patient id and the doses
    public List<PatientCareDTO> getPCsByPatientId(Integer id) {
        List<PatientCare> _patientCare = patientCareRepository.findPCsByPatientId(id);
        if (_patientCare.isEmpty()) {
            throw new ApiNoContentException(
                    "The list of patient cares of the patient id: "+ id +" has no content");
        } else {
            return patientCareConverter.entitiesToDTOs(_patientCare);
        }
    }
    
    //Returns a patient care by the patient id
    public PatientCareDTO getPatientCareByPatientId(Integer id) {
        PatientCare _patientCare = patientCareRepository.findByPatientId(id)
                .orElse(null);
        return patientCareConverter.entityToDTO(_patientCare);
    }
    
    //Returns a patient care by the vaccine id
    public List<PatientCareDTO> getPCsByVaccineId(Integer id) {
        List<PatientCare> _patientCare = patientCareRepository.findPCsByVaccineId(id);
        if (_patientCare.isEmpty()) {
            throw new ApiNoContentException(
                    "It does not exist the patient cares list with vaccine id: "+ id);
        } else {
            return patientCareConverter.entitiesToDTOs(_patientCare);
        }
    }
    
    //Returns a list of vaccines that have the same number of Doses
    public List<PatientCareDTO> getPCsByDose(Byte dose) {
        List<PatientCare> _patientCare = patientCareRepository.findPCsByDose(dose);
        if (_patientCare.isEmpty()) {
            throw new ApiNoContentException(
                    "It does not exist the patient cares list with dose: "+ dose);
        } else {
            return patientCareConverter.entitiesToDTOs(_patientCare);
        }
    }
    
    //Returns a list of vaccines that have the same number of Doses
    public List<PatientCareDTO> getPCsByVaccineIdAndDoseAndDoseDate(Integer id, Byte dose, LocalDate doseDate) {
        List<PatientCare> _patientCare = patientCareRepository.findPCsByVaccineIdAndDoseAndDoseDate(id, dose, doseDate);
        if (_patientCare.isEmpty()) {
            throw new ApiNoContentException(
                    "It does not exist the patient cares list with dose: "+ dose);
        } else {
            return patientCareConverter.entitiesToDTOs(_patientCare);
        }
    }
    
    //Update a patient's care and return it
    public PatientCareDTO updatePatientCare(PatientCare pc) {
        if (pc.getId() != null) {
            if (patientCareRepository.existsById(pc.getId())) {
                PatientCare _patientCare = patientCareRepository.save(pc);
                return patientCareConverter.entityToDTO(_patientCare);
            } else {
                throw new ApiNotFoundException(
                        "It does not exist the patient care with id: " + pc.getId());
            }
        } else {
            throw new ApiRequestException(
                    "The given patient care id must not be null");
        }
    }

    public PatientCareDTO addPatientCare(PatientCare pc) {
        PatientCare _patientCare = patientCareRepository.save(pc);
        return patientCareConverter.entityToDTO(_patientCare);
    }
    
    public PatientCareDTO addManagedPatientCare(PatientCareDTO patientCareDTO) {
        PatientCare pc = patientCareConverter.dtoToEntity(patientCareDTO);
        PatientCareDTO _pc = null;
        LocalDate comeBackDate = null;
        FullVaccineDTO _fullVaccineDTO = fullVaccineService.getFullVaccine(pc.getVaccineId());
        PatientCareDTO _patientCare = this.getPatientCareByPatientId(pc.getPatientId());
        if(_patientCare!=null) {
        comeBackDate = LocalDate.of(_patientCare.getDoseDate().getYear(),
                                                      _patientCare.getDoseDate().getMonth(),
                                                      _patientCare.getDoseDate().getDayOfMonth()
                                            ).plusDays(_fullVaccineDTO.getRestDays());
        }
        //Checks if he is the first time for vaccinated
        if(pc.getDose() > 1) {
            //Checks if he is an old patient who was vaccinated
            if (_patientCare != null) {
                if (!_patientCare.getDose().equals(pc.getDose())) {
                    //Checks if the patient vaccinated has the complete dose
                    if (!_patientCare.isCompleteDose()) {
                        //Check if the patient has taken enough rest
                        if(pc.getDoseDate().isAfter(comeBackDate)) {
                            //Checks if is the same vaccine brand
                            if(_patientCare.getVaccine().getName() == null ? _fullVaccineDTO.getName() == null : 
                                    _patientCare.getVaccine().getName().equals(_fullVaccineDTO.getName())) {
                                if(pc.getDose().equals(_fullVaccineDTO.getNumberDoses())) {
                                    pc.setCompleteDose(true);
                                }
                                fullVaccineService.reduceQuantity(_fullVaccineDTO);
                                _pc = addPatientCare(pc);
                            } else {
                                throw new ApiRequestException(
                                        "It is not compatible with the first vaccine: "+_patientCare.getVaccine().getName());
                            }
                        } else {
                            throw new ApiRequestException(
                                    "Patient has not been added, needs to spend days off, should return later in "+comeBackDate);
                        }
                    } else {
                        throw new ApiRequestException(
                                "The doses of the "+_patientCare.getVaccine().getName()+" vaccine were complete");
                    }
                } else {
                    throw new ApiRequestException("Patient has already received dose " +_patientCare.getDose());                    
                }
            } else {
                throw new ApiRequestException("The patient did not receive the first dose");
            }
        } else {
            if (_patientCare != null) {
                if (pc.getDose() == 1 && _patientCare.getVaccineId().equals(pc.getVaccineId())) {
                    throw new ApiRequestException("The patient has already been vaccinated with the first dose.");
                } else {
                    if (_patientCare.isCompleteDose()) {
                        if(fullVaccineService.compatibleVaccines(pc.getVaccineId(),_patientCare.getVaccine())) {
                            if (pc.getDoseDate().isAfter(comeBackDate)) {
                                pc.setCompleteDose(true);
                                fullVaccineService.reduceQuantity(_fullVaccineDTO);
                                _pc = addPatientCare(pc);
                            } else {
                                throw new ApiRequestException(
                                        "Patient has not been added, needs to spend the days off"
                                                + " for the booster dose, should return later in " +comeBackDate);
                            }
                        } else {
                            throw new ApiRequestException("Vaccines are not compatible");
                        }                        
                    } else {
                        throw new ApiRequestException("Does not have the full dose");
                    }
                }
            } else {
                fullVaccineService.reduceQuantity(_fullVaccineDTO);
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
    
    public void deletePatientCareByVaccineId(Integer id) {
        //Revisar si es realmente necesario verificar que exista
        if (patientCareRepository.existsByVaccineId(id)) {
            patientCareRepository.removePatientCaresByVaccineId(id);
        }
    }
}
