package com.pharmacy.healthcare;

import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.Role;
import com.pharmacy.healthcare.domain.TimeSlot;
import com.pharmacy.healthcare.domain.User;
import com.pharmacy.healthcare.repository.DoctorRepository;
import com.pharmacy.healthcare.repository.TimeSlotRepository;
import com.pharmacy.healthcare.repository.UserRepository;
import com.pharmacy.healthcare.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class TimeSlotGenerator {

    private Set<TimeSlot> timeSlots;

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Autowired
    @Qualifier("doctorService")
    private DoctorService doctorService;

    public Set<TimeSlot> generateTimeSlots(int weeks, int startTime, int endTime, int consultTime){
        timeSlots = new HashSet<>();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
//        c.add(Calendar.WEEK_OF_MONTH, 1);
        c.add(Calendar.HOUR, startTime-1);
        c.set(Calendar.MINUTE, 60-consultTime);
        c.set(Calendar.SECOND, 0);
        System.out.println("timeslots will be generated from :" + c.getTime());

        Calendar calendarEndFix = Calendar.getInstance();
        calendarEndFix.setTime(c.getTime());
        calendarEndFix.add(Calendar.MINUTE, consultTime);
        TimeSlot timeslot = new TimeSlot(c.getTime(), calendarEndFix.getTime(), null, true);

        //adding timeslots
        Date start = timeslot.getStartTime();
        Date end = timeslot.getEndTime();
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(start);
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(end);


        int weekAmount = 5*(endTime-startTime)*(60/consultTime) + weeks;
        weeks = weeks * weekAmount;

        for(int i=0; i<weeks; i++){

            if(calendarStart.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && calendarEnd.get(Calendar.HOUR_OF_DAY) == endTime) {
                calendarStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                calendarStart.add(Calendar.WEEK_OF_YEAR, 1);
                calendarStart.set(Calendar.HOUR_OF_DAY, startTime-1);
                calendarStart.set(Calendar.MINUTE, 60-consultTime);
                calendarEnd.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                calendarEnd.add(Calendar.WEEK_OF_YEAR, 1);
                calendarEnd.set(Calendar.HOUR_OF_DAY, startTime);
                calendarEnd.set(Calendar.MINUTE, 0);
            }

            //check if endtime is equal to 12:00, add a day and fix time
            if(calendarEnd.get(Calendar.HOUR_OF_DAY) == endTime){
                calendarStart = addDays(start, 1);
                calendarStart.set(Calendar.HOUR_OF_DAY, startTime-1);
                calendarStart.set(Calendar.MINUTE, 60-consultTime);
                calendarEnd = addDays(end, 1);
                calendarEnd.set(Calendar.HOUR_OF_DAY, startTime);
                calendarEnd.set(Calendar.MINUTE, 0);
            }
            //check if day of the week is equal to friday and end time = 12:00

            else{
                calendarStart.add(Calendar.MINUTE, consultTime);
                calendarEnd.add(Calendar.MINUTE, consultTime);
                start = calendarStart.getTime();
                end = calendarEnd.getTime();
                System.out.println(calendarStart.getTime() + " " + calendarEnd.getTime());
                timeSlots.add(new TimeSlot(start, end, null, true));
            }
        }
        return timeSlots;
    }

    public void addTimeSlotsToDoctors(Set<TimeSlot> timeSlotlist){
        doctorService.addTimeSlotsToDoctors(timeSlotlist);
    }


    public Calendar addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal;
    }



}
