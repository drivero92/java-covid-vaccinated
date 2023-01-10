/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.services;

import com.vaccination.restapi.exception.ApiNoContentException;
import com.vaccination.restapi.exception.ApiNotFoundException;
import com.vaccination.restapi.exception.ApiRequestException;
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
    
    //Adds the vaccine to the repository and returns the vaccine with its own id
    public Vaccine addVaccine(Vaccine vaccine) {
        return vaccineRepository.save(vaccine);
    }
    
    //This will update the vaccine properties but not the id
    public Vaccine updateVaccine(Vaccine vaccine) {
        if (vaccine.getId() != null) {
            if (vaccineRepository.existsById(vaccine.getId())) {
                return vaccineRepository.save(vaccine);
            } else {
                throw new ApiNotFoundException(
                        "It does not exist the vaccine: " + vaccine.getId());
            }
        } else {
            throw new ApiRequestException(
                    "The given vaccine id must not be null");
        }
    }
    
    //Delete the vaccine by id
    public void deleteVaccine(Integer id) {
        if (vaccineRepository.existsById(id)) {
            try {
                vaccineRepository.deleteById(id);
            } catch (RuntimeException e) {
                throw new ApiRequestException(
                        "There is a reference to this in another part");
            }
            
        } else {
            throw new ApiNotFoundException(
                    "It does not exist the vaccine with id: "+ id);
        }        
    }
    
    //Reduces the amount of the vaccine by one unit
    public void reduceQuantity(Integer vaccineId, Integer quantity) {
        if (quantity>0) {
            quantity--;
            vaccineRepository.reduceVaccineQuantityById(vaccineId, quantity);
        } else {
            throw new ApiRequestException("Vaccines sold out");
        }
    }
}
