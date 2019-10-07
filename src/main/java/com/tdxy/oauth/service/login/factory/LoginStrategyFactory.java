package com.tdxy.oauth.service.login.factory;

import com.tdxy.oauth.common.ApplicationContextUtil;
import com.tdxy.oauth.service.login.LoginStrategy;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Objects;

public class LoginStrategyFactory {
    private static Map<String, LoginStrategy> loginStrategy;

    public static LoginStrategy getStrategy(String role) {
        if (Objects.isNull(loginStrategy)) {
            ApplicationContext ac = ApplicationContextUtil.getApplicationContext();
            loginStrategy = ac.getBeansOfType(LoginStrategy.class);
        }
        return loginStrategy.get(role);
    }
}
