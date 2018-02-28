package com.pharmacy.healthcare.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "patients")
public class Patient extends User {

    @Column(name = "age", nullable = false)
    protected Long age;

}
