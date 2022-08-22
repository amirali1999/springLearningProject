package com.example.demo.exception.type;

import org.springframework.stereotype.Component;


public class AddNewObjectException extends RuntimeException{
    private String message;
    public AddNewObjectException(){}
    public AddNewObjectException(String message){
        super(message);
        this.message = message;
    }
}
