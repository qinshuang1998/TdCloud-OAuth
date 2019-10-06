package com.tdxy.oauth.exception;

public class IllegalUserException extends Exception {
    private static final long serialVersionUID = 1L;

    public IllegalUserException(String message) {
        super(message);
    }
}
