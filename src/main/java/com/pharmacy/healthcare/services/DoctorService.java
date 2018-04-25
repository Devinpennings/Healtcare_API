package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.domain.TimeSlot;
import com.pharmacy.healthcare.domain.User;
import com.pharmacy.healthcare.repository.DoctorRepository;
import com.pharmacy.healthcare.repository.TimeSlotRepository;
import com.pharmacy.healthcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("doctorService")
public class DoctorService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("patientService")
    PatientService patientService;

    public void addTimeSlotsToDoctors(Set<TimeSlot> timeSlotlist) {
        Collection<Doctor> doctors = userRepository.findAllDoctors();
        for (Doctor doctor : doctors) {
            doctor.addTimeSlotList(timeSlotlist);
            userRepository.save(doctor);
        }
    }

    public Doctor updateDoctor(long id, Doctor doctor) {
        Doctor currentUser = (Doctor) userRepository.findOne(id);

        if (currentUser == null) {
            return null;
        } else {
            currentUser.setUsername(doctor.getUsername());
            currentUser.setFirstname(doctor.getFirstname());
            currentUser.setLastname(doctor.getLastname());
            currentUser.setGender(doctor.getGender());
            currentUser.setStreet(doctor.getStreet());
            currentUser.setCity(doctor.getCity());
            currentUser.setZipcode(doctor.getZipcode());
            currentUser.setHousenumber(doctor.getHousenumber());
            return userRepository.save(currentUser);
        }
    }

    public Collection<User> getAllDoctors(long doctor_id)
    {
        try {
            if (doctor_id != 0) {
                return Collections.singletonList(doctorRepository.findOne(doctor_id));
            } else {
                return userRepository.findAllEmployees();
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Collection<Patient> getAllPatientsByDoctorId(long doctor_id)
    {
        try {
            Doctor doctor = doctorRepository.findOne(doctor_id);
            return doctor.getPatients();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Doctor addDoctor(Doctor doctor)
    {
        if (doctor == null) {
            return null;
        } else {
            doctor.setEnabled(true);
            doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
            return userRepository.save(doctor);
        }
    }

    public void requestSubtitude(long request_id, long subtitude_id, String starttime, String endtime) {
        Date startTime;
        Date endTime;

        try {
            startTime = new Date(Long.parseLong(starttime));
            endTime = new Date(Long.parseLong(endtime));


            Doctor doctor = doctorRepository.findOne(request_id);
            Doctor subtitude = doctorRepository.findOne(subtitude_id);

            for (TimeSlot ts : doctor.getTimeSlots(startTime, endTime)) {

                if (subtitude.isAvailable(ts.getStartTime()) && subtitude.isAvailable(ts.getEndTime())) {
                    for (TimeSlot sts : subtitude.getTimeSlots(ts.getStartTime(), ts.getEndTime())) {
                        sts.setMappedDoctor(null);
                        sts.setAvailable(true);
                        timeSlotRepository.save(sts);
                    }

                    ts.setMappedDoctor(subtitude);
                    ts.setAvailable(false);
                } else {
                    ts.setDoctorAvailable(false);
                    patientService.sendAppointmentCancelMail(ts.getMappedPatient());
                }
                timeSlotRepository.save(ts);
            }
        } catch (Exception e) {
        }
    }
}
