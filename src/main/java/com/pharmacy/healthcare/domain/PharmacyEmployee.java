package com.pharmacy.healthcare.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "pharmacyEmployee")
public class PharmacyEmployee extends User implements Serializable {

    @Override
    public String getType() {
        return "pharmacyEmployee";
    }
}
