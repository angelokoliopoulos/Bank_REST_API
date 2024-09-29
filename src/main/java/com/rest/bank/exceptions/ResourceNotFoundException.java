package com.rest.bank.exceptions;

public class ResourceNotFoundException extends  RuntimeException {

    public ResourceNotFoundException(String message){
        super(message);
    }
}
