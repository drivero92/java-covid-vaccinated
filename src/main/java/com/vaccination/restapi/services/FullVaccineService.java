/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.services;

import com.vaccination.restapi.exception.ApiNotFoundException;
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
    
    public List<FullVaccine> getFullVaccines() {
        return fullVaccineRepository.findAll();
    }
    
    public FullVaccine getFullVaccine(Integer id) {
        return fullVaccineRepository.findById(id).get();
    }
    
    public FullVaccine addFullVaccine(FullVaccine fullVaccine) {
        FullVaccine _fullVaccine = new FullVaccine();
        Vaccine _vaccine = fullVaccine.getVaccine();
        if (_vaccine != null ) {
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
        if (fullVaccine.getId() != null) {
            _fullVaccine.setId(fullVaccine.getId());
        }
        return fullVaccineRepository.save(_fullVaccine);
    }
    
    public FullVaccine updateFullVaccine(FullVaccine fullVaccine) {
        if (fullVaccineRepository.existsById(fullVaccine.getId())) {
            Vaccine _vaccine = vaccineService.updateVaccine(fullVaccine.getVaccine());
            fullVaccine.setVaccine(_vaccine);
            return addFullVaccine(fullVaccine);
        } else {
            throw new ApiNotFoundException(
                    "It does not exist vaccine with id: "+ fullVaccine.getId());
        }
    }
    
    public void deleteFullVaccine(Integer id) {
        FullVaccine _fullVaccine = new FullVaccine();
        if (fullVaccineRepository.existsById(id)) {
            _fullVaccine = getFullVaccine(id);
            _fullVaccine.getVaccines().clear();
            fullVaccineRepository.deleteById(id);
        } else {
            throw new ApiNotFoundException(
                    "It does not exist vaccine with id: "+ id);
        }
    }
}
