package com.pharmacy.healthcare.controller;


import com.pharmacy.healthcare.domain.TimeSlot;
import com.pharmacy.healthcare.services.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/timeslots")
public class TimeSlotController {

    @Autowired
    TimeSlotService timeSlotService;



//    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
//    public ResponseEntity<?> addAppointment(@PathVariable long userId, @RequestBody TimeSlot timeSlot)
//    {
//        return new ResponseEntity<?>();
//    }





}
