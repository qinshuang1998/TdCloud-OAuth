package com.tdxy.oauth.service.user;

import com.tdxy.oauth.Constant;
import com.tdxy.oauth.model.bo.LoginResult;
import com.tdxy.oauth.model.bo.User;
import com.tdxy.oauth.model.bo.ZFCookie;
import com.tdxy.oauth.model.po.Member;
import com.tdxy.oauth.service.StudentService;
import com.tdxy.oauth.service.ZFService;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("student")
public class StudentStrategy implements UserStrategy {
    private final ZFService zfService;

    private final StudentService studentService;

    @Autowired
    public StudentStrategy(StudentService studentService, ZFService zfService) {
        this.studentService = studentService;
        this.zfService = zfService;
    }

    @Override
    public LoginResult login(String username, String password, String role) {
        String hash = Md5Crypt.apr1Crypt(
                username + Md5Crypt.apr1Crypt(password, Constant.Security.MD5_SALT_STU_PWD),
                Constant.Security.MD5_SALT_CACHE);
        ZFCookie cookie = zfService.getCookieByHash(hash);
        int cookieStatus = zfService.checkCookie(cookie);
        boolean result = (cookieStatus == 1) || zfService.doLogin(username, password);
        User user = result ? new User(role, username) : null;
        return new LoginResult(result, user);
    }

    @Override
    public Member getInfo(String identity) {
        return studentService.getInfo(identity);
    }
}
