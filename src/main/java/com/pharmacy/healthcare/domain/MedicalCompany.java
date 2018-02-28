package com.pharmacy.healthcare.domain;

import javax.persistence.*;

@MappedSuperclass
@Table(name = "medicalCompany")
public abstract class MedicalCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "telNumber", nullable = false)
    private String telNumber;

    public MedicalCompany(Long id, String name, String street, String city, String number, String telNumber) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.number = number;
        this.telNumber = telNumber;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getNumber() {
        return number;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }
}
