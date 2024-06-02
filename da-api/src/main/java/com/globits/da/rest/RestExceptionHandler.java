package com.globits.da.rest;

import com.globits.da.dto.RestResponse;
import com.globits.da.exception.*;
import com.globits.da.validator.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        int errorCode = ValidationError.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.getErrorCode();
        return new RestResponse<>(errorCode, errorMessages);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse<?> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errorMessages = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        int errorCode = ValidationError.CONTRAIN_VIOLATION_EXCEPTION.getErrorCode();
        return new RestResponse<>(errorCode, errorMessages);
    }

    @ExceptionHandler(FileNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse<?> handleFileNotReadableException(FileNotReadableException ex) {
        return new RestResponse<>(ex.getErrorCode(), Collections.singletonList(ex.getMessage()));
    }

    @ExceptionHandler(MismatchLocationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse<?> handleMismatchLocationException(MismatchLocationException ex) {
        return new RestResponse<>(ex.getErrorCode(), Collections.singletonList(ex.getMessage()));
    }

    @ExceptionHandler(DeleteNotAllowedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public RestResponse<?> handleDeleteNotAllowedException(DeleteNotAllowedException ex) {
        return new RestResponse<>(ex.getErrorCode(), Collections.singletonList(ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestResponse<?> handleNotFoundException(NotFoundException ex) {
        return new RestResponse<>(ex.getErrorCode(), Collections.singletonList(ex.getMessage()));
    }

    @ExceptionHandler(NotNullableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse<?> handleNotNullableExcpetion(NotNullableException ex) {
        return new RestResponse<>(ex.getErrorCode(), Collections.singletonList(ex.getMessage()));
    }

    @ExceptionHandler(NotValidCertificateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse<?> handleNotValidCertificateException(NotValidCertificateException ex) {
        return new RestResponse<>(ex.getErrorCode(), Collections.singletonList(ex.getMessage()));
    }

    @ExceptionHandler(DuplicateValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse<?> handleDuplicateEmployeeCodeException(DuplicateValueException ex) {
        return new RestResponse<>(ex.getErrorCode(), Collections.singletonList(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final RestResponse<?> handleGeneralExceptions(Exception ex) {
        int errorCode = ValidationError.GENERAL_EXCEPTION.getErrorCode();
        return new RestResponse<>(errorCode, Collections.singletonList(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final RestResponse<?> handleRuntimeExceptions(RuntimeException ex) {
        int errorCode = ValidationError.RUNTIME_EXCEPTION.getErrorCode();
        return new RestResponse<>(errorCode, Collections.singletonList(ex.getMessage()));
    }

    @ExceptionHandler(ExportExcelFileException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final RestResponse<?> handleTypeExceptions(ExportExcelFileException ex) {
        return new RestResponse<>(ex.getErrorCode(), Collections.singletonList(ex.getMessage()));
    }
}
