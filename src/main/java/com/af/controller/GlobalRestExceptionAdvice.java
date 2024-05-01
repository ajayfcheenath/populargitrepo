package com.af.controller;

import com.af.exception.RepositoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Global exception handler for handling exceptions thrown by controllers.
 */
@RestControllerAdvice
public class GlobalRestExceptionAdvice {

    /**
     * Handles RepositoryNotFoundException and returns a 404 response with the exception message.
     * @param ex The RepositoryNotFoundException instance.
     * @return ResponseEntity with a 404 status and the exception message in the response body.
     */
    @ExceptionHandler(RepositoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleRepositoryNotFoundException(RepositoryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
