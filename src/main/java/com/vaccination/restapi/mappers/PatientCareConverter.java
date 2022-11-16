/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.mappers;

import com.vaccination.restapi.dtos.PatientCareDTO;
import com.vaccination.restapi.models.PatientCare;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

/**
 *
 * @author daniel
 */
@Component
public class PatientCareConverter {
    private static final ModelMapper modelMapper = new ModelMapper();
    
    PatientCareConverter() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.getConfiguration().setSkipNullEnabled(true);
    }    
    
    public List<PatientCareDTO> entitiesToDTOs(List<PatientCare> patientCares) {
        
        return patientCares.stream()
                            .map(patientCare -> modelMapper.typeMap(PatientCare.class, PatientCareDTO.class)
                                                        .addMapping(map -> map.getPatient(), PatientCareDTO::setPatient)
                                                        .addMapping(map -> map.getVaccine(), PatientCareDTO::setVaccine)
                                                        .map(patientCare))
                            .collect(Collectors.toList());
    }
    
    public PatientCareDTO entityToDTO(PatientCare patientCare) {
        return patientCare == null ? null : modelMapper.typeMap(PatientCare.class, PatientCareDTO.class)
                            .addMapping(map -> map.getPatient(), PatientCareDTO::setPatient)
                            .addMapping(map -> map.getVaccine(), PatientCareDTO::setVaccine)
                            .map(patientCare);
    }
    
    public PatientCare dtoToEntity(PatientCareDTO patientCareDTO) {
        return modelMapper.map(patientCareDTO, PatientCare.class);
    }
}
