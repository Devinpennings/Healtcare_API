package com.pharmacy.healthcare.domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "prescription")
public class Prescription {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER)
    private Medicine medicine;

    @Column(name = "unitOfMeasure", nullable = false)
    private String unitOfMeasure;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "instructions", nullable = false)
    private String Instructions;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne(fetch=FetchType.EAGER)
    private Patient patient;



}
