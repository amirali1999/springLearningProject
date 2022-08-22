package com.example.demo.exception.handler;

import com.example.demo.exception.ErrorResponse;
import com.example.demo.exception.type.DeleteObjectException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DeleteObjectExceptionHandler {
    @ExceptionHandler(value = DeleteObjectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse HandlerDeleteObjectException(DeleteObjectException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}
