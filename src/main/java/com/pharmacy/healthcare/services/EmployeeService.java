package com.pharmacy.healthcare.services;


import com.pharmacy.healthcare.domain.User;
import com.pharmacy.healthcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service("employeeService")
public class EmployeeService
{
    @Autowired
    UserRepository userRepository;

    public Collection<User> getAllEmployees()
    {
        try {
            return userRepository.findAllEmployees();
        }
        catch (Exception e)
        {
            return Collections.emptyList();
        }
    }

    public User findOneEmployee(long user_id)
    {
        try {
            return userRepository.findOne(user_id);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
