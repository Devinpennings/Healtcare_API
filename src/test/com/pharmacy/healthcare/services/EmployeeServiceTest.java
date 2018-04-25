package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.DoctorEmployee;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.domain.User;
import com.pharmacy.healthcare.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployees() {

        List<User> employees = new ArrayList<>();
        employees.add(new Doctor("stefan", "gies", "gieforce",true));
        employees.add(new Doctor("stefan", "gies", "gieforce",true));
        employees.add(new Doctor("stefan", "gies", "gieforce",true));
        when(userRepository.findAllEmployees()).thenReturn(employees);
        List<User> results = (List<User>) employeeService.getAllEmployees();
        assertEquals(3, results.size());
    }

    @Test
    public void findOneEmployee() {

        Doctor doctor = new Doctor("stefan", "gies", "gieforce",true);
        when(userRepository.findOne(1L)).thenReturn(doctor);
        User foundEmployee = employeeService.findOneEmployee(1L);
        assertEquals(doctor.getUsername(), foundEmployee.getUsername());

    }
}