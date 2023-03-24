package com.epam.esm.exceptions;

public class ObjectIsExistException extends Exception{
    public ObjectIsExistException(String objectName, Long id) {
        super(String.format("%s with id - %d is exist!", objectName, id));
    }
    public ObjectIsExistException(String objectName, String name) {
        super(String.format("%s with name - %s is exist!", objectName, name));
    }

}
