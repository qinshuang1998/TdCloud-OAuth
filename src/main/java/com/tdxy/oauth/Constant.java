package com.tdxy.oauth;

public final class Constant {
    public interface HttpStatus {
        int SUCCESS = 200;
        int ERROR = 500;
        int REDIRECT = 302;
        int UNAUTHORIZED = 401;
        int UNDEFINE = -1;
    }

    public interface ZhengFang {
        String URL = "http://jwxt.nytdc.edu.cn";
        String DOMAIN = "jwxt.nytdc.edu.cn";
        int COOKIE_VALID = 1;
        int COOKIE_INVALID = 0;
        int COOKIE_NOT_EXIST = -1;
        int TRY_LOGIN_TIMES = 3;
    }

    public interface Session {
        String SESSION_KEY = "OAuth_User";
    }

    public interface Token {
        String HEADER = "Authorization";
        String HEAD = "Bearer ";
        String TYPE = "bearer";
        String SCOPE = "read";
        long EXPIRE_TIME_SEC = 3600L;
    }

    public interface Code {
        long EXPIRE_TIME_SEC = 600L;
        String PREFIX = "code:";
    }

    public interface Security {
        String MD5_SALT_STU_PWD = "pwd";
        String MD5_SALT_TCH_PWD = "tdxy";
        String MD5_SALT_CACHE = "cache";
        String MD5_SALT_RC4 = "Rc4Encrypt";
    }
}
