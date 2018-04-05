package com.pharmacy.healthcare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<TimeSlot> timeSlots = new HashSet<>();

//    public void addTimeSlot(TimeSlot timeSlot){
//        timeSlots.add(timeSlot);
//    }

    public void addTimeSlotList(List<TimeSlot> timeSlots)
    {
        timeSlots.addAll(timeSlots);
        System.out.println(timeSlots);
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
