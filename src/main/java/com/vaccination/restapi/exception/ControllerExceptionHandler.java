/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vaccination.restapi.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.hibernate.exception.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author daniel
 */
@ControllerAdvice//This annotation tells that we multiple exception handles with this class
public class ControllerExceptionHandler {
    
    @ExceptionHandler(value = {ApiRequestException.class})
    //Here it is handle the custom api request exception for spring boot
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;//Keyboard shortcut Alt+Enter choose "Introduce Variable"
        
        // 1. Create the payload containing exception details, this is the format that is expose to the client
        ErrorMessage errorMessage = new ErrorMessage(
                e.getMessage(), badRequest, 
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        // 2. Return response entity
        return new ResponseEntity<>(errorMessage, badRequest);
    }
    
    @ExceptionHandler(value = {ApiNoContentException.class})
    public ResponseEntity<Object> handleApiNoContentException(ApiNoContentException e) {
        HttpStatus noContent = HttpStatus.NO_CONTENT;
        
        ErrorMessage errorMessage = new ErrorMessage(
                e.getMessage(), noContent, 
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(errorMessage, noContent);
    }
    
    @ExceptionHandler(value = {ApiNotFoundException.class})
    public ResponseEntity<Object> handleApiNotFoundException(ApiNotFoundException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        
        ErrorMessage errorMessage = new ErrorMessage(
                e.getMessage(), notFound, 
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(errorMessage, notFound);
    }
}
