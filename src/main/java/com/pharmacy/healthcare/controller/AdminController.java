package com.pharmacy.healthcare.controller;

import com.pharmacy.healthcare.domain.Admin;
import com.pharmacy.healthcare.repository.UserRepository;
import com.pharmacy.healthcare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
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
            currentUser.setLastname(admin.getLastname());

            userRepository.save(currentUser);
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllEmployees()
    {
        return new ResponseEntity<>(userRepository.findAllByType(), HttpStatus.OK);
    }

}
