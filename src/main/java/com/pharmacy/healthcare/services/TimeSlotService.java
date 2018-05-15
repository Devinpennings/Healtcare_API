package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.TimeSlot;
import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    public Set<TimeSlot> getTimeSlots()
//    {
//        Set<TimeSlot> timeSlots = new HashSet<>();
//        Iterable<Doctor> doctors = doctorRepository.findAll();
//        for (Doctor doc: doctors
//             ) {
//            timeSlots.addAll(doc.getTimeSlots());
//        }
//        return timeSlots;
//    }

    public void reserveTimeSlot(long timeslot_id, long user_id, String note)
    {
        TimeSlot timeSlot = timeSlotRepository.findOne(timeslot_id);
        timeSlot.setMappedPatient(patientRepository.findOne(user_id));
        timeSlot.setAvailable(false);
        timeSlot.setNote(note);
        timeSlotRepository.save(timeSlot);
    }

    public void clearTimeSlot(long timeslot_id)
    {
        TimeSlot timeSlot = timeSlotRepository.findOne(timeslot_id);
        timeSlot.removeMappedPatient();//setpatient null
        timeSlot.setAvailable(true);
        timeSlot.setNote(null);
        timeSlotRepository.save(timeSlot);
    }

    public void approveTimeSlot(long timeslot_id)
    {
        TimeSlot timeSlot = timeSlotRepository.findOne(timeslot_id);
        timeSlot.setApproval(true);
        timeSlotRepository.save(timeSlot);
    }

    public Collection<TimeSlot> getDoctorAppointments(long availability, long doctor_id) {
            if (availability == 2 && doctor_id == 0) {
                return timeSlotRepository.findAllByAvailableIsFalse();
            } else if (availability == 1 && doctor_id > 0) {
                Collection<TimeSlot> returnSet = new HashSet<>();
                for (TimeSlot ts : timeSlotRepository.findAllByDoctorId(doctor_id)) {
                    //todo in de query afhandelen
                    if (ts.getAvailable()) {
                        returnSet.add(ts);
                    }
                }
                return returnSet;
            } else {
                Collection<TimeSlot> returnSet = new HashSet<>();
                for (TimeSlot ts : timeSlotRepository.findAllByDoctorId(doctor_id)) {
                    //todo in de query afhandelen
                    if (!ts.getAvailable()) {
                        returnSet.add(ts);
                    }
                }
                return returnSet;

            }
    }

    public Collection<TimeSlot> getAllTimeslots(){
        return timeSlotRepository.findAll();
    }

    //todo refacter, zet in query
    public Collection<TimeSlot> getApproved(long approval, long doctor_id){
        if (approval == 2 && doctor_id ==0) {
            return timeSlotRepository.findAllByApprovalIsTrue();
        } else if (approval == 1) {
            Set<TimeSlot> returnSet = new HashSet<>();
            for (TimeSlot ts : timeSlotRepository.findAllByDoctorId(doctor_id)) {
                if (ts.getApproval()) {
                    returnSet.add(ts);
                }
            }
            return returnSet;
        }
        else {
            Set<TimeSlot> returnSet = new HashSet<>();
            for (TimeSlot ts : timeSlotRepository.findAllByDoctorId(doctor_id)) {
                if (!ts.getApproval()) {
                    returnSet.add(ts);
                }
            }
            return returnSet;
        }
    }


    public void disapproveTimeSlot(long timeslot_id)
    {
        TimeSlot timeSlot = timeSlotRepository.findOne(timeslot_id);
        timeSlot.setApproval(false);
        timeSlotRepository.save(timeSlot);
    }

    public void resetTimeSlot(long timeslot_id){
        TimeSlot timeSlot = timeSlotRepository.findOne(timeslot_id);
        timeSlot.setMappedPatient(null);
        timeSlot.setApproval(false);
        timeSlot.setAvailable(true);
    }



}
