package com.tdxy.oauth.exception;

/**
 * 非法客户端异常
 *
 * @author Qug_
 */
public class IllegalClientException extends Exception {
    private static final long serialVersionUID = 1L;

    public IllegalClientException(String message) {
        super(message);
    }
}
