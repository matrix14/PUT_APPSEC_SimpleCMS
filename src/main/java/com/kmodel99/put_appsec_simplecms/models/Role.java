package com.kmodel99.put_appsec_simplecms.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN(0), ROLE_USER(1), ROLE_WRITER(2);

    private final Integer id;

    Role(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getAuthority() {
        return name();
    }

}
