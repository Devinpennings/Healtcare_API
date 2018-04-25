package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.DoctorEmployee;
import com.pharmacy.healthcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("doctorEmployeeService")
public class DoctorEmployeeService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DoctorEmployee addDoctorEmployee(DoctorEmployee doctorEmployee)
    {
        if (doctorEmployee == null) {
            return null;
        } else {
            System.out.print("passed");
            doctorEmployee.setEnabled(true);
            doctorEmployee.setPassword(passwordEncoder.encode(doctorEmployee.getPassword()));
            return userRepository.save(doctorEmployee);
        }
    }

    public DoctorEmployee updateDoctorEmployee(long id, DoctorEmployee doctorEmployee)
    {
        DoctorEmployee currentUser = (DoctorEmployee) userRepository.findOne(id);

        if (currentUser == null) {
            return null;
        }
        else{
            currentUser.setUsername(doctorEmployee.getUsername());
            currentUser.setFirstname(doctorEmployee.getFirstname());
            currentUser.setLastname(doctorEmployee.getLastname());
            currentUser.setGender(doctorEmployee.getGender());
            currentUser.setStreet(doctorEmployee.getStreet());
            currentUser.setCity(doctorEmployee.getCity());
            currentUser.setZipcode(doctorEmployee.getZipcode());
            currentUser.setHousenumber(doctorEmployee.getHousenumber());

            userRepository.save(currentUser);
            return currentUser;
        }
    }

}
