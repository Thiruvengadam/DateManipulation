package com.rabo.exception;

public class InvalidDateException extends Exception {

    public InvalidDateException(String message) {
        super(message);
    }

    public InvalidDateException(String message, Throwable t) {
        super(message, t);
    }
}
