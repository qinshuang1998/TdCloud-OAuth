package com.tdxy.oauth.context;

import com.tdxy.oauth.model.entity.User;

import java.io.Serializable;

public interface AuthContext extends Serializable {
    Object getPrincipal();

    void setPrincipal(User var1);

    boolean isAuthenticated();
}
