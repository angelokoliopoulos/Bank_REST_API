package com.rest.bank.exceptions;

public class InvalidAccountException extends RuntimeException{
    public InvalidAccountException(String message){
        super(message);

    }
}
