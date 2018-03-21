package com.pharmacy.healthcare.controller;


import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.DoctorEmployee;
import com.pharmacy.healthcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/doctoremployees")
public class DoctorEmployeeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addDoctor(@RequestBody DoctorEmployee doctorEmployee) {

        if (doctorEmployee == null) {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        } else {
            doctorEmployee.setEnabled(true);
            doctorEmployee.setPassword(passwordEncoder.encode(doctorEmployee.getPassword()));
            return new ResponseEntity<DoctorEmployee>(userRepository.save(doctorEmployee), HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePatient(@PathVariable("id") long id,
                                           @RequestBody DoctorEmployee doctorEmployee){
        DoctorEmployee currentUser = (DoctorEmployee) userRepository.findOne(id);

        if (currentUser == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else{
            currentUser.setUsername(doctorEmployee.getUsername());
            currentUser.setFirstname(doctorEmployee.getFirstname());
            currentUser.setSurname(doctorEmployee.getSurname());

            userRepository.save(currentUser);
            return new ResponseEntity<DoctorEmployee>(currentUser, HttpStatus.OK);
        }
    }





}
