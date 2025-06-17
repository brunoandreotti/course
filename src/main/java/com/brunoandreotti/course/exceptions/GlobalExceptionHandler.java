package com.brunoandreotti.course.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
        log.error("NotFoundException message: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorResponse> MethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation error", errors);
        log.error("MethodArgumentNotValidException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> errors = new HashMap<>();

        if (ex.getCause() instanceof InvalidFormatException invalidFormatEx
                && invalidFormatEx.getTargetType().isEnum()) {
            String fieldName = invalidFormatEx.getPath().getLast().getFieldName();
            String errorMessage = ex.getMessage();
            errors.put(fieldName, errorMessage);
        }

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Invalid enum value", errors);
        log.error("HttpMessageNotReadableException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponse);
    }

    @ExceptionHandler(SubscriptionAlreadyExistsException.class)
    private ResponseEntity<ErrorResponse> handleSubscriptionAlreadyExistsException(SubscriptionAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), null);
        log.error("SubscriptionAlreadyExistsException message: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(BlockedStatusException.class)
    private ResponseEntity<ErrorResponse> handleBlockedStatusException(BlockedStatusException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), null);
        log.error("BlockedStatusException message: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(ClientErrorException.class)
    private ResponseEntity<ErrorResponse> handleClientErrorException(ClientErrorException ex) {
        int httpStatus = ex.getStatus() != null ? ex.getStatus() : 400;
        ErrorResponse errorResponse = new ErrorResponse(httpStatus, ex.getMessage(), null);
        log.error("ClientErrorException message: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.valueOf(httpStatus)).body(errorResponse);
    }
}
