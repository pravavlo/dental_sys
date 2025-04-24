package edu.miu.cs489.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler({ResourceNotFoundException.class, NoSuchElementException.class})
public ResponseEntity<ApiError> handleResourceNotFound(
        Exception e, HttpServletRequest request) {
    ApiError apiError = new ApiError(
            e.getMessage(),
            request.getRequestURI(),
            HttpStatus.NOT_FOUND.value(),
            Instant.now().toEpochMilli() // Use milliseconds for consistency
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
}

@ExceptionHandler(AppointmentConflictException.class)
public ResponseEntity<ApiError> handleAppointmentConflict(
        AppointmentConflictException e, HttpServletRequest request) {
    ApiError apiError = new ApiError(
            e.getMessage(),
            request.getRequestURI(),
            HttpStatus.CONFLICT.value(),
            Instant.now().toEpochMilli()
    );
    return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
}

@ExceptionHandler(InvalidOperationException.class)
public ResponseEntity<ApiError> handleInvalidOperation(
        InvalidOperationException e, HttpServletRequest request) {
    ApiError apiError = new ApiError(
            e.getMessage(),
            request.getRequestURI(),
            HttpStatus.BAD_REQUEST.value(),
            Instant.now().toEpochMilli()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
}

@ExceptionHandler(DataValidationException.class)
public ResponseEntity<ApiError> handleDataValidationException(
        DataValidationException e, HttpServletRequest request) {
    ApiError apiError = new ApiError(
            e.getMessage(),
            request.getRequestURI(),
            HttpStatus.BAD_REQUEST.value(),
            Instant.now().toEpochMilli()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
}

@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ApiError> handleValidationErrors(
        MethodArgumentNotValidException e, HttpServletRequest request) {
    String errorMessage = e.getBindingResult().getFieldErrors()
                           .stream()
                           .map(error -> error.getField() + ": " + error.getDefaultMessage())
                           .reduce("", (acc, message) -> acc + message + "; ");
    ApiError apiError = new ApiError(
            errorMessage.trim(),
            request.getRequestURI(),
            HttpStatus.BAD_REQUEST.value(),
            Instant.now().toEpochMilli()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
}


@ExceptionHandler(Exception.class)
public ResponseEntity<ApiError> handleGenericException(
        Exception e, HttpServletRequest request) {
    ApiError apiError = new ApiError(
            "An unexpected error occurred: " + e.getMessage(), // More generic message
            request.getRequestURI(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            Instant.now().toEpochMilli()
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
}
}
