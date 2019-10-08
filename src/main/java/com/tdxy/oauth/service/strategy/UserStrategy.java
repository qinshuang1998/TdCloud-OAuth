package com.tdxy.oauth.service.strategy;

import com.tdxy.oauth.model.bo.LoginResult;
import com.tdxy.oauth.model.po.Member;

public interface UserStrategy {
    LoginResult login(String username, String password, String role);

    Member getInfo(String identity);
}
