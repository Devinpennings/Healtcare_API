package com.pharmacy.healthcare.controller;


import com.pharmacy.healthcare.TimeSlotGenerator;
import com.pharmacy.healthcare.domain.Admin;
import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.domain.TimeSlot;
import com.pharmacy.healthcare.repository.DoctorRepository;
import com.pharmacy.healthcare.repository.PatientRepository;
import com.pharmacy.healthcare.repository.TimeSlotRepository;
import com.pharmacy.healthcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePatient(@PathVariable("id") long id,
                                           @RequestBody Doctor doctor){
        Doctor currentUser = (Doctor) userRepository.findOne(id);

        if (currentUser == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else{
            currentUser.setUsername(doctor.getUsername());
            currentUser.setFirstname(doctor.getFirstname());
            currentUser.setLastname(doctor.getLastname());

            userRepository.save(currentUser);
            return new ResponseEntity<Doctor>(currentUser, HttpStatus.OK);
        }
    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
//    public ResponseEntity<?> addPatientToDoctor(@PathVariable long id,  @Param("patient_id") long patient_id)
//    {
//        try {
//            Patient patient = patientRepository.findOne(patient_id);
//            Doctor doctor = doctorRepository.findOne(id);
//            doctor.addPatientToDoctor(patient);
//            doctorRepository.save(doctor);
//            return ResponseEntity.ok().build();
//        }
//        catch (Exception e)
//        {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @RequestMapping(value =  "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientsByDoctor(@PathVariable long id)
    {
        try {
            Doctor doctor = doctorRepository.findOne(id);
            return new ResponseEntity<>(doctor.getPatients(), HttpStatus.OK);
        }
        catch (Exception e)
        {
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
                                              @RequestParam(value = "endTime") String pEndTime){

        Date startTime;
        Date endTime;

        try{
            startTime = new Date(Long.parseLong(pStartTime));
            endTime = new Date(Long.parseLong(pEndTime));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Doctor doctor = doctorRepository.findOne(requestId);
        Doctor subtitude = doctorRepository.findOne(pSubtitudeId);

        for(TimeSlot ts : doctor.getTimeSlots(startTime, endTime)){

            if(subtitude.isAvailable(ts.getStartTime()) && subtitude.isAvailable(ts.getEndTime())){
                for(TimeSlot sts : subtitude.getTimeSlots(ts.getStartTime(), ts.getEndTime())){
                    sts.setMappedDoctor(null);
                    sts.setAvailable(true);
                    timeSlotRepository.save(sts);
                }

                ts.setMappedDoctor(subtitude);
                ts.setAvailable(false);
            }else{
                ts.setDoctorAvailable(false);
                ts.getMappedPatient().sendAppointmentCancelMail(ts.getMappedPatient());
            }
            timeSlotRepository.save(ts);

        }

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
