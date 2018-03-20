package com.pharmacy.healthcare.domain;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("doctor")
public class Doctor extends User implements Serializable{

    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Set<Patient> patients = new HashSet<>();

    @Override
    public String getType() {
        return "doctor";
    }


}
