package com.example.demo.exceptions;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ApiError {

    public static class ValidationErrors {
        private int errorCode;
        private String errorMessage;

        public ValidationErrors(int errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        @Override
        public String toString() {
            return "ValidationErrors{" +
                    "errorCode=" + errorCode +
                    ", errorMessage='" + errorMessage + '\'' +
                    '}';
        }
    }

    private int status;
    private String message;
    private String developerMessage;
    private List<ValidationErrors> validationErrors;

    public ApiError(final int status, final String message, final String developerMessage) {
        this(status, message, developerMessage, null);
    }

    public ApiError(final int status, final String message, final String developerMessage, List<ValidationErrors> validationErrors) {
        this.status = status;
        this.message = message;
        this.developerMessage = developerMessage;
        this.validationErrors = validationErrors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(final String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public List<ValidationErrors> getValidationErrors() {
        return validationErrors;
    }

    public static final ApiError message(final HttpStatus httpStatus, final Exception ex) {
        return message(httpStatus, ex, null);
    }

    public static final ApiError message(final HttpStatus httpStatus, final Exception ex, List<ValidationErrors> validationErrors) {
        final String message = ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage();
        final String devMessage = ExceptionUtils.getRootCauseMessage(ex);
        return new ApiError(httpStatus.value(), message, devMessage, validationErrors);
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", developerMessage='" + developerMessage + '\'' +
                ", validationErrors=" + validationErrors +
                '}';
    }

}