package com.example.demo.exception.type;

public class UpdateObjectException extends RuntimeException {
    private String message;
    public UpdateObjectException(){
    }
    public UpdateObjectException(String message){
        super(message);
        this.message = message;
    }
}
