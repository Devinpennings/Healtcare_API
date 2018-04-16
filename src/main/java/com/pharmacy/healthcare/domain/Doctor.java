package com.pharmacy.healthcare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
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

    public Set<TimeSlot> getTimeSlots(Date startTime, Date endTime) {
        Set<TimeSlot> returnSet = new HashSet<>();

        for (TimeSlot ts : this.timeSlots) {
            if((ts.getStartTime().after(startTime) && ts.getStartTime().before(endTime)) ||
                    (ts.getEndTime().after(startTime) && ts.getEndTime().before(endTime)) ||
                    (ts.getEndTime().compareTo(endTime) == 0 && ts.getStartTime().compareTo(startTime) == 0)
                    ) {

                returnSet.add(ts);

            }
        }

        return returnSet;
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

    public boolean isAvailable(Date timeStamp){
        for (TimeSlot ts : this.timeSlots) {
            if(((timeStamp.after(ts.getStartTime()) && timeStamp.before(ts.getEndTime())) ||
                    (ts.getEndTime().compareTo(timeStamp) == 0 && ts.getStartTime().compareTo(timeStamp) == 0)) && ts.getAvailable() == false) {
                return false;
            }
        }
        return true;
    }
}
