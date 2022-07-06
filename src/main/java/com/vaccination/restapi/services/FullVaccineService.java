/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.services;

import com.vaccination.restapi.models.FullVaccine;
import java.util.List;
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
    FullVaccineRepository vaccineListRepository;
    
    public List<FullVaccine> getFullVaccines() {
        return vaccineListRepository.findAll();
    }
    
    public FullVaccine getFullVaccine(Integer id) {
        return vaccineListRepository.findById(id).get();
    }
    
    public FullVaccine addFullVaccine(FullVaccine fullVaccine) {
        return vaccineListRepository.save(fullVaccine);
    }
    
    public FullVaccine updateFullVaccine(FullVaccine fullVaccine) {
        return vaccineListRepository.save(fullVaccine);
    }
}
