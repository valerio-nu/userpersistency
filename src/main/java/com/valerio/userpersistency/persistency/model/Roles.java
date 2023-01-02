package com.valerio.userpersistency.persistency.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(RoleUser.class)

public class Roles {
    @Id
    @Column(name = "username", length = 50)
    @NotNull
    private String username;

    @Id
    @Column(name = "rolename", length = 50)
    @NotNull
    private String rolename;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "username", referencedColumnName = "username",
                    insertable = false, updatable = false)
    })
    @JsonIgnore
    private User user;

    public Roles() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Roles)) return false;
        Roles that = (Roles) o;
        return Objects.equals(getUsername(), that.getUsername()) &&
                Objects.equals(getRolename(), that.getRolename());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getRolename());
    }

    @Override
    public String toString() {
        return "AuthorityEntity{" +
                "username='" + username + '\'' +
                ", authority='" + rolename + '\'' +
                '}';
    }
}
