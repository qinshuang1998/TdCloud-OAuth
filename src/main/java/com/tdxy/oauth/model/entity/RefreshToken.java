package com.tdxy.oauth.model.entity;

import java.util.Date;

public class RefreshToken {
    private String refreshToken;

    private String tokenId;

    private String appId;

    private String userIdentity;

    private String userRole;

    private Date expireTime;

    public RefreshToken() {
    }

    public RefreshToken(String refreshToken, String tokenId, String appId, String userIdentity, String userRole) {
        this.refreshToken = refreshToken;
        this.tokenId = tokenId;
        this.appId = appId;
        this.userIdentity = userIdentity;
        this.userRole = userRole;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken == null ? null : refreshToken.trim();
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId == null ? null : tokenId.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity == null ? null : userIdentity.trim();
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole == null ? null : userRole.trim();
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}