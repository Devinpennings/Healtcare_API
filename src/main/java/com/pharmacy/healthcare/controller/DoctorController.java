package com.pharmacy.healthcare.controller;

import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.repository.DoctorRepository;
import com.pharmacy.healthcare.repository.TimeSlotRepository;
import com.pharmacy.healthcare.repository.UserRepository;
import com.pharmacy.healthcare.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DoctorService doctorService;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> updateDoctor(@PathVariable("id") long id,
                                           @RequestBody Doctor doctor) {
        return new ResponseEntity<>(doctorService.updateDoctor(id, doctor), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllDoctors(@RequestParam(value = "doctor_id", required = false, defaultValue = "0") long doctor_id) {
        try {
            if (doctor_id != 0) {
                return new ResponseEntity<>(doctorRepository.findOne(doctor_id), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(userRepository.findAllEmployees(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/patients/{doctor_id}")
    public ResponseEntity<?> getAllPatientsByDoctorId(@RequestParam(value = "doctor_id") long doctor_id) {
        try {
            Doctor doctor = doctorRepository.findOne(doctor_id);
            return new ResponseEntity<>(doctor.getPatients(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addDoctor(@RequestBody Doctor doctor) {
        if (doctor == null) {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        } else {
            doctor.setEnabled(true);
            doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
            return new ResponseEntity<>(userRepository.save(doctor), HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "requestSubtitude/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> requestSubtitude(@PathVariable("id") long requestId,
                                              @RequestParam(value = "subtitudeId") long pSubtitudeId,
                                              @RequestParam(value = "startTime") String pStartTime,
                                              @RequestParam(value = "endTime") String pEndTime) {

        try {
            doctorService.requestSubtitude(requestId, pSubtitudeId, pStartTime, pEndTime);
            return ResponseEntity.ok().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.notFound().build() ;
        }


    }
}
