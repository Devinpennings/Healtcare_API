package com.pharmacy.healthcare.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@DiscriminatorValue("doctor")
public class Doctor extends User implements Serializable{
    @Override
    public String getType() {
        return "doctor";
    }
}
