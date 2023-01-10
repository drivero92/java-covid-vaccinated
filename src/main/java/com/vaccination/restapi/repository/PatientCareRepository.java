/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vaccination.restapi.repository;

import com.vaccination.restapi.models.PatientCare;
import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author daniel
 */
@Repository
public interface PatientCareRepository extends JpaRepository<PatientCare, Integer>{
    @Query(value =  "SELECT * "
                    + "FROM patient_vaccine "
                    + "WHERE patient_id=:id "
                    + "AND patient_care_id=(SELECT MAX(patient_care_id) FROM patient_vaccine WHERE patient_id=:id)", nativeQuery = true)
    Optional<PatientCare> findByPatientId(@Param("id") Integer id);
    
    @Query(value =  "SELECT * "
                    + "FROM patient_vaccine "
                    + "WHERE patient_id=:id", nativeQuery = true)
    List<PatientCare> findPCsByPatientId(@Param("id") Integer id);
    
    @Query(value =  "SELECT * "
                    + "FROM patient_vaccine "
                    + "WHERE vaccine_id=:id "
                    + "ORDER BY patient_id ASC", nativeQuery = true)
    List<PatientCare> findPCsByVaccineId(@Param("id") Integer id);
    
    @Query(value =  "SELECT * "
                    + "FROM patient_vaccine "
                    + "WHERE dose=:doses ", nativeQuery = true)
    List<PatientCare> findPCsByDose(@Param("doses") Byte dose);
    
    @Query(value =  "SELECT * "
                    + "FROM patient_vaccine "
                    + "WHERE (:id IS NULL OR vaccine_id=:id) "
                    + "AND (:doses IS NULL OR dose=:doses) "
                    + "AND (DATE(:doseDate) IS NULL OR dose_date=:doseDate) "
                    + "ORDER BY patient_id ASC", nativeQuery = true)
    List<PatientCare> findPCsByVaccineIdAndDoseAndDoseDate(
            @Param("id") Integer id, 
            @Param("doses") Byte dose,
            @Param("doseDate") LocalDate doseDate);
    
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value =  "DELETE FROM patient_vaccine "
                    + "WHERE vaccine_id=:id", nativeQuery = true)
    void removePatientCaresByVaccineId(Integer id);
    
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM patient_vaccine "
                    + "WHERE patient_id=:id", nativeQuery = true)
    void removePatientCaresByPatientId(Integer id);
    
    boolean existsByPatientId(Integer id);
    boolean existsByVaccineId(Integer id);
}
