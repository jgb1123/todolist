package com.solo.todolist.common.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public com.solo.todolist.common.exception.ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        return com.solo.todolist.common.exception.ErrorResponse.of(e.getBindingResult());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public com.solo.todolist.common.exception.ErrorResponse handleConstraintViolationException(
            ConstraintViolationException e) {

        return com.solo.todolist.common.exception.ErrorResponse.of(e.getConstraintViolations());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public com.solo.todolist.common.exception.ErrorResponse handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {

        return com.solo.todolist.common.exception.ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public com.solo.todolist.common.exception.ErrorResponse handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {

        return com.solo.todolist.common.exception.ErrorResponse.of(HttpStatus.BAD_REQUEST,
                "Required request body is missing");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public com.solo.todolist.common.exception.ErrorResponse handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {

        return com.solo.todolist.common.exception.ErrorResponse.of(HttpStatus.BAD_REQUEST,
                e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public com.solo.todolist.common.exception.ErrorResponse handleExpiredJwtException(
            ExpiredJwtException e) {

        return com.solo.todolist.common.exception.ErrorResponse.of(HttpStatus.PRECONDITION_FAILED,
                e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public com.solo.todolist.common.exception.ErrorResponse handleException(Exception e) {
        log.error("Internal Server Error", e);

        return com.solo.todolist.common.exception.ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleBusinessLogicException(com.solo.todolist.common.exception.BusinessLogicException e) {
        final com.solo.todolist.common.exception.ErrorResponse response = com.solo.todolist.common.exception.ErrorResponse.of(e.getExceptionCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode()
                .getStatus()));
    }
}