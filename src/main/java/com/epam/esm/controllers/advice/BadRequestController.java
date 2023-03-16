package com.epam.esm.controllers.advice;

import com.epam.esm.dto.ErrorDto;
import com.epam.esm.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadRequestController {
    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto tagNotFoundException(BadRequestException ex) {
        return new ErrorDto(ex.getMessage(), 404);
    }

}
