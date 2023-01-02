package com.github.szsalyi.globalblue.spring;

import com.github.szsalyi.globalblue.dto.ApiError;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler({
            ConstraintViolationException.class,
            IllegalArgumentException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class

    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ApiError> handleConstraintViolationException(Exception e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Not valid input parameters, due to validation error: " + e.getMessage(), e.getLocalizedMessage());

        return new ResponseEntity<>(apiError, apiError.status());
    }
}


