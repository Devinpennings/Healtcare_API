package com.pharmacy.healthcare.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
public class Doctor extends User implements Serializable{


//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "doctor_id", nullable = false, updatable = false)
//    protected Long doctor_id;

    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Set<Patient> patients = new HashSet<>();

    @Override
    public String getType() {
        return "doctor";
    }

    public void addPatient(Patient patient)
    {
        this.patients.add(patient);
    }
}
