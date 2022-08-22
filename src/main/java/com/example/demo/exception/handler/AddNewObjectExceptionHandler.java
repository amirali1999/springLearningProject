package com.example.demo.exception.handler;

import com.example.demo.exception.ErrorResponse;
import com.example.demo.exception.type.AddNewObjectException;
import com.example.demo.exception.type.UpdateObjectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class AddNewObjectExceptionHandler {
    @ExceptionHandler(value = AddNewObjectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse HandlerAddNewObjectException(AddNewObjectException ex) {
        if(ex.getMessage().contains("exists")){
            return new ErrorResponse(HttpStatus.FOUND.value(), ex.getMessage());
        }
        else if(ex.getMessage().contains("not found")){
            return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        }
        else if(ex.getMessage().contains("role") && ex.getMessage().contains("Allowed")){
            return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        }
        else return new ErrorResponse(HttpStatus.NOT_MODIFIED.value(), "unhandled error!!!!!");
    }
}
