/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.mappers;

import com.vaccination.restapi.combinolas.CombinolaVaccine;
import com.vaccination.restapi.models.FullVaccine;
import com.vaccination.restapi.models.Vaccine;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author daniel
 */
@SpringBootApplication
public class VaccineConverter {
    private static final ModelMapper modelMapper = new ModelMapper();
    
    public List<CombinolaVaccine> entitiesToDTOs(List<FullVaccine> fullVaccines) {
        return fullVaccines.stream()
                            .map(_fullVaccine -> modelMapper.typeMap(FullVaccine.class, CombinolaVaccine.class)
                                                    .addMapping(m->m.getVaccine(),CombinolaVaccine::setVaccine)
                                                    .addMapping(m->m.getVaccines(),CombinolaVaccine::setVaccines)
                                                    .map(_fullVaccine))
                            .collect(Collectors.toList());
    }
    
    public CombinolaVaccine entityToDTO(FullVaccine fullVaccine) {
        return modelMapper.typeMap(FullVaccine.class, CombinolaVaccine.class)
                            .addMapping(map -> map.getVaccine(),CombinolaVaccine::setVaccine)
                            .addMapping(map -> map.getVaccines(),CombinolaVaccine::setVaccines)
                            .map(fullVaccine);
    }
    
    public FullVaccine dtoToEntity(CombinolaVaccine dtoFullVaccine) {
        Vaccine _vaccine = new Vaccine();
        FullVaccine _fullVaccine = new FullVaccine();
        _fullVaccine.setId(dtoFullVaccine.getId());
        _fullVaccine.setVaccineId(dtoFullVaccine.getId());
        _vaccine.setId(dtoFullVaccine.getId());
        _vaccine.setName(dtoFullVaccine.getName());
        _vaccine.setQuantity(dtoFullVaccine.getQuantity());
        _vaccine.setRestDays(dtoFullVaccine.getRestDays());
        _vaccine.setNumberDoses(dtoFullVaccine.getNumberDoses());
        _fullVaccine.setVaccine(_vaccine);
        _fullVaccine.setVaccines(dtoFullVaccine.getVaccines());
        return _fullVaccine;
    }
}
