package com.pharmacy.healthcare.controller;


import com.pharmacy.healthcare.TimeSlotGenerator;
import com.pharmacy.healthcare.domain.TimeSlot;
import com.pharmacy.healthcare.repository.DoctorRepository;
import com.pharmacy.healthcare.repository.PatientRepository;
import com.pharmacy.healthcare.repository.TimeSlotRepository;
import com.pharmacy.healthcare.services.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
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
    public ResponseEntity<?> getTimeSlots()
    {
        return new ResponseEntity<>(timeSlotRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/available")
    public ResponseEntity<?> getDoctorAppointments(@RequestParam(value = "doctor_id", required = false, defaultValue = "0") long doctor_id,@RequestParam(value = "availability", required = false, defaultValue = "2") long availability){
        try{
            if (availability == 2 && doctor_id == 0) {
                //todo alle availabale
                return new ResponseEntity<>(timeSlotRepository.findAllByAvailableIsTrue(), HttpStatus.OK);
            }
            else if (availability == 1){
                Set<TimeSlot> returnSet = new HashSet<>();

                for (TimeSlot ts : timeSlotRepository.findAllByDoctorId(doctor_id)) {
                    if(ts.getAvailable()){
                        returnSet.add(ts);
                    }
                }
                return new ResponseEntity<>(returnSet, HttpStatus.OK);
            }
            else {
                Set<TimeSlot> returnSet = new HashSet<>();

                for (TimeSlot ts : timeSlotRepository.findAllByDoctorId(doctor_id)) {
                    if(!ts.getAvailable()){
                        returnSet.add(ts);
                    }
                }
                return new ResponseEntity<>(returnSet, HttpStatus.OK);
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "/approved")
    public ResponseEntity<?> getApproved(@RequestParam(value = "doctor_id", required = false, defaultValue = "0") long doctor_id, @RequestParam(value = "approval", required = false, defaultValue = "2") long approval) {
        try {
            if (approval == 2 && doctor_id ==0) {
                return new ResponseEntity<>(timeSlotRepository.findAllByApprovalIsTrue(), HttpStatus.OK);
            } else if (approval == 1) {
                Set<TimeSlot> returnSet = new HashSet<>();

                for (TimeSlot ts : timeSlotRepository.findAllByDoctorId(doctor_id)) {
                    if (ts.getApproval()) {
                        returnSet.add(ts);
                    }
                }
                return new ResponseEntity<>(returnSet, HttpStatus.OK);
            }
            else {
                Set<TimeSlot> returnSet = new HashSet<>();

                for (TimeSlot ts : timeSlotRepository.findAllByDoctorId(doctor_id)) {
                    if (!ts.getApproval()) {
                        returnSet.add(ts);
                    }
                }
                return new ResponseEntity<>(returnSet, HttpStatus.OK);
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }
    }


    @RequestMapping(value = "/{timeslot_id}", method = RequestMethod.POST)
    public ResponseEntity<?> addTimeSlot(@PathVariable long timeslot_id, @RequestParam(value = "user_id") long user_id, @RequestParam(value = "note") String note)
    {
        timeSlotService.reserveTimeSlot(timeslot_id, user_id, note);
        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "/{timeslot_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> clearTimeSlot(@PathVariable long timeslot_id)
    {
        try {
            timeSlotService.clearTimeSlot(timeslot_id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(value = "/approve/{timeslot_id}", method = RequestMethod.POST)
    public ResponseEntity<?> approveTimeSlot(@PathVariable long timeslot_id, @RequestParam(value = "approval") Boolean approval)
    {
        try {
            if (approval)
            {
                timeSlotService.approveTimeSlot(timeslot_id);
                return ResponseEntity.ok().build();
            }
            else {
                timeSlotService.disapproveTimeSlot(timeslot_id);
                return ResponseEntity.ok().build();
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.noContent().build();
        }
    }
}
