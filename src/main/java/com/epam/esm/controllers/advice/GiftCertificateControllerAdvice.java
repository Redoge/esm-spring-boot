package com.epam.esm.controllers.advice;

import com.epam.esm.dto.ErrorDto;
import com.epam.esm.exceptions.GiftCertificateIsExistException;
import com.epam.esm.exceptions.GiftCertificateNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.lang.String.format;

@RestControllerAdvice
public class GiftCertificateControllerAdvice {
    @ExceptionHandler(value = {GiftCertificateNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto tagNotFoundException(GiftCertificateNotFoundException ex) {
        return new ErrorDto(format("Gift certificate with %s - NOT FOUND!", ex.getMessage()), 404);
    }

    @ExceptionHandler(value = {GiftCertificateIsExistException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto TagIsExistException(GiftCertificateIsExistException ex) {
        return new ErrorDto(format("Gift certificate with %s is exist!", ex.getMessage()), 400);
    }
}
