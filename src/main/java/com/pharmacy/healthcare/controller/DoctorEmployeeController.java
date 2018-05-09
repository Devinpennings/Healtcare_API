package com.pharmacy.healthcare.controller;


import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.DoctorEmployee;
import com.pharmacy.healthcare.repository.UserRepository;
import com.pharmacy.healthcare.services.DoctorEmployeeService;
import com.pharmacy.healthcare.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctoremployees")
public class DoctorEmployeeController {

    @Autowired
    DoctorEmployeeService doctorEmployeeService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addDoctorEmployee(@RequestBody DoctorEmployee doctorEmployee) {
        DoctorEmployee createdDoctorEmployee = doctorEmployeeService.addDoctorEmployee(doctorEmployee);
        if (createdDoctorEmployee != null)
        {
            return new ResponseEntity<>(createdDoctorEmployee, HttpStatus.OK);
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> updateDoctorEmployee(@PathVariable("id") long id,
                                           @RequestBody DoctorEmployee doctorEmployee){
        DoctorEmployee updatedDoctorEmployee = doctorEmployeeService.updateDoctorEmployee(id, doctorEmployee);
        if (updatedDoctorEmployee != null)
        {
            return new ResponseEntity<>(updatedDoctorEmployee, HttpStatus.OK);
        }
        return ResponseEntity.noContent().build();
    }
}
