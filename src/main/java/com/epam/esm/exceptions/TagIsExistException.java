package com.epam.esm.exceptions;

public class TagIsExistException extends Exception{
    public TagIsExistException() {
        super();
    }

    public TagIsExistException(String message) {
        super(message);
    }
}
