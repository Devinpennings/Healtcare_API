package com.pharmacy.healthcare.controller;

import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.domain.User;
import com.pharmacy.healthcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<Patient>> getUsers()
    {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable long id)
    {
        User user = userRepository.findOne(id);
        if (user != null)
        {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else return null;
    }
}
