package com.tdxy.oauth.service.login;

import com.tdxy.oauth.model.bo.LoginResult;

public interface LoginStrategy {
    LoginResult login(String username, String password, String role);
}
