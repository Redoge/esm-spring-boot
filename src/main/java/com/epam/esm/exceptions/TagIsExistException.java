package com.epam.esm.exception;

public class TagIsExistException extends Exception{
    public TagIsExistException() {
        super();
    }

    public TagIsExistException(String message) {
        super(message);
    }
}
