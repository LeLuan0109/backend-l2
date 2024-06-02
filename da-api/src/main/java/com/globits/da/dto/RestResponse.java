package com.globits.da.dto;

import com.globits.da.validator.ValidationError;

import java.util.Collections;
import java.util.List;

public class RestResponse<T> {
    private int errorCode;
    private List<String> errorMessages;
    private T data;

    public RestResponse(int code, List<String> message) {
        this.errorCode = code;
        this.errorMessages = message;
    }

    public RestResponse(T data) {
        this.errorCode = ValidationError.SUCCESS.getErrorCode();
        this.errorMessages = Collections.singletonList("Request is successful");
        this.data = data;
    }

    public RestResponse(T data, List<String> messages) {
        this.errorCode = ValidationError.SUCCESS.getErrorCode();
        this.errorMessages = messages;
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
