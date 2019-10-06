package com.tdxy.oauth.exception;

/**
 * 未知客户端异常
 *
 * @author Qug_
 */
public class UnknownClientException extends Exception {
    private static final long serialVersionUID = 1L;

    public UnknownClientException(String message) {
        super(message);
    }
}
