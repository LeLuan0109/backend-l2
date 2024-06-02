package com.globits.da.exception;

import com.globits.da.validator.ValidationError;

public class NotValidCertificateException extends RuntimeException {
    private int errorCode;

    public NotValidCertificateException(ValidationError error) {
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
