package com.tdxy.oauth.service.user.factory;

import com.tdxy.oauth.common.ApplicationContextUtil;
import com.tdxy.oauth.service.user.UserStrategy;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Objects;

public class UserStrategyFactory {
    private static Map<String, UserStrategy> UserStrategy;

    public static UserStrategy getStrategy(String role) {
        if (Objects.isNull(UserStrategy)) {
            ApplicationContext ac = ApplicationContextUtil.getApplicationContext();
            UserStrategy = ac.getBeansOfType(UserStrategy.class);
        }
        return UserStrategy.get(role);
    }
}
