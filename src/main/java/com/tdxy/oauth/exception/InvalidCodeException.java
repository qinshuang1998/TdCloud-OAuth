package com.tdxy.oauth.exception;

/**
 * 无效Code异常
 *
 * @author Qug_
 */
public class InvalidCodeException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidCodeException(String message) {
        super(message);
    }
}
