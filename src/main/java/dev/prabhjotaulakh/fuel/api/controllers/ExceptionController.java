package dev.prabhjotaulakh.fuel.api.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.prabhjotaulakh.fuel.api.data.ErrorResponse;
import dev.prabhjotaulakh.fuel.api.data.ValidationErrorResponse;
import dev.prabhjotaulakh.fuel.api.exceptions.DuplicateCredentialsException;
import dev.prabhjotaulakh.fuel.api.exceptions.UserAlreadyExistsException;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        var validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setMessage("Some validation error(s) occured");
        validationErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        validationErrorResponse.setLocalDateTime(LocalDateTime.now());
        validationErrorResponse.setValidationErrors(new ArrayList<>());
        
        for (ObjectError error : e.getAllErrors()) {
            validationErrorResponse.getValidationErrors().add(error.getDefaultMessage());
        }
        
        return ResponseEntity.badRequest().body(validationErrorResponse);
    }

    @ExceptionHandler(DuplicateCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleDuplicateCredsException(DuplicateCredentialsException e) {
        return badRequest(e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return badRequest(e.getMessage());
    }

    private ResponseEntity<ErrorResponse> badRequest(String message) {
        var errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setLocalDateTime(LocalDateTime.now());
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
}