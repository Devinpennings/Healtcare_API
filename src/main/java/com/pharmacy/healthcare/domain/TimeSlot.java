package com.pharmacy.healthcare.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "timeslot")
public class TimeSlot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @Column(name = "starttime", nullable = false)
    private java.util.Date startTime;

    @Column(name = "endtime", nullable = false)
    private java.util.Date endTime;

    @Column(name = "note", nullable = true)
    private String note;

    @Column(name = "available", nullable = false)
    private Boolean available = true;

    @Column(name = "approval", nullable = false)
    private Boolean approval = false;

    @ManyToOne(fetch=FetchType.LAZY)
    private Doctor doctor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_user_id")
    private Patient patient;


    public TimeSlot(java.util.Date startTime, java.util.Date endTime, String note, Boolean available) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.note = note;
        this.available = available;
        this.approval = false;
    }

    public java.util.Date getStartTime() {
        return startTime;
    }

    public java.util.Date getEndTime() {
        return endTime;
    }

    public void setStartTime(java.util.Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public TimeSlot(){

    }

    public Boolean getAvailable() {
        return available;
    }

    public long getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
