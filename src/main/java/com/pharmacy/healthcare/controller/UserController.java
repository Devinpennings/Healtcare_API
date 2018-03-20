package com.pharmacy.healthcare.controller;

import com.pharmacy.healthcare.domain.*;
import com.pharmacy.healthcare.repository.UserRepository;
import com.pharmacy.healthcare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
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
    public ResponseEntity<Collection<User>> getUsers() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getType());
        if (user != null) {
            return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }



//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//    public ResponseEntity<User> updateUser(@PathVariable("id") long id,
//                                           @RequestBody User user) {
//        User u = userRepository.findOne(id);
//        if(u != null){
//            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

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
