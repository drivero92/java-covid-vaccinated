/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vaccination.restapi.repository;

import com.vaccination.restapi.models.PatientCare;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author daniel
 */
@Repository
public interface PatientCareRepository extends JpaRepository<PatientCare, Integer>{
    @Query(value =  "SELECT * "
                    + "FROM patient_vaccine "
                    + "WHERE fk_patient=:id "
                    + "AND id=(SELECT MAX(id) FROM patient_vaccine WHERE fk_patient=:id)", nativeQuery = true)
    Optional<PatientCare> findByPatientId(@Param("id") Integer id);
    
    @Query(value =  "SELECT * "
                    + "FROM patient_vaccine "
                    + "WHERE fk_patient=:id", nativeQuery = true)
    List<PatientCare> findPCsByPatientId(@Param("id") Integer id);
    
    @Query(value =  "SELECT * "
                    + "FROM patient_vaccine "
                    + "WHERE fk_vaccine=:id "
                    + "ORDER BY fk_patient ASC", nativeQuery = true)
    List<PatientCare> findPCsByVaccineId(@Param("id") Integer id);
    
    @Query(value =  "SELECT * "
                    + "FROM patient_vaccine "
                    + "WHERE dose=:doses ", nativeQuery = true)
    List<PatientCare> findPCsByDose(@Param("doses") Byte dose);
}
