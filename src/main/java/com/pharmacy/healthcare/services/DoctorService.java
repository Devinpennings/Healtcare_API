package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.TimeSlot;
import com.pharmacy.healthcare.repository.DoctorRepository;
import com.pharmacy.healthcare.repository.TimeSlotRepository;
import com.pharmacy.healthcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service("doctorService")
public class DoctorService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Autowired
    DoctorRepository doctorRepository;

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

    public Boolean requestSubtitude(long request_id, long subtitude_id, String starttime, String endtime) {
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
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
