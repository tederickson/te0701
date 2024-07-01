package com.tools.rental.controller;

import com.tools.rental.exception.InvalidRequestException;
import com.tools.rental.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ManagerAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(final InvalidRequestException exception) {
        log.error("handleBadRequest {}", exception.getMessage());
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(final NotFoundException exception) {
        log.info("handleNotFound {}", exception.getMessage());
        return exception.getMessage();
    }
}
