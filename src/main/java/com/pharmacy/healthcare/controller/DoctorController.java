package com.pharmacy.healthcare.controller;

import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.domain.User;
import com.pharmacy.healthcare.repository.DoctorRepository;
import com.pharmacy.healthcare.repository.TimeSlotRepository;
import com.pharmacy.healthcare.repository.UserRepository;
import com.pharmacy.healthcare.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> updateDoctor(@PathVariable("id") long id,
                                           @RequestBody Doctor doctor) {
        return new ResponseEntity<>(doctorService.updateDoctor(id, doctor), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllDoctors(@RequestParam(value = "doctor_id", required = false, defaultValue = "0") long doctor_id) {
        Collection<User> users = doctorService.getAllDoctors(doctor_id);
        if (!users.isEmpty())
        {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/patients/{doctor_id}")
    public ResponseEntity<?> getAllPatientsByDoctorId(@RequestParam(value = "doctor_id") long doctor_id) {
        Collection<Patient> patients = doctorService.getAllPatientsByDoctorId(doctor_id);
        if (!patients.isEmpty())
        {
            return new ResponseEntity<>(patients, HttpStatus.OK);
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addDoctor(@RequestBody Doctor doctor) {
        Doctor addedDoctor = doctorService.addDoctor(doctor);
        if (addedDoctor != null)
        {
            return new ResponseEntity<>(addedDoctor, HttpStatus.OK);
        }
        return ResponseEntity.noContent().build();
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
