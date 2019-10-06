package com.tdxy.oauth.exception;

/**
 * 无效的grant_type
 *
 * @author Qug_
 */
public class GrantTypeException extends Exception {
    private static final long serialVersionUID = 1L;

    public GrantTypeException(String message) {
        super(message);
    }
}
