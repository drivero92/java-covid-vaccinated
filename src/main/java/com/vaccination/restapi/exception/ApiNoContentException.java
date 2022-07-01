/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.exception;

/**
 *
 * @author daniel
 */
public class ApiNoContentException extends RuntimeException{

    public ApiNoContentException(String message) {
        super(message);
    }

    public ApiNoContentException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
