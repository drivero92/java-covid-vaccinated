/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.exception;

/**
 *
 * @author daniel
 */
//This class is the custom exception
public class ApiRequestException extends RuntimeException {
    
    //These methods is created with Alt+Enter choose "Constructor"

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
