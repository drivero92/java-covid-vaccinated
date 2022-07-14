/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.controllers;

import com.vaccination.restapi.combinolas.CombinolaVaccine;
import com.vaccination.restapi.models.Vaccine;
import com.vaccination.restapi.models.FullVaccine;
import com.vaccination.restapi.payload.response.MessageResponse;
import com.vaccination.restapi.services.FullVaccineService;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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
    
//    @GetMapping("/list")
//    public ResponseEntity<List<FullVaccine>> getFullVaccines() {
//        List<FullVaccine> _fullVaccine = fullVaccineService.getFullVaccines();
//        return new ResponseEntity<>(_fullVaccine, HttpStatus.OK);
//    }    
    @GetMapping("/list")
    public ResponseEntity<List<CombinolaVaccine>> getFullVaccines() {
        List<CombinolaVaccine> _fullVaccineDTO = fullVaccineService.getFullVaccines();
        return new ResponseEntity<>(_fullVaccineDTO, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CombinolaVaccine> getFullVaccine(@PathVariable Integer id) {
        CombinolaVaccine _fullVaccine = fullVaccineService.getFullVaccine(id);        
        return new ResponseEntity<>(_fullVaccine,HttpStatus.OK);
    }
    
//    @GetMapping("/get_vaccines/{id}")
//    public ResponseEntity<Collection<Vaccine>> getVaccineList(@PathVariable Integer id) {
//        FullVaccine _fullVaccine = fullVaccineService.getFullVaccine(id);        
//        return new ResponseEntity<>(_fullVaccine.getVaccines(),HttpStatus.OK);
//    }    
    @GetMapping("/get_vaccines/{id}")
    public ResponseEntity<Collection<Vaccine>> getVaccineList(@PathVariable Integer id) {
        CombinolaVaccine _fullVaccine = fullVaccineService.getFullVaccine(id);        
        return new ResponseEntity<>(_fullVaccine.getVaccines(),HttpStatus.OK);
    }
    
    @GetMapping("/get_vaccinesIds/{id}")
    public ResponseEntity<Collection<Vaccine>> getVaccinesList(@PathVariable Integer id) {
        return new ResponseEntity<>(fullVaccineService.getVaccines(id),HttpStatus.OK);
    }
    
//    @PostMapping("/save")
//    public ResponseEntity<?> addFullVaccine(@RequestBody FullVaccine fullVaccine) {
//        FullVaccine _fullVaccine = fullVaccineService.addFullVaccine(fullVaccine);
//        return ResponseEntity.ok(new MessageResponse(
//            "Vaccine was successfully added"));
//    }
    @PostMapping()
    public ResponseEntity<?> addFullVaccine(@RequestBody CombinolaVaccine dtoFullVaccine) {
        FullVaccine _fullVaccine = fullVaccineService.addFullVaccine(dtoFullVaccine);
        return ResponseEntity.ok(new MessageResponse(
            "Vaccine was successfully added"));
    }
    
    @PostMapping("/save_compatibles_vaccines")
    public void addVaccineList(Set<Vaccine> vaccines) {
        vaccines.forEach(vaccine -> {
            vaccine.getId();
        });        
    }
    
    @PutMapping()
    public ResponseEntity<?> updateFullVaccine(@RequestBody CombinolaVaccine dtoFullVaccine) {
        FullVaccine _fullVaccine = fullVaccineService.updateFullVaccine(dtoFullVaccine);
            return ResponseEntity.ok(new MessageResponse(
                    "Vaccine was successfully updated"));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFullVaccine(@PathVariable Integer id) {
        fullVaccineService.deleteFullVaccine(id);
        return ResponseEntity.ok(new MessageResponse(
                "Vaccine was successfully deleted"));
    }
            
}
