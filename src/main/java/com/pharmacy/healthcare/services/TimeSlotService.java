package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.TimeSlot;
import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

@Service
public class TimeSlotService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    public Set<TimeSlot> getTimeSlots()
    {
        Set<TimeSlot> timeSlots = new HashSet<>();
        Iterable<Doctor> doctors = doctorRepository.findAll();
        for (Doctor doc: doctors
             ) {
            timeSlots.addAll(doc.getTimeSlots());
        }
        return timeSlots;
    }

    public void reserveTimeSlot(long timeslot_id, long user_id)
    {
        TimeSlot timeSlot = timeSlotRepository.findOne(timeslot_id);
        timeSlot.setMappedPatient(patientRepository.findOne(user_id));
        timeSlot.setAvailable(false);
        timeSlotRepository.save(timeSlot);
    }

    public void clearTimeSlot(long timeslot_id)
    {
        TimeSlot timeSlot = timeSlotRepository.findOne(timeslot_id);
        timeSlot.removeMappedPatient();
        timeSlot.setAvailable(true);
        timeSlotRepository.save(timeSlot);
    }

    public void approveTimeSlot(long timeslot_id)
    {
        TimeSlot timeSlot = timeSlotRepository.findOne(timeslot_id);
        timeSlot.setApproval(true);
        timeSlotRepository.save(timeSlot);
    }

    public void disapproveTimeSlot(long timeslot_id)
    {
        TimeSlot timeSlot = timeSlotRepository.findOne(timeslot_id);
        timeSlot.setApproval(false);
        timeSlotRepository.save(timeSlot);
    }



}
