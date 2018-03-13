package com.pharmacy.healthcare.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("patient")
public class Patient extends User {

    @Column(name = "age", nullable = false)
    protected Long age;

    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Set<Diagnosis> diagnoses;

    public Long getAge() {
        return age;
    }

    public void addDiagnosis(Diagnosis diagnosis)
    {
        diagnoses.add(diagnosis);
    }

}
