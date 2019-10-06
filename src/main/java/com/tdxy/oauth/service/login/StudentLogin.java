package com.tdxy.oauth.service.login;

import com.tdxy.oauth.Constant;
import com.tdxy.oauth.model.bo.LoginResult;
import com.tdxy.oauth.model.bo.User;
import com.tdxy.oauth.model.bo.ZFCookie;
import com.tdxy.oauth.service.ZFService;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("student")
public class StudentLogin implements LoginStrategy {
    private final ZFService zfService;

    @Autowired
    public StudentLogin(ZFService zfService) {
        this.zfService = zfService;
    }

    @Override
    public LoginResult login(String username, String password, String role) {
        String hash = Md5Crypt.apr1Crypt(
                username + Md5Crypt.apr1Crypt(password, Constant.Security.MD5_SALT_STU_PWD),
                Constant.Security.MD5_SALT_CACHE);
        ZFCookie cookie = this.zfService.getCookieByHash(hash);
        int cookieStatus = this.zfService.checkCookie(cookie);
        boolean result = (cookieStatus == 1) || this.zfService.doLogin(username, password);
        User user = result ? new User(role, username) : null;
        return new LoginResult(result, user);
    }
}
