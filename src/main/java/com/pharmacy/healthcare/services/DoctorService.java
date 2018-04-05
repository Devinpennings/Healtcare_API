package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.TimeSlot;
import com.pharmacy.healthcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service("doctorService")
public class DoctorService {
    @Autowired
    private UserRepository userRepository;

    public void addTimeSlotsToDoctors(List<TimeSlot> timeSlotlist){
        Collection<Doctor> doctors = userRepository.findAllDoctors();
        for(Doctor doctor: doctors){
            doctor.addTimeSlotList(timeSlotlist);
            userRepository.save(doctor);
        }
        //userRepository.save(doctors);
    }
}
