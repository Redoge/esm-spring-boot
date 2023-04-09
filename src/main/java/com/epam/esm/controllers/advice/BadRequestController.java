package com.epam.esm.controllers.advice;

import com.epam.esm.dto.ErrorDto;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadRequestController {
    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto badRequestException(BadRequestException ex) {
        return new ErrorDto(ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());
    }
    @ExceptionHandler(value = {ObjectNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto notFoundException(ObjectNotFoundException ex) {
        return new ErrorDto(ex.getMessage(),
                HttpStatus.NOT_FOUND.value());
    }
    @ExceptionHandler(value = {ObjectIsExistException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto isExistException(ObjectIsExistException ex) {
        return new ErrorDto(ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());
    }


}
