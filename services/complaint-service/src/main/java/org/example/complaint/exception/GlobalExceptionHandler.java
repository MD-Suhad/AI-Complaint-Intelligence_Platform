package org.example.complaint.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ComplaintNotFoundException.class)
    public ApiError handleComplaintNotFound(ComplaintNotFoundException ex) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Instant.now());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ApiError(HttpStatus.BAD_REQUEST.value(), "Validation failed", Instant.now(), errors);
    }

    @ExceptionHandler(Exception.class)
    public ApiError handleGenericException(Exception ex) {
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), Instant.now());
    }

    public record ApiError(int status, String message, Instant timestamp, Map<String, String> errors) {
        public ApiError(int status, String message, Instant timestamp) {
            this(status, message, timestamp, null);
        }
    }
}
