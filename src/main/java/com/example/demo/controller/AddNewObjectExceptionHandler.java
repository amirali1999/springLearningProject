package com.example.demo.controller;

import com.example.demo.exception.AddNewObjectException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AddNewObjectExceptionHandler {
    @ExceptionHandler({AddNewObjectException.class})
    public ResponseEntity<Object> HandlerAddNewObjectException(Exception exception, WebRequest request) {
        return new ResponseEntity<Object>("Resource not found!!!!!", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
