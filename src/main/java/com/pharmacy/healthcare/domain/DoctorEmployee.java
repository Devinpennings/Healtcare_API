package com.pharmacy.healthcare.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;


@Entity
@DiscriminatorValue("doctorEmployee")
public class DoctorEmployee extends User implements Serializable{

    public DoctorEmployee() {
    }

    @Override
    public String getType() {
        return "doctorEmployee";
    }
}
