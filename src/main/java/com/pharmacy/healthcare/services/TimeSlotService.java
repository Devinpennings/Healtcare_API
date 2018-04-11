package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.TimeSlot;
import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TimeSlotService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    public TimeSlot save(TimeSlot timeSlot, long patientId, long doctorId) {
//        if (timeSlot != null && doctorId == -1) {
//            Patient patient = patientRepository.findOne(patientId);
//            Doctor doctor = patient.getDoctor();
//            patient.addAppointment(timeSlot);
//            doctor.addAppointment(timeSlot);
//            patientRepository.save(patient);
//            doctorRepository.save(doctor);
//            return timeSlot;
//        } else if (timeSlot != null && doctorId != -1) {
//            Patient patient = patientRepository.findOne(patientId);
//            Doctor doctor = doctorRepository.findOne(doctorId);
//            patient.addAppointment(timeSlot);
//            doctor.addAppointment(timeSlot);
//            patientRepository.save(patient);
//            doctorRepository.save(doctor);
//        } else {
//            return null;
//        }
        return null;
    }

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



}
