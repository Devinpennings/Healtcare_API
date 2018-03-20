package com.pharmacy.healthcare.domain;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class DoctorEmployee extends User implements Serializable{

    @Override
    public String getType() {
        return "doctorEmployee";
    }
}
