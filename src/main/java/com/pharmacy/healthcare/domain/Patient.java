package com.pharmacy.healthcare.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("patient")
public class Patient extends User {

    @Column(name = "age", nullable = false)
    protected Long age;

    @OneToMany(
    cascade = CascadeType.ALL,
    orphanRemoval = true
    )
    private List<Diagnosis> diagnoses;

    public Long getAge() {
        return age;
    }

    public List<Diagnosis> getDiagnoses() {
        return diagnoses;
    }
}
