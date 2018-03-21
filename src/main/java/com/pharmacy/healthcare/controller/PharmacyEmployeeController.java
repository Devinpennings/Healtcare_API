package com.pharmacy.healthcare.controller;

import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.domain.PharmacyEmployee;
import com.pharmacy.healthcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pharmacyemployee")
public class PharmacyEmployeeController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePatient(@PathVariable("id") long id,
                                           @RequestBody PharmacyEmployee pharmacyEmployee){
        PharmacyEmployee currentUser = (PharmacyEmployee) userRepository.findOne(id);

        if (currentUser == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else{
            currentUser.setUsername(pharmacyEmployee.getUsername());
            currentUser.setFirstname(pharmacyEmployee.getFirstname());
            currentUser.setSurname(pharmacyEmployee.getSurname());

            userRepository.save(currentUser);
            return new ResponseEntity<PharmacyEmployee>(currentUser, HttpStatus.OK);
        }
    }
}
