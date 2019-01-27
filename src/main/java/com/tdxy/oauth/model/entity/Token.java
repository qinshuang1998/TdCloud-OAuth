package com.tdxy.oauth.model.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class Token {
    @JSONField(name = "access_token", ordinal = 1)
    private String accessToken;

    @JSONField(name = "token_type", ordinal = 2)
    private String tokenType;

    @JSONField(name = "expires_in", ordinal = 3)
    private long expiresIn;

    @JSONField(name = "refresh_token", ordinal = 4)
    private String refreshToken;

    @JSONField(ordinal = 5)
    private String scope;

    public Token() {
    }

    public Token(String accessToken, String tokenType, long expiresIn, String refreshToken, String scope) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.scope = scope;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
