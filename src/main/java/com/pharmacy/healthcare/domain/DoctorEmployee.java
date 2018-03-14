package com.pharmacy.healthcare.domain;

public class DoctorEmployee extends User{

    @Override
    public String getType() {
        return "doctorEmployee";
    }
}
