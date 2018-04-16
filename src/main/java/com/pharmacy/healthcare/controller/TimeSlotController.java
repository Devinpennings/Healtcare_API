package com.pharmacy.healthcare.controller;


import com.pharmacy.healthcare.TimeSlotGenerator;
import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.domain.TimeSlot;
import com.pharmacy.healthcare.repository.DoctorRepository;
import com.pharmacy.healthcare.repository.PatientRepository;
import com.pharmacy.healthcare.repository.TimeSlotRepository;
import com.pharmacy.healthcare.repository.UserRepository;
import com.pharmacy.healthcare.services.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/timeslots")
public class TimeSlotController {

    @Autowired
    TimeSlotService timeSlotService;

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    TimeSlotGenerator timeSlotGenerator = new TimeSlotGenerator();

    @PostConstruct
    public void init()
    {
        Set<TimeSlot> timeSlotList = timeSlotGenerator.generateTimeSlots(1, 9, 10, 10);
        timeSlotGenerator.addTimeSlotsToDoctors(timeSlotList);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getTimeSlots(@RequestParam(value = "availability", required = false, defaultValue = "2") int availability)
    {
        try
        {
            if (availability == 2) {
                return new ResponseEntity<>(timeSlotService.getTimeSlots(), HttpStatus.OK);
            }else{
                Set<TimeSlot> returnSet = new HashSet<>();

                for (TimeSlot ts : timeSlotService.getTimeSlots()) {
                    if(ts.getAvailable() == (availability != 0)){

                        returnSet.add(ts);

                    }
                }

                return new ResponseEntity<>(returnSet, HttpStatus.OK);
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(value = "/{doctor_id}", method = RequestMethod.POST)
    public ResponseEntity<?> addTimeSlot(@PathVariable long doctor_id, @RequestParam(value = "userid") long userid, @RequestParam(value = "starttime") String starttime)
    {
        timeSlotService.reserveTimeSlot(userid, doctor_id, starttime);
        return ResponseEntity.ok().build();
    }






}
