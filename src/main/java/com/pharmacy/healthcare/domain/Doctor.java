package com.pharmacy.healthcare.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("doctor")
public class Doctor extends User {
    @Override
    public String getType() {
        return "doctor";
    }
}
