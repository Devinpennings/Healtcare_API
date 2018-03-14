package com.pharmacy.healthcare.domain;

import java.io.Serializable;

public class DoctorEmployee extends User implements Serializable{

    @Override
    public String getType() {
        return "doctorEmployee";
    }
}
