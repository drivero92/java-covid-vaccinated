/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vaccination.restapi.repository;

import com.vaccination.restapi.models.FullVaccine;
import com.vaccination.restapi.models.Vaccine;
import java.util.Set;
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
public interface FullVaccineRepository extends JpaRepository<FullVaccine, Integer>{
    @Query(value =  "SELECT vaccine_id "
                    + "FROM compatible_vaccines "
                    + "WHERE full_vaccine_id=:id", nativeQuery = true)
    Set<Vaccine> findVaccinesByFullVaccineId(Integer id);
    
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value =  "WITH rows as (SELECT vaccine_id FROM compatible_vaccines LIMIT :size) "
                    + "DELETE FROM compatible_vaccines "
                    + "WHERE full_vaccine_id=:id AND vaccine_id IN (SELECT vaccine_id FROM rows)", nativeQuery = true)
    void removeRowsByRequiredAmountVaccines(Integer id, @Param("size") Integer size);
    
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value =  "DELETE FROM compatible_vaccines "
                    + "WHERE full_vaccine_id=:id", nativeQuery = true)
    void removeByFullVaccineId(Integer id);
    
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value =  "DELETE FROM compatible_vaccines "
                    + "WHERE vaccine_id=:id", nativeQuery = true)
    void removeByVaccineId(Integer id);
    
    @Transactional
    @Query(value =  "SELECT CASE WHEN EXISTS"
                    + "(SELECT * FROM compatible_vaccines "
                    + "WHERE full_vaccine_id IN "
                    + "(SELECT full_vaccine_id "
                    + "FROM compatible_vaccines "
                    + "WHERE full_vaccine_id='2' "
                    + "OR vaccine_id='2')) "
                    + "THEN 1 ELSE 0 END "
                    + "FROM compatible_vaccines", nativeQuery = true)
    boolean existsByFullandVaccineId(Integer id);
}
