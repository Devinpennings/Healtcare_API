package com.pharmacy.healthcare.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "pharmacy")
public class Pharmacy extends MedicalCompany implements Serializable {


    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Medicine> medicines;

    public Pharmacy(Long id, String name, String street, String city, String number, String telNumber, List<Medicine> medicines) {
        super(id, name, street, city, number, telNumber);
        this.medicines = medicines;
    }

    public void addMedicine(Medicine medicine)
    {
        medicines.add(medicine);
    }
}
