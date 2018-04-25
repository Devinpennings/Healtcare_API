package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.Admin;
import com.pharmacy.healthcare.domain.User;
import com.pharmacy.healthcare.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock



    @InjectMocks
    private AdminService adminService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateUser() {
        User user = new Admin("Stefan", "Gies", "Gieforce", true);
        when(userRepository.findOne(1L)).thenReturn(user);
        Admin userToChange = new Admin("Devin", "Pennings", "DPP", false);
        User changedUser = adminService.updateUser(1L, userToChange);
        assertEquals(userToChange.getUsername(), changedUser.getUsername());
    }
}
