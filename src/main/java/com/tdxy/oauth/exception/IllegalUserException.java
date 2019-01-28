package com.tdxy.oauth.exception;

public class IllegalUserException extends Exception {
    private static final long serialVersionUID = 2210235163816068945L;

    public IllegalUserException(String message) {
        super(message);
    }
}
