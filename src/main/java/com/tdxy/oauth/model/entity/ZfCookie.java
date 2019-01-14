package com.tdxy.oauth.model.entity;

import java.util.Date;

public class ZfCookie {
    private String stuNumber;

    private String cookiePrefix;

    private String cookieValue;

    private Date updateTime;

    public ZfCookie() {
    }

    public ZfCookie(String stuNumber, String cookiePrefix, String cookieValue) {
        this.stuNumber = stuNumber;
        this.cookiePrefix = cookiePrefix;
        this.cookieValue = cookieValue;
    }

    public String getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber == null ? null : stuNumber.trim();
    }

    public String getCookiePrefix() {
        return cookiePrefix;
    }

    public void setCookiePrefix(String cookiePrefix) {
        this.cookiePrefix = cookiePrefix == null ? null : cookiePrefix.trim();
    }

    public String getCookieValue() {
        return cookieValue;
    }

    public void setCookieValue(String cookieValue) {
        this.cookieValue = cookieValue == null ? null : cookieValue.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}