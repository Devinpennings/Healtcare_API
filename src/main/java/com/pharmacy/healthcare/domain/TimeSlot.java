package com.pharmacy.healthcare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctors_user_id", referencedColumnName = "user_id")
    private Doctor mappedDoctor;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctors_user_id", referencedColumnName = "user_id")
    public Doctor getMappedDoctor() {
        return mappedDoctor;
    }

    public void setMappedDoctor(Doctor mappedDoctor) {
        this.mappedDoctor = mappedDoctor;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patients_user_id", referencedColumnName = "user_id")
    private Patient mappedPatient;

    public void setMappedPatient(Patient mappedPatient) {
        this.mappedPatient = mappedPatient;
        if (mappedPatient.getTimeSlots().contains(this))
        {
            mappedPatient.setMappedTimeSlot(this);
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patients_user_id", referencedColumnName = "user_id")
    public Patient getMappedPatient() {
        return mappedPatient;
    }

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

    public TimeSlot() {
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
