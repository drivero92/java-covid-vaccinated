/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.services;

import com.vaccination.restapi.combinolas.CombinolaVaccine;
import com.vaccination.restapi.exception.ApiNotFoundException;
import com.vaccination.restapi.mappers.VaccineConverter;
import com.vaccination.restapi.models.FullVaccine;
import com.vaccination.restapi.models.Vaccine;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vaccination.restapi.repository.FullVaccineRepository;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    
    private VaccineConverter vaccineConverter;
    FullVaccineService (VaccineConverter vaccineConverter) {
        this.vaccineConverter = vaccineConverter;
    }

//    public List<FullVaccine> getFullVaccines() {
//        return fullVaccineRepository.findAll();
//    }    
    public List<CombinolaVaccine> getFullVaccines() {
        List<FullVaccine> _fullVaccines = fullVaccineRepository.findAll();
        return vaccineConverter.entitiesToDTOs(_fullVaccines);
    }

//    public FullVaccine getFullVaccine(Integer id) {
//        return fullVaccineRepository.findById(id).get();
//    }    
    public CombinolaVaccine getFullVaccine(Integer id) {
        FullVaccine _fullVaccine = fullVaccineRepository.findById(id).get();
        return vaccineConverter.entityToDTO(_fullVaccine);
    }

    public FullVaccine addFullVaccine(CombinolaVaccine dtoFullVaccine) {
        FullVaccine fullVaccine = vaccineConverter.dtoToEntity(dtoFullVaccine);
        FullVaccine _fullVaccine = new FullVaccine();
        Set<Vaccine> vaccines = new HashSet<>();
        Vaccine _vaccine = fullVaccine.getVaccine();
        Integer _vaccineArraySize = 0;

//        if (fullVaccine.getId() != null) {
//            _fullVaccine.setId(fullVaccine.getId());
//            _fullVaccine.setVaccineId(fullVaccine.getVaccineId());
//            vaccines.addAll(
//                    fullVaccineRepository.findVaccinesByFullVaccineId(fullVaccine.getId())
//                            .stream()
//                            .collect(Collectors.toSet()));
//            if (fullVaccine.getVaccines().size() < vaccines.size()) {
//                _vaccineArraySize = Math.abs(fullVaccine.getVaccines().size() - vaccines.size());
//                fullVaccineRepository.removeRowsByRequiredAmountVaccines(fullVaccine.getId(), _vaccineArraySize+1);
//            }
//        }
        if (_vaccine != null) {
            _vaccine = vaccineService.addVaccine(_vaccine);
        } else {
            _vaccine = vaccineService.getVaccine(fullVaccine.getVaccineId());
        }
        _fullVaccine.setVaccine(_vaccine);
        _fullVaccine.getVaccines()
                .addAll(fullVaccine.getVaccines()
                        .stream()
                        .map(vaccine -> {
                            Vaccine _v = vaccineService.getVaccine(vaccine.getId());
                            _v.getFullVaccines().add(_fullVaccine);
                            return _v;
                        })
                        .collect(Collectors.toSet())
                );
        return fullVaccineRepository.save(_fullVaccine);
    }

    public FullVaccine updateFullVaccine(CombinolaVaccine dtoFullVaccine) {
        FullVaccine fullVaccine = vaccineConverter.dtoToEntity(dtoFullVaccine);
        FullVaccine _fullVaccine = new FullVaccine();
        Set<Vaccine> vaccines = new HashSet<>();
        Vaccine _vaccine = fullVaccine.getVaccine();
        Integer _vaccineArraySize = 0;
        
        if (fullVaccineRepository.existsById(fullVaccine.getId())) {
            _vaccine = vaccineService.updateVaccine(fullVaccine.getVaccine());
            
            if (fullVaccine.getId() != null) {
                _fullVaccine.setId(fullVaccine.getId());
                _fullVaccine.setVaccineId(fullVaccine.getVaccineId());
                vaccines.addAll(
                        fullVaccineRepository.findVaccinesByFullVaccineId(fullVaccine.getId())
                                    .stream()
                                    .collect(Collectors.toSet()));
                    if (fullVaccine.getVaccines().size() < vaccines.size()) {
                        _vaccineArraySize = Math.abs(fullVaccine.getVaccines().size() - vaccines.size());
                        fullVaccineRepository.removeRowsByRequiredAmountVaccines(fullVaccine.getId(), _vaccineArraySize+1);
                    }
                }
                _fullVaccine.setVaccine(_vaccine);
                _fullVaccine.getVaccines()
                        .addAll(fullVaccine.getVaccines()
                                .stream()
                                .map(vaccine -> {
                                    Vaccine _v = vaccineService.getVaccine(vaccine.getId());
                                    _v.getFullVaccines().add(_fullVaccine);
                                    return _v;
                                })
                                .collect(Collectors.toSet())
                        );
            
            return fullVaccineRepository.save(_fullVaccine);
        } else {
            throw new ApiNotFoundException(
                    "It does not exist vaccine with id: " + fullVaccine.getId());
        }
    }

    public void deleteFullVaccine(Integer id) {
        FullVaccine _fullVaccine;
        if (fullVaccineRepository.existsById(id)) {            
            _fullVaccine = vaccineConverter.dtoToEntity(getFullVaccine(id));
            _fullVaccine.getVaccines().clear();            
            fullVaccineRepository.deleteById(id);
            //Revisar el borrar con otras relaciones
            vaccineService.deleteVaccine(id);
        } else {
            throw new ApiNotFoundException(
                    "It does not exist vaccine with id: " + id);
        }
    }

    public Set<Vaccine> getVaccines(Integer id) {
        return fullVaccineRepository.findVaccinesByFullVaccineId(id);
    }
}
