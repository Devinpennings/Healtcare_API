package com.pharmacy.healthcare.oauth;

import com.pharmacy.healthcare.domain.Doctor;

import java.io.Serializable;

public class OAuthModel implements Serializable {

    private Long user_id;
    private String firstname;
    private String lastname;
    private String username;
    private String type;

    public OAuthModel(Long user_id, String firstname, String lastname, String username, String type) {
        this.user_id = user_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.type = type;
    }

    public Long getUser_id() {
        return user_id;
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
