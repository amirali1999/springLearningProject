package com.example.demo.controller;

import com.example.demo.exception.UpdateObjectException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class UpdateObjectExceptionHandler {
    @ExceptionHandler({UpdateObjectException.class})
    public ResponseEntity<Object> HandlerUpdateObjectException(Exception exception, WebRequest request) {
        return new ResponseEntity<Object>("object not found! can not update", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
