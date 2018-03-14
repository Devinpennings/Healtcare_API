package com.pharmacy.healthcare.oauth;

import java.io.Serializable;

public class OAuthModel implements Serializable {

    private Long userId;
    private String firstname;
    private String lastname;
    private String username;
    private String type;

    public OAuthModel(Long userId, String firstname, String lastname, String username, String type) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }
}
