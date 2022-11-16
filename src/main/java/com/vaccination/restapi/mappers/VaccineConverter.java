/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.mappers;

import com.vaccination.restapi.dtos.FullVaccineDTO;
import com.vaccination.restapi.models.FullVaccine;
import com.vaccination.restapi.models.Vaccine;

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
public class VaccineConverter {
    private static final ModelMapper modelMapper = new ModelMapper();

    public VaccineConverter() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);        
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.getConfiguration().setSkipNullEnabled(true);
    }
    
    public List<FullVaccineDTO> entitiesToDTOs(List<FullVaccine> fullVaccines) {
        return fullVaccines.stream()
                            .map(fullVaccine -> modelMapper.typeMap(FullVaccine.class, FullVaccineDTO.class)
                                                    .addMapping(map -> map.getVaccine(),FullVaccineDTO::setVaccine)
                                                    .addMapping(map -> map.getVaccines(),FullVaccineDTO::setVaccines)
                                                    .map(fullVaccine))
                            .collect(Collectors.toList());
    }
    
    public FullVaccineDTO entityToDTO(FullVaccine fullVaccine) {
        return  fullVaccine == null ? null : modelMapper.typeMap(FullVaccine.class, FullVaccineDTO.class)
                            .addMapping(map -> map.getVaccine(),FullVaccineDTO::setVaccine)
                            .addMapping(map -> map.getVaccines(),FullVaccineDTO::setVaccines)
                            .map(fullVaccine);
    }
    
    public FullVaccine dtoToEntity(FullVaccineDTO fullVaccineDTO) {
        FullVaccine _fullVaccine = new FullVaccine();
        _fullVaccine.setId(fullVaccineDTO.getId());
        _fullVaccine.setVaccineId(fullVaccineDTO.getId());
        Vaccine _vaccine = modelMapper.map(fullVaccineDTO, Vaccine.class);
        _fullVaccine.setVaccine(_vaccine);
        _fullVaccine.setVaccines(fullVaccineDTO.getVaccines());
        return _fullVaccine;
    }
}
