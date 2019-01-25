package com.tdxy.oauth.exception;

/**
 * 无效token异常
 *
 * @author Qug_
 */
public class InvalidTokenException extends Exception {
    private static final long serialVersionUID = -5808028192716121382L;

    public InvalidTokenException(String message) {
        super(message);
    }
}
