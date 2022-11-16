/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vaccination.restapi.repository;

import com.vaccination.restapi.models.Vaccine;
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
public interface VaccineRepository extends JpaRepository<Vaccine, Integer>{
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value =  "UPDATE vaccines "
                    + "SET quantity = :quantity "
                    + "WHERE vaccine_id = :id", nativeQuery = true)
    void reduceVaccineQuantityById(Integer id, @Param("quantity") Integer q);
}
