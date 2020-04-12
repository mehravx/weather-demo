package com.example.demo.exceptions;

public class DataLoadingException extends RuntimeException {

    public DataLoadingException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public DataLoadingException(Throwable throwable) {
        super(throwable);
    }

    public DataLoadingException(String message) {
        super(message);
    }

    public DataLoadingException() {
        super();
    }

}
