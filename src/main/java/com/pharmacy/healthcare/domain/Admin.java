package com.pharmacy.healthcare.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User implements Serializable {

    public Admin() {
    }

    public Admin(String firstname, String lastname, String username, boolean enabled) {
        super(firstname, lastname, username, enabled);
    }

    @Override
    public String getType() {
        return "admin";
    }
}
