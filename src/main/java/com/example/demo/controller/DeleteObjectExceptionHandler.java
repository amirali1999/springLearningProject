package com.example.demo.controller;

import com.example.demo.exception.DeleteObjectException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class DeleteObjectExceptionHandler {
    @ExceptionHandler({DeleteObjectException.class})
    public ResponseEntity<Object> HandlerDeleteObjectException(Exception exception, WebRequest request) {
        return new ResponseEntity<Object>("object not found!!!!!", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
