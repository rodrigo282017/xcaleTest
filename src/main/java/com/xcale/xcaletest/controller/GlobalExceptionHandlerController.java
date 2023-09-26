package com.xcale.xcaletest.controller;

import com.xcale.xcaletest.exception.EntityNotFoundException;
import com.xcale.xcaletest.exception.ValidationException;
import com.xcale.xcaletest.model.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controller advice to handle global exceptions and provide consistent error responses.
 * This controller advice intercepts specific exceptions and maps them to appropriate HTTP error responses,
 * enhancing the API's error handling and consistency.
 * <p>
 * Exception Handling:
 * - {@link ValidationException}: Handles validation-related errors and returns a Bad Request (400) response.
 * - {@link EntityNotFoundException}: Handles entity not found errors and returns a Not Found (404) response.
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandlerController {
    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(ValidationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getCode(), exception.getMessage()));
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityError(EntityNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getCode(), exception.getMessage()));
    }
}