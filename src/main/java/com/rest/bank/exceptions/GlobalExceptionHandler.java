package com.rest.bank.exceptions;

import com.rest.bank.domain.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach( err -> {
            String fieldName = ((FieldError) err).getField();
            String errorMessage = err.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        String errorMessage = "Validation failed for fields: " + errors;
        ErrorResponse errorResponse = new ErrorResponse(
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),webRequest.getRequestURI(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFundsException(InsufficientFundsException ex, HttpServletRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), webRequest.getRequestURI(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAccountException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAccountException(InvalidAccountException ex, HttpServletRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), webRequest.getRequestURI(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCurrencyException.class)
    public  ResponseEntity<ErrorResponse> handleInvalidCurrencyException(InvalidCurrencyException ex, HttpServletRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), webRequest.getRequestURI(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }






}