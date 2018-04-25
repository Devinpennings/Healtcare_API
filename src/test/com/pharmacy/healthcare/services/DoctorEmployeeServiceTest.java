package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.DoctorEmployee;
import com.pharmacy.healthcare.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class DoctorEmployeeServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DoctorEmployeeService doctorEmployeeService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addDoctorEmployee() {
        DoctorEmployee doctorEmployee = new DoctorEmployee("stefan", "gies", "gieforce", true);
        when(userRepository.save(doctorEmployee)).thenReturn(doctorEmployee);
        DoctorEmployee addedDoctorEmployee = doctorEmployeeService.addDoctorEmployee(doctorEmployee);
        assertEquals(doctorEmployee.getUsername(), addedDoctorEmployee.getUsername());
    }

    @Test
    public void updateDoctorEmployee() {
        DoctorEmployee doctorEmployee = new DoctorEmployee("stefan", "gies", "gieforce", true);
        String firstDoctorEmployee = doctorEmployee.getUsername();
        when(userRepository.findOne(1L)).thenReturn(doctorEmployee);
        DoctorEmployee doctorEmployeeToUpdate = new DoctorEmployee("devin", "pennings", "dppennings", true);
        DoctorEmployee updatedDoctorEmployee = doctorEmployeeService.updateDoctorEmployee(1L, doctorEmployeeToUpdate);
        assertNotEquals(updatedDoctorEmployee.getUsername(), firstDoctorEmployee);
    }
}