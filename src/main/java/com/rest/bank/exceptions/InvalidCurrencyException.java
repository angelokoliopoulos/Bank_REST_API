package com.rest.bank.exceptions;

public class InvalidCurrencyException extends RuntimeException{
    public  InvalidCurrencyException(String message){
        super( message);
    }
}
