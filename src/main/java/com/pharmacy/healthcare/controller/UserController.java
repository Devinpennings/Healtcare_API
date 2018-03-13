package com.pharmacy.healthcare.controller;

import com.pharmacy.healthcare.domain.User;
import com.pharmacy.healthcare.repository.UserRepository;
import com.pharmacy.healthcare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> getUsers()
    {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public ResponseEntity<User> getUser(@PathVariable long id)
//    {
//        User user = userRepository.findOne(id);
//        if (user != null)
//        {
//            return new ResponseEntity<>(user, HttpStatus.OK);
//        }
//        else return null;
//    }

//    @RequestMapping(method = RequestMethod.POST)
//    ResponseEntity<Void> save(@RequestBody User input)
//    {
//        if (userService.userExists(input.getUsername()))
//        {
//
//        }
//    }
}
