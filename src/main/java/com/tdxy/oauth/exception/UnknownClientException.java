package com.tdxy.oauth.exception;

/**
 * 未知客户端异常
 *
 * @author Qug_
 */
public class UnknownClientException extends Exception {
    private static final long serialVersionUID = 4647260137611886630L;

    public UnknownClientException(String message) {
        super(message);
    }
}
