package com.pharmacy.healthcare.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;


@Entity
@DiscriminatorValue("doctorEmployee")
public class DoctorEmployee extends User implements Serializable{

    public DoctorEmployee() {
    }

    public DoctorEmployee(String firstname, String lastname, String username, boolean enabled) {
        super(firstname, lastname, username, enabled);
    }

    @Override
    public String getType() {
        return "doctorEmployee";
    }
}
