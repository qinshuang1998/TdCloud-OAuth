package com.tdxy.oauth.service.strategy;

import com.tdxy.oauth.model.bo.LoginResult;
import com.tdxy.oauth.model.po.Member;

public class RejectedStrategy implements UserStrategy {
    public static class Singleton {
        public static RejectedStrategy INSTANCE = new RejectedStrategy();
    }

    @Override
    public LoginResult login(String username, String password, String role) {
        return new LoginResult(false, null);
    }

    @Override
    public Member getInfo(String identity) {
        return null;
    }
}
