package com.tdxy.oauth.service.strategy.factory;

import com.tdxy.oauth.common.ApplicationContextUtil;
import com.tdxy.oauth.service.strategy.RejectedStrategy;
import com.tdxy.oauth.service.strategy.UserStrategy;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Objects;

public class UserStrategyFactory {
    private static Map<String, UserStrategy> UserStrategy;

    public static UserStrategy getStrategy(String role) {
        if (Objects.isNull(UserStrategy)) {
            synchronized (UserStrategyFactory.class) {
                if (Objects.isNull(UserStrategy)) {
                    ApplicationContext ac = ApplicationContextUtil.getApplicationContext();
                    UserStrategy = ac.getBeansOfType(UserStrategy.class);
                }
            }
        }
        UserStrategy strategy = UserStrategy.get(role);
        return Objects.nonNull(strategy) ? strategy : RejectedStrategy.Singleton.INSTANCE;
    }
}
