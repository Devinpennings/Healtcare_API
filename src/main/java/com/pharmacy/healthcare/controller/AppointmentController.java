package com.pharmacy.healthcare.controller;


import com.pharmacy.healthcare.domain.Appointment;
import com.pharmacy.healthcare.domain.Diagnosis;
import com.pharmacy.healthcare.repository.AppointmentRepository;
import com.pharmacy.healthcare.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;



    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    public ResponseEntity<?> addAppointment(@PathVariable long userId, @RequestBody String date,@RequestBody String note, @RequestBody Long doctorId)
    {
        Timestamp ts = Timestamp.valueOf(date);
        Appointment appointment = new Appointment(ts, note);
        return new ResponseEntity<>(appointmentService.save(appointment, userId, doctorId), HttpStatus.CREATED);
    }





}
