package com.pharmacy.healthcare;

import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.TimeSlot;
import com.pharmacy.healthcare.repository.DoctorRepository;
import com.pharmacy.healthcare.repository.TimeSlotRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@SpringBootApplication
@EnableAutoConfiguration
public class ServletInitializer extends SpringBootServletInitializer {



//    TimeSlotGenerator timeSlotGenerator = new TimeSlotGenerator();


    public static void main(String[] args) {
        SpringApplication.run(ServletInitializer.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ServletInitializer.class);
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void addTimeSlotsToDoctors() {
//        List<TimeSlot> timeSlotList = timeSlotGenerator.generateTimeSlots(4, 9, 13, 10);
//        timeSlotGenerator.addTimeSlotsToDoctors(timeSlotList);
//    }










}
