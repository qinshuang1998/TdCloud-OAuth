package com.tdxy.oauth.model.bo;

public class LoginResult {
    private User user;
    private boolean success;

    public LoginResult(boolean success, User user) {
        this.user = user;
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
