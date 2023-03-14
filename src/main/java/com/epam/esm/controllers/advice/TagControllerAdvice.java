package com.epam.esm.controller.advice;

import com.epam.esm.dto.ErrorDto;
import com.epam.esm.exception.TagIsExistException;
import com.epam.esm.exception.TagNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.lang.String.format;

@RestControllerAdvice
public class TagControllerAdvice {
    @ExceptionHandler(value = {TagNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto tagNotFoundException(TagNotFoundException ex) {
        return new ErrorDto(format("Tag with %s - NOT FOUND!", ex.getMessage()), 404);
    }

    @ExceptionHandler(value = {TagIsExistException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto TagIsExistException(TagIsExistException ex) {
        return new ErrorDto(format("Tag with %s is exist!", ex.getMessage()), 400);
    }
}
