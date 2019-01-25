package com.tdxy.oauth.context;

import com.tdxy.oauth.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuthUserContext implements AuthContext {
    private static final long serialVersionUID = -5803208351122804211L;
    private static final ThreadLocal<User> PRINCIPAL = new ThreadLocal<>();

    @Override
    public User getPrincipal() {
        return PRINCIPAL.get();
    }

    @Override
    public void setPrincipal(User user) {
        PRINCIPAL.set(user);
    }

    @Override
    public boolean isAuthenticated() {
        return (PRINCIPAL.get() != null);
    }

    public static void clearThread() {
        PRINCIPAL.remove();
    }
}
