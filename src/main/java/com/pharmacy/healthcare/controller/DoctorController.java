package com.pharmacy.healthcare.controller;


import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    UserRepository userRepository;

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
            currentUser.setSurname(doctor.getSurname());

            userRepository.save(currentUser);
            return new ResponseEntity<Doctor>(currentUser, HttpStatus.OK);
        }
    }

}
