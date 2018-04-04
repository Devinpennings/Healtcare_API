package com.pharmacy.healthcare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
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
    private Set<Appointment> appointments = new HashSet<>();

    public void addAppointment(Appointment appointment){
        appointments.add(appointment);
    }

    public void removeAppointment(Appointment appointment){
        appointments.remove(appointment);
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
