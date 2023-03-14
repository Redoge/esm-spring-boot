package com.epam.esm.exceptions;

public class GiftCertificateIsExistException extends Exception{
    public GiftCertificateIsExistException() {
        super();
    }

    public GiftCertificateIsExistException(String message) {
        super(message);
    }
}
