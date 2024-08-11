package dev.prabhjotaulakh.fuel.api.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.prabhjotaulakh.fuel.api.data.ErrorResponse;
import dev.prabhjotaulakh.fuel.api.data.ValidationErrorResponse;
import dev.prabhjotaulakh.fuel.api.exceptions.DuplicateCredentialsException;
import dev.prabhjotaulakh.fuel.api.exceptions.InvalidCountryException;
import dev.prabhjotaulakh.fuel.api.exceptions.JwtException;
import dev.prabhjotaulakh.fuel.api.exceptions.LogNotFoundException;
import dev.prabhjotaulakh.fuel.api.exceptions.ResourceNotOwnedByUserException;
import dev.prabhjotaulakh.fuel.api.exceptions.SheetAlreadyExistsForUsernameException;
import dev.prabhjotaulakh.fuel.api.exceptions.SheetNotFoundException;
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

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthenticationException e) {
        return badRequest(e.getMessage());
    }

    @ExceptionHandler(SheetAlreadyExistsForUsernameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleSheetForUsernameException(SheetAlreadyExistsForUsernameException e) {
        return badRequest(e.getMessage());
    }

    @ExceptionHandler(ResourceNotOwnedByUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleResourceNotOwned(ResourceNotOwnedByUserException e) {
        var errorRep = new ErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(errorRep);
    }

    @ExceptionHandler(SheetNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleSheetNotFound(SheetNotFoundException e) {
        return notFound(e.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException e) {
        return ResponseEntity.internalServerError().body(new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidCountryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidCountry(InvalidCountryException e) {
        return badRequest(e.getMessage());
    }

    @ExceptionHandler(LogNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleLogNotFound(LogNotFoundException e) {
        return notFound(e.getMessage());
    }

    private ResponseEntity<ErrorResponse> badRequest(String message) {
        var errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setLocalDateTime(LocalDateTime.now());
        
        return ResponseEntity.badRequest().body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> notFound(String message) {
        var errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setLocalDateTime(LocalDateTime.now());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(errorResponse);
    }
}
