package com.pharmacy.healthcare.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("patient")
public class Patient extends User {

    @Column(name = "age", nullable = false)
    protected Long age;

    @OneToMany(
            orphanRemoval = true
    )
    private List<Diagnosis> diagnoses;

    public Long getAge() {
        return age;
    }
}
