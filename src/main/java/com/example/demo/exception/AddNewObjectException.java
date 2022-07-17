package com.example.demo.exception;

import org.springframework.stereotype.Component;


public class AddNewObjectException extends Exception{
    public AddNewObjectException(String message){
        super(message);
    }
}
