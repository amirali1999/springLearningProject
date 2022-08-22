package com.example.demo.exception.type;

public class DeleteObjectException extends RuntimeException{
    private String message;
    public DeleteObjectException(){}
    public DeleteObjectException(String message){
        super(message);
        this.message = message;
    }
}
