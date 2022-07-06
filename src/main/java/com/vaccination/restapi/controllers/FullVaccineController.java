/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.controllers;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author daniel
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("full_vaccine")
public class FullVaccineController {
    @Autowired
    FullVaccineService vaccineListService;
    
    @GetMapping("/list")
    public ResponseEntity<List<FullVaccine>> getFullVaccines() {
        List<FullVaccine> _fullVaccine = vaccineListService.getFullVaccines();
        return new ResponseEntity<>(_fullVaccine, HttpStatus.OK);
    }
    
    @GetMapping("/get_vaccines/{id}")
    public ResponseEntity<Collection<Vaccine>> getVaccineList(@PathVariable Integer id) {
        FullVaccine _fullVaccine = vaccineListService.getFullVaccine(id);
        
        return new ResponseEntity<>(_fullVaccine.getVaccines(),HttpStatus.OK);
    }
    
    @PostMapping("/save")
    public FullVaccine addFullVaccine(FullVaccine fullVaccine) {
        return vaccineListService.addFullVaccine(fullVaccine);
    }
    
    @PutMapping("/update")
    public FullVaccine updateFullVaccine(FullVaccine fullVaccine) {
        return vaccineListService.updateFullVaccine(fullVaccine);
    }
}
