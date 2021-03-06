package com.tdxy.oauth.context;

import com.tdxy.oauth.model.bo.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class AuthUserContext implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final ThreadLocal<User> PRINCIPAL = new ThreadLocal<>();

    public User getPrincipal() {
        return PRINCIPAL.get();
    }

    public void setPrincipal(User user) {
        PRINCIPAL.set(user);
    }

    public boolean isAuthenticated() {
        return (PRINCIPAL.get() != null);
    }

    public static void clearThread() {
        PRINCIPAL.remove();
    }
}
