package com.salesmanagementplatform.equity.controller;

import com.salesmanagementplatform.equity.error.Error;
import com.salesmanagementplatform.equity.error.exceptions.RequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EquityControllerAdvice {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Error> runtimeExceptionHandler(RuntimeException ex)
    {
        Error error = Error.builder().code("P-500").message(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<Error> requestExceptionHandler(RequestException ex)
    {
        Error error = Error.builder().code(ex.getCode()).message(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
