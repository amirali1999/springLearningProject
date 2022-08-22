package com.example.demo.exception.handler;

import com.example.demo.exception.ErrorResponse;
import com.example.demo.exception.type.UpdateObjectException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UpdateObjectExceptionHandler {
    @ExceptionHandler(value = UpdateObjectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse HandlerUpdateObjectException(UpdateObjectException ex) {
        if(ex.getMessage().contains("not found")){
            return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        }
        else if(ex.getMessage().contains("exists")){
            return new ErrorResponse(HttpStatus.FOUND.value(), ex.getMessage());
        }
        else return new ErrorResponse(HttpStatus.NOT_MODIFIED.value(), "unhandled error!!!!!");
    }
}
