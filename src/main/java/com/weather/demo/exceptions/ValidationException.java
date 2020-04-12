package com.weather.demo.exceptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationException extends RuntimeException {

    public static class ValidationError {

        private int errorCode;
        private String errorMessage;

        public ValidationError(int errorCode, String errorMessage) {
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

    private List<ValidationError> validationErrors = new ArrayList<>();

    public ValidationException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ValidationException(Throwable throwable) {
        super(throwable);
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException() {
        super();
    }

    public boolean addValidationError(ValidationError validationError) {
        return validationErrors.add(validationError);
    }

    public List<ValidationError> getValidationErrors() {
        return Collections.unmodifiableList(validationErrors);
    }

}
