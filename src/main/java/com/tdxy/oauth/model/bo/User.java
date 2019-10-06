package com.tdxy.oauth.model.bo;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 4226133552814487404L;
    private String role;
    private String identity;

    public User() {
    }

    public User(String role, String identity) {
        this.role = role;
        this.identity = identity;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
