package com.novice.ecom.advice;


import com.novice.ecom.exceptions.BadRequestException;
import com.novice.ecom.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIError> resourceNotFound(ResourceNotFoundException rnfe) {

        APIError apiError = returnAPIError(rnfe);
        apiError.setHttpStatus(HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<APIError> resourceNotFound(BadRequestException bre) {

        APIError apiError = returnAPIError(bre);
        apiError.setHttpStatus(HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIError> validationErrors(MethodArgumentNotValidException manve) {

        //getting all the binding errors from the exception and converting it to List<String> using stream
        List<String> errorList = manve.getBindingResult()
                .getAllErrors()
                .stream().map(error->error.getDefaultMessage())
                .collect(Collectors.toList());

        APIError apiError = returnAPIError(manve);
        apiError.setHttpStatus(HttpStatus.BAD_REQUEST);
        apiError.setSubErrors(errorList);

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> internalServerError(Exception exception) {

        APIError apiError = returnAPIError(exception);
            apiError.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //return APIError, with exception message, and current date,time
    APIError returnAPIError(Exception exception){

        String currentDateAndTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm"));

        return APIError.builder()
                .message(exception.getMessage())
                .errorRecordedTime(currentDateAndTime)
                .build();
    }
}