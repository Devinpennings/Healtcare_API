package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.domain.User;
import com.pharmacy.healthcare.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private Patient patient;
    private Date date;

    @Before
    public void setUp() throws Exception {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        date = c.getTime();
        patient = new Patient(1,"x", "x", "x", "x", true, date);
        patient.setUsername("test");

    }

    @Test
    public void loadUserByUsername() {
        when(userRepository.findOneByUsername("test")).thenReturn(patient);
        Patient result = (Patient) userService.loadUserByUsername("test");
        assertEquals(patient.getUsername(), result.getUsername());
    }

}