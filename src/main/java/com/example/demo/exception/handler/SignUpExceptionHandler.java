package com.example.demo.exception.handler;

import com.example.demo.exception.ErrorResponse;
import com.example.demo.exception.type.SignUpException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class SignUpExceptionHandler {
    @ExceptionHandler(value = SignUpException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse HandlerSignUpObjectException(SignUpException ex) {
        return new ErrorResponse(HttpStatus.FOUND.value(), ex.getMessage());
    }
}
