package com.pharmacy.healthcare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@DiscriminatorValue("doctor")
public class Doctor extends User implements Serializable{

    public Doctor() {
    }

    @Override
    public String getType() {
        return "doctor";
    }

    @JsonIgnore
    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Set<Patient> patients = new HashSet<>();

    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @OrderBy("id")
    @Where(clause = "starttime >= CURRENT_TIMESTAMP")
    private Set<TimeSlot> timeSlots = new HashSet<>();

    public void addTimeSlotList(Set<TimeSlot> timeSlots)
    {
        this.timeSlots = timeSlots;
        System.out.println(timeSlots);
    }

    public Set<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void removeTimeSlot(TimeSlot timeSlot){
        timeSlots.remove(timeSlot);
    }

    public void addPatientToDoctor(Patient patient)
    {
        patients.add(patient);
    }

    public Set<Patient> getPatients()
    {
        return patients;
    }
}
