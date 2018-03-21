package com.pharmacy.healthcare.domain;

import java.io.Serializable;

public class DoctorEmployee extends User implements Serializable{

    public DoctorEmployee() {
    }

    @Override
    public String getType() {
        return "doctorEmployee";
    }
}
