package com.epam.esm.exceptions;

import static com.epam.esm.util.StringConst.badRequest;

public class BadRequestException extends Exception{
    public BadRequestException() {
        super(badRequest);
    }
    public BadRequestException(String message) {
        super(message);
    }
}
