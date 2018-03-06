package com.pharmacy.healthcare.domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "note", nullable = true)
    private String note;

    @OneToOne
    @JoinColumn(name = "medicalCompany")
    private MedicalCompany medicalCompany;

//    @ManyToOne(fetch=FetchType.EAGER)
//    private Doctor doctor;

    public Appointment(Long id, Date date, String note) {
        this.id = id;
        this.date = date;
        this.note = note;
    }

    public Appointment(Date date, String note) {
        this.date = date;
        this.note = note;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
