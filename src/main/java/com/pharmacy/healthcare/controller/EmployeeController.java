package com.pharmacy.healthcare.controller;


import com.pharmacy.healthcare.domain.User;
import com.pharmacy.healthcare.repository.UserRepository;
import com.pharmacy.healthcare.services.DoctorService;
import com.pharmacy.healthcare.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllEmployees() {
        Collection<User> employees = employeeService.getAllEmployees();
        if (!employees.isEmpty())
        {
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{user_id}")
    public ResponseEntity<?> findOneEmployee(@PathVariable long user_id)
    {
        User employee = employeeService.findOneEmployee(user_id);
        if (employee != null)
        {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
}
