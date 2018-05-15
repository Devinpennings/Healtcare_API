package com.pharmacy.healthcare.domain;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Order implements Serializable {

    private Doctor mappedDoctorOrder;

    private Patient mappedPatientOrder;

    private List<Medicine> medicines;

    @Column(name = "token")
    private String token;
}
