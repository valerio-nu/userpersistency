package com.valerio.userpersistency.security.service;

import java.util.List;
import java.util.Objects;

public class UserDTO implements Comparable<UserDTO> {

    private int userNum;
    private String userName;
    private List<String> userRoles;
    private String userRolesAsString;

    public UserDTO() {
    }

    public int getuserNum() {
        return userNum;
    }

    public void setuserNum(int userNum) {
        this.userNum = userNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
    }

    public String getUserRolesAsString() {
        return userRolesAsString;
    }

    public void setUserRolesAsString(String userRolesAsString) {
        this.userRolesAsString = userRolesAsString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(getUserName(), userDTO.getUserName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName());
    }

    @Override
    public int compareTo(UserDTO o) {
        return this.userName.compareTo(o.getUserName());
    }
}

