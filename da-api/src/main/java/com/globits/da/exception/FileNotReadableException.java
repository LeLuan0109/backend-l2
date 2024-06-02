package com.globits.da.exception;

import com.globits.da.validator.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileNotReadableException extends RuntimeException {
    private int errorCode;

    public FileNotReadableException(ValidationError error) {
        super(error.getErrorMessage());
        this.errorCode = error.getErrorCode();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
