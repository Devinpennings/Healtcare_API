package com.pharmacy.healthcare.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "appointment")
public class Appointment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    @Column(name = "note", nullable = true)
    private String note;

//    @OneToOne
//    @JoinColumn(name = "medicalCompany")
//    private MedicalCompany medicalCompany;

    public Appointment(Long id, Timestamp date, String note) {
        this.id = id;
        this.date = date;
        this.note = note;
    }

    public Appointment(Timestamp date, String note) {
        this.date = date;
        this.note = note;
    }

    public Appointment(){

    }

    public long getId() {
        return id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
