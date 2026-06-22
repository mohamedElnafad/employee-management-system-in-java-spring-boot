package com.quickStart.quickStart.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponse<?>> handleValidationErrors(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> new GlobalResponse.Errors(e.getField() + ": " + e.getDefaultMessage()))
                .toList();
        return new ResponseEntity<>(new GlobalResponse<>(errors), HttpStatus.resolve(ex.getStatusCode().value()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<GlobalResponse<?>> handleValidationErrors(NoResourceFoundException ex) {
        var errors = List.of(new GlobalResponse.Errors("Resource Not Found"));
        return new ResponseEntity<>(new GlobalResponse<>(errors), HttpStatus.resolve(ex.getStatusCode().value()));
    }

    @ExceptionHandler(CustomeExceptionHandler.class)
    public ResponseEntity<GlobalResponse<?>> handleCustomErrors(CustomeExceptionHandler ex) {
        var errors = List.of(new GlobalResponse.Errors(ex.getMessage()));
        return new ResponseEntity<>(new GlobalResponse<>(errors), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GlobalResponse<?>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = ex.getMostSpecificCause().getMessage();
        var errors = List.of(new GlobalResponse.Errors(message));
        return new ResponseEntity<>(new GlobalResponse<>(errors), HttpStatus.CONFLICT);
    }

    // for duplicated username
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GlobalResponse<?>> handleIllegalArgument(IllegalArgumentException ex) {
        var errors = List.of(new GlobalResponse.Errors(ex.getMessage()));
        return new ResponseEntity<>(new GlobalResponse<>(errors), HttpStatus.CONFLICT);
    }

}
