package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.Appointment;
import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment save(Appointment appointment, long patientId, long doctorId) {
        if (appointment != null && doctorId == -1) {
            Patient patient = patientRepository.findOne(patientId);
            Doctor doctor = patient.getDoctor();
            patient.addAppointment(appointment);
            doctor.addAppointment(appointment);
            patientRepository.save(patient);
            doctorRepository.save(doctor);
            return appointment;
        } else if (appointment != null && doctorId != -1) {
            Patient patient = patientRepository.findOne(patientId);
            Doctor doctor = doctorRepository.findOne(doctorId);
            patient.addAppointment(appointment);
            doctor.addAppointment(appointment);
            patientRepository.save(patient);
            doctorRepository.save(doctor);
        } else {
            return null;
        }
        return null;
    }



}
