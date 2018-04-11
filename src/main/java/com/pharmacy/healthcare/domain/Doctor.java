package com.pharmacy.healthcare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

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
            cascade = CascadeType.ALL,
            mappedBy = "mappedDoctor",
            fetch = FetchType.LAZY
    )
    private Set<Patient> patients = new HashSet<>();

    @JsonIgnore
    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            mappedBy = "mappedDoctor",
            fetch = FetchType.EAGER
    )
    @OrderBy("id")
    @Where(clause = "starttime >= CURRENT_TIMESTAMP")
    private Set<TimeSlot> timeSlots = new HashSet<>();

    public void addTimeSlotList(Set<TimeSlot> timeSlots)
    {
        for (TimeSlot t: timeSlots
             ) {
            this.timeSlots.add(t);
            if (t.getMappedDoctor() != this)
            {
                t.setMappedDoctor(this);
            }
        }
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
        if(patient.getDoctor() != this)
        {
            patient.setMappedDoctor(this);
        }
    }

    public Set<Patient> getPatients()
    {
        return patients;
    }
}
