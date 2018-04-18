package com.pharmacy.healthcare.controller;

import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.domain.PharmacyEmployee;
import com.pharmacy.healthcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pharmacyemployees")
public class PharmacyEmployeeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> updatePatient(@PathVariable("id") long id,
                                           @RequestBody PharmacyEmployee pharmacyEmployee){
        PharmacyEmployee currentUser = (PharmacyEmployee) userRepository.findOne(id);

        if (currentUser == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else{
            currentUser.setUsername(pharmacyEmployee.getUsername());
            currentUser.setFirstname(pharmacyEmployee.getFirstname());
            currentUser.setLastname(pharmacyEmployee.getLastname());
            currentUser.setGender(pharmacyEmployee.getGender());
            currentUser.setStreet(pharmacyEmployee.getStreet());
            currentUser.setCity(pharmacyEmployee.getCity());
            currentUser.setZipcode(pharmacyEmployee.getZipcode());
            currentUser.setHousenumber(pharmacyEmployee.getHousenumber());

            userRepository.save(currentUser);
            return new ResponseEntity<PharmacyEmployee>(currentUser, HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addDoctor(@RequestBody PharmacyEmployee pharmacyEmployee) {

        if (pharmacyEmployee == null) {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        } else {
            pharmacyEmployee.setEnabled(true);
            pharmacyEmployee.setPassword(passwordEncoder.encode(pharmacyEmployee.getPassword()));
            return new ResponseEntity<PharmacyEmployee>(userRepository.save(pharmacyEmployee), HttpStatus.CREATED);
        }
    }
}
