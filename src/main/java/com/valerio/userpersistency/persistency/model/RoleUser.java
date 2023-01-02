package com.valerio.userpersistency.persistency.model;

import java.io.Serializable;
import java.util.Objects;

public class RoleUser implements Serializable {
    private static final long serialVersionUID = 3246039946807996563L;

    private String username;

    private String rolename;

    public RoleUser() {
    }

    public RoleUser(String username, String rolename) {
        this.username = username;
        this.rolename = rolename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleUser)) return false;
        RoleUser that = (RoleUser) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(rolename, that.rolename);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username, rolename);
    }


}
