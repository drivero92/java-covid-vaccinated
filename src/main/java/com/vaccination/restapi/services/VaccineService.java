/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.services;

import com.vaccination.restapi.combinolas.CombinolaVaccine;
import com.vaccination.restapi.exception.ApiNoContentException;
import com.vaccination.restapi.exception.ApiNotFoundException;
import com.vaccination.restapi.exception.ApiRequestException;
import com.vaccination.restapi.mappers.VaccineConverter;
import com.vaccination.restapi.models.Vaccine;
import com.vaccination.restapi.repository.VaccineRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author daniel
 */
@Service
public class VaccineService {
    
    @Autowired
    VaccineRepository vaccineRepository;
    
    private VaccineConverter vaccineConverter;
    VaccineService (VaccineConverter vaccineConverter) {
        this.vaccineConverter = vaccineConverter;
    }
    
    //Return a list of vaccines
    public List<Vaccine> getVaccines() {
         List<Vaccine> _vaccineList = vaccineRepository.findAll();
         if (_vaccineList.isEmpty()) {
            throw new ApiNoContentException(
                    "The list of vaccines has no content");
        } else {
             return _vaccineList;
        }
    }
    
    //Return the specific vaccine from repository
    public Vaccine getVaccine(Integer id) {
        return vaccineRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException(
                        "Vaccine id : "+id+" is not found"));
    }
//    public CombinolaVaccine getVaccine(Integer id) {
//        Vaccine _vaccine = vaccineRepository.findById(id)
//                .orElseThrow(() -> new ApiNotFoundException(
//                        "Vaccine id : "+id+" is not found"));
//        return vaccineConverter.entityToDTO(_vaccine);
//    }
    
    //Adds the vaccine to the repository and returns the vaccine with its own id
    public Vaccine addVaccine(Vaccine vaccine) {
        return vaccineRepository.save(vaccine);
    }
    
    //This will update the vaccine properties but not the id
    public Vaccine updateVaccine(Vaccine newVaccine) {
        if(vaccineRepository.existsById(newVaccine.getId())) {
            return vaccineRepository.save(newVaccine);
        } else {
            throw new ApiNotFoundException(
                    "It does not exist the vaccine: "+ newVaccine.getId());
        }
        
    }
    
    //Delete the vaccine by id
    public void deleteVaccine(Integer id) {
        if (vaccineRepository.existsById(id)) {
            vaccineRepository.deleteById(id);
        } else {
            throw new ApiNotFoundException(
                    "It does not exist the vaccine with id: "+ id);
        }        
    }
    
    //Reduces the amount of the vaccine by one unit
    public void reduceQuantity(Vaccine v) {
        Integer _quantity = v.getQuantity();
        if (_quantity>0) {
            _quantity--;
            v.setQuantity(_quantity);
            vaccineRepository.save(v);
        } else {
            throw new ApiRequestException("Vaccines sold out");
        }        
    }
}
