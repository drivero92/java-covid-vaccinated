/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.controllers;

import com.vaccination.restapi.dtos.FullVaccineDTO;
import com.vaccination.restapi.models.Vaccine;
import com.vaccination.restapi.models.FullVaccine;
import com.vaccination.restapi.payload.response.MessageResponse;
import com.vaccination.restapi.services.FullVaccineService;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author daniel
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("full_vaccines")
public class FullVaccineController {
    @Autowired
    FullVaccineService fullVaccineService;
    
    //Returns a full vaccine list   
    @GetMapping("/list")
    public ResponseEntity<List<FullVaccineDTO>> getFullVaccines() {
        List<FullVaccineDTO> _fullVaccinesDTO = fullVaccineService.getFullVaccines();
        return new ResponseEntity<>(_fullVaccinesDTO, HttpStatus.OK);
    }
    
    //Returns a full vaccine from service
    @GetMapping("/{id}")
    public ResponseEntity<FullVaccineDTO> getFullVaccine(@PathVariable Integer id) {
        FullVaccineDTO _fullVaccineDTO = fullVaccineService.getFullVaccine(id);        
        return new ResponseEntity<>(_fullVaccineDTO,HttpStatus.OK);
    }
    
    //Returns a compatible vaccines of a vaccine from service  
    @GetMapping("/{id}/vaccines")
    public ResponseEntity<Collection<Vaccine>> getVaccineList(@PathVariable Integer id) {
        FullVaccineDTO _fullVaccineDTO = fullVaccineService.getFullVaccine(id);        
        return new ResponseEntity<>(_fullVaccineDTO.getVaccines(),HttpStatus.OK);
    }
    
    //Returns a compatible vaccine IDs of a vaccine from service
    @GetMapping("/{id}/vaccinesIds")
    public ResponseEntity<Collection<Vaccine>> getVaccinesList(@PathVariable Integer id) {
        return new ResponseEntity<>(fullVaccineService.getVaccines(id),HttpStatus.OK);
    }
    
    //Adds a new full vaccine
    @PostMapping()
    public ResponseEntity<?> addFullVaccine(@RequestBody FullVaccineDTO fullVaccineDTO) {
        FullVaccine _fullVaccine = fullVaccineService.addFullVaccine(fullVaccineDTO);
        return ResponseEntity.ok(new MessageResponse(
            "Vaccine was successfully added"));
    }
    
    //Updates a full vaccine
    @PutMapping()
    public ResponseEntity<?> updateFullVaccine(@RequestBody FullVaccineDTO fullVaccineDTO) {
        FullVaccine _fullVaccine = fullVaccineService.updateFullVaccine(fullVaccineDTO);
            return ResponseEntity.ok(new MessageResponse(
                    "Vaccine was successfully updated"));
    }
    
    //Deletes a full vaccine
    //@param Integer id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFullVaccine(@PathVariable Integer id) {
        fullVaccineService.deleteFullVaccine(id);
        return ResponseEntity.ok(new MessageResponse(
                "Vaccine was successfully deleted"));
    }
    
}
