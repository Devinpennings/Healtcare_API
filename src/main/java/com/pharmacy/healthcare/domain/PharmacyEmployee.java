package com.pharmacy.healthcare.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@DiscriminatorValue("pharmacyEmployee")
public class PharmacyEmployee extends User implements Serializable {

    public PharmacyEmployee() {
    }

    @Override
    public String getType() {
        return "pharmacyEmployee";
    }
}
