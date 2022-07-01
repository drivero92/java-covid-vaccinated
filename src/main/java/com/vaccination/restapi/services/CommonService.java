/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vaccination.restapi.services;

import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author daniel
 */
public interface CommonService<E> {
    public ArrayList<E> findAll();
    Optional<E> findById(Long id);
    public E save(E entity);
    public E update(E entity);
    public void delete(Long id);
}
