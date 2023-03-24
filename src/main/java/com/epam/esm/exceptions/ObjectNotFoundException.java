package com.epam.esm.exceptions;

public class ObjectNotFoundException extends Exception{
    public ObjectNotFoundException(String objectName, Long id) {
        super(String.format("%s with id - %d not found!", objectName, id));
    }
    public ObjectNotFoundException(String objectName, String name) {
        super(String.format("%s with name - %s not found!", objectName, name));
    }
}
