/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.services;

import com.vaccination.restapi.dtos.FullVaccineDTO;
import com.vaccination.restapi.exception.ApiNoContentException;
import com.vaccination.restapi.exception.ApiNotFoundException;
import com.vaccination.restapi.exception.ApiRequestException;
import com.vaccination.restapi.mappers.VaccineConverter;
import com.vaccination.restapi.models.FullVaccine;
import com.vaccination.restapi.models.Vaccine;

import java.util.List;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vaccination.restapi.repository.FullVaccineRepository;

/**
 *
 * @author daniel
 */
@Service
public class FullVaccineService {

    @Autowired
    FullVaccineRepository fullVaccineRepository;

    @Autowired
    VaccineService vaccineService;
    
    @Autowired
    PatientCareService patientCareService;
    
    @Autowired
    private Validator validator;
    
    private VaccineConverter vaccineConverter;
    FullVaccineService (VaccineConverter vaccineConverter) {
        this.vaccineConverter = vaccineConverter;
    }
  
    public List<FullVaccineDTO> getFullVaccines() {
        List<FullVaccine> _fullVaccines = fullVaccineRepository.findAll();
        if (_fullVaccines.isEmpty()) {
            throw new ApiNoContentException(
                    "The list of vaccines has no content");
        } else {
            return vaccineConverter.entitiesToDTOs(_fullVaccines);
        }
    }
   
    public FullVaccineDTO getFullVaccine(Integer id) {
        FullVaccine _fullVaccine = fullVaccineRepository.findById(id)
                                                        .orElseThrow(() -> new ApiNotFoundException(
                                                        "Vaccine id: "+id+" is not found"));
        return vaccineConverter.entityToDTO(_fullVaccine);
    }

    public FullVaccine addFullVaccine(FullVaccineDTO fullVaccineDTO) {
        FullVaccine fullVaccine = vaccineConverter.dtoToEntity(fullVaccineDTO);
        FullVaccine _fullVaccine = new FullVaccine();
        Vaccine _vaccine = fullVaccine.getVaccine();
        Set<ConstraintViolation<Vaccine>> violations = validator.validate(_vaccine);
        
        addCompatibleVaccines(fullVaccine,_fullVaccine);
        if (violations.isEmpty()) {
            _vaccine = vaccineService.addVaccine(_vaccine);
            _fullVaccine.setVaccine(_vaccine);
            return fullVaccineRepository.save(_fullVaccine);
        } else {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Vaccine> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
                sb.append(" ");
            }
            throw new ApiRequestException("Error occurred: " + sb.toString());
        }
    }

    public FullVaccine updateFullVaccine(FullVaccineDTO fullVaccineDTO) {
        FullVaccine fullVaccine = vaccineConverter.dtoToEntity(fullVaccineDTO);
        FullVaccine _fullVaccine = new FullVaccine();
        Set<Vaccine> vaccines = new HashSet<>();
        if (fullVaccine.getId() != null) {
            if (fullVaccineRepository.existsById(fullVaccine.getId())) {
                Vaccine _vaccine = vaccineService.updateVaccine(fullVaccine.getVaccine()); 
                
                _fullVaccine.setId(fullVaccine.getId());
                _fullVaccine.setVaccineId(fullVaccine.getVaccineId());
                vaccines.addAll(
                        fullVaccineRepository.findVaccinesByFullVaccineId(fullVaccine.getId())
                                .stream()
                                .collect(Collectors.toSet()));
                if (fullVaccine.getVaccines().size() < vaccines.size()) {
                    Integer _vaccineArraySize = Math.abs(fullVaccine.getVaccines().size() - vaccines.size());
                    fullVaccineRepository.removeRowsByRequiredAmountVaccines(fullVaccine.getId(), _vaccineArraySize + 1);
                }
                addCompatibleVaccines(fullVaccine,_fullVaccine);
                _fullVaccine.setVaccine(_vaccine);
                return fullVaccineRepository.save(_fullVaccine);
            } else {
                throw new ApiNotFoundException(
                        "It does not exist vaccine with id: " + fullVaccine.getId());
            }
        } else {
            throw new ApiRequestException(
                    "The given vaccine id must not be null");
        }
    }
    
    public void addCompatibleVaccines(FullVaccine fullVaccine, FullVaccine _fullVaccine) {
        if (fullVaccine.getVaccines() == null) {
            throw new ApiRequestException(
                    "The array compatible vaccines must not be null");            
        } else {
        _fullVaccine.getVaccines()
                        .addAll(fullVaccine.getVaccines()
                                .stream()
                                .map(vaccine -> {
                                    Vaccine _v = vaccineService.getVaccine(vaccine.getId());
                                    _v.getFullVaccines().clear();
                                    _v.getFullVaccines().add(_fullVaccine);
                                    return _v;
                                })
                                .collect(Collectors.toSet())
                        );
        }
    }

    public void deleteFullVaccine(Integer id) {
        if (fullVaccineRepository.existsById(id)) {            
            FullVaccine _fullVaccine = vaccineConverter.dtoToEntity(getFullVaccine(id));
            _fullVaccine.getVaccines().clear();
            //The following respectively sorted lines remove the full vaccine with all
            //references in other tables such as the patient_vaccine table
            deleteCompatibleVaccines(id);
            patientCareService.deletePatientCareByVaccineId(id);
            fullVaccineRepository.deleteById(id);
            vaccineService.deleteVaccine(id);
        } else {
            throw new ApiNotFoundException(
                    "It does not exist vaccine with id: " + id);
        }
    }
    
    public void deleteCompatibleVaccines(Integer id) {
//        if (fullVaccineRepository.existsByFullandVaccineId(id)) {
            //Maybe is necessary a statement "if" on each one remove
            fullVaccineRepository.removeByFullVaccineId(id);
            fullVaccineRepository.removeByVaccineId(id);
//        }
    }

    public Set<Vaccine> getVaccines(Integer id) {
        return fullVaccineRepository.findVaccinesByFullVaccineId(id);
    }
    
    public boolean compatibleVaccines(Integer vaccineId1, FullVaccineDTO fullVaccineDTO2) {
        if (fullVaccineDTO2.getVaccines().isEmpty()) {
            throw new ApiNotFoundException("There are no compatible vaccines");
        } else {
            Set<Vaccine> _vaccines = fullVaccineDTO2.getVaccines();
            return _vaccines.stream()
                            .anyMatch(vaccine -> Objects.equals(vaccine.getId(), vaccineId1));
        }        
    }
    
    public void reduceQuantity(FullVaccineDTO fullVaccineDTO) {
        vaccineService.reduceQuantity(fullVaccineDTO.getId(), fullVaccineDTO.getQuantity());
    }
}
