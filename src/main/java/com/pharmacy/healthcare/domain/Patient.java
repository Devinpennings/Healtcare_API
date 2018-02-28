package com.pharmacy.healthcare.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient extends User {

    @Column(name = "age", nullable = false)
    protected Long age;

    @OneToMany(
    cascade = CascadeType.ALL,
    orphanRemoval = true
    )
    private List<Diagnosis> diagnoses;

}
