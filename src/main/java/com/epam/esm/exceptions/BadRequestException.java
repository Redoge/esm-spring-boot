package com.epam.esm.exceptions;

public class BadRequestException extends Exception{
    public BadRequestException() {
        super("Bad request!");
    }
    public BadRequestException(String message) {
        super(message);
    }
}
