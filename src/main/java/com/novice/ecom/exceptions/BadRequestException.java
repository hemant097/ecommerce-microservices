package com.novice.ecom.exceptions;

public class BadRequestException  extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
