package com.pharmacy.healthcare.controller;


import com.pharmacy.healthcare.TimeSlotGenerator;
import com.pharmacy.healthcare.domain.TimeSlot;
import com.pharmacy.healthcare.services.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Set;

@RestController
@RequestMapping("/timeslots")
public class TimeSlotController {

    @Autowired
    TimeSlotService timeSlotService;

    @Autowired
    TimeSlotGenerator timeSlotGenerator = new TimeSlotGenerator();

    @PostConstruct
    public void init()
    {
        Set<TimeSlot> timeSlotList = timeSlotGenerator.generateTimeSlots(2, 9, 10, 10);
        timeSlotGenerator.addTimeSlotsToDoctors(timeSlotList);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getTimeSlots()
    {
        try
        {
            return new ResponseEntity<>(timeSlotService.getTimeSlots(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return ResponseEntity.noContent().build();
        }
    }






}
