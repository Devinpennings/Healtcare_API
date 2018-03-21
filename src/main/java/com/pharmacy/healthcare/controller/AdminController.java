package com.pharmacy.healthcare.controller;

import com.pharmacy.healthcare.domain.Admin;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.repository.UserRepository;
import com.pharmacy.healthcare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") long id,
                                        @RequestBody Admin admin){
        Admin currentUser = (Admin) userRepository.findOne(id);

        if (currentUser == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else{
            currentUser.setUsername(admin.getUsername());
            currentUser.setFirstname(admin.getFirstname());
            currentUser.setSurname(admin.getSurname());

            userRepository.save(currentUser);
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
        }
    }




}
