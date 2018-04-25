package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.*;
import com.pharmacy.healthcare.repository.DiagnosesRepository;
import com.pharmacy.healthcare.repository.PatientRepository;
import com.pharmacy.healthcare.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class PatientServiceTest {

    private Date testDate;


    @Mock
    private UserRepository userRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DiagnosesRepository diagnosesRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PatientService patientService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        String inputString = "11-11-2012";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        testDate = dateFormat.parse(inputString);
    }


    @Test
    public void getPatient(){
        Patient patient = new Patient("x", "x", "x", true, testDate);
        when(patientRepository.findOne(1L)).thenReturn(patient);
        Patient receivedUser = patientService.getPatient(1L);
        Assert.assertEquals(receivedUser.getUsername(), patient.getUsername());
    }

    @Test
    public void getPatients() {
         List<Patient> patients = new ArrayList<>();
         patients.add(new Patient(1L, "x", "x","x", "x", true, testDate));
         patients.add(new Patient(2L, "x", "x","x", "x", true, testDate));
         patients.add(new Patient(3L, "x", "x","x", "x", true, testDate));
         when(patientRepository.findAll()).thenReturn(patients);
         Collection<Patient> result = patientService.getPatients();
         assertEquals(3, result.size());
    }

    @Test
    public void updatePatient() {
        User user = new Patient("x", "x", "x", true, testDate);
        when(userRepository.findOne(1L)).thenReturn(user);
        Patient patientToChange = new Patient("y", "y", "y", true, testDate);
        User changedUser = patientService.updatePatient(1L, patientToChange);
        Assert.assertEquals(patientToChange.getUsername(), changedUser.getUsername());
    }

    @Test
    public void GetAllDiagnosisByUserId(){
        List<Diagnosis> diagnoses = new ArrayList<>();
        diagnoses.add(new Diagnosis("x", "x", testDate));
        diagnoses.add(new Diagnosis("x", "x", testDate));
        diagnoses.add(new Diagnosis("x", "x", testDate));
        when(diagnosesRepository.findAllByUserId(1L)).thenReturn(diagnoses);
        Collection<Diagnosis> result = patientService.GetAllDiagnosisByUserId(1L);
        assertEquals(result.size(), 3);
    }

    @Test
    public void deleteDiagnosis() {
        Diagnosis diagnosis = new Diagnosis(1L,"x", "x", testDate);
        Patient patient = new Patient(1L, "x", "x","x", "x", true, testDate);
        when(patientRepository.findOne(1L)).thenReturn(patient);
        when(diagnosesRepository.findDiagnosesById(1L)).thenReturn(diagnosis);
        assertEquals(true, patientService.deleteDiagnosis(patient.getUser_id(), diagnosis.getId()));
    }

    @Test
    public void deleteDossier() {
        Diagnosis diagnosis = new Diagnosis(1L,"x", "x", testDate);
        Patient patient = new Patient(1L, "x", "x","x", "x", true, testDate);
        when(patientRepository.findOne(1L)).thenReturn(patient);
        when(diagnosesRepository.findDiagnosesById(1L)).thenReturn(diagnosis);
        assertEquals(true, patientService.deleteDossier(patient.getUser_id()));
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    public void enableUser(){
        UserToken token = new UserToken("x", testDate, TokenType.ACTIVATION, false, new Patient(1L, "x", "x","x", "x", true, testDate));
        User mock = new Patient("x", "x", "x", true, testDate);
        when(userRepository.findAllByToken(token.getToken())).thenReturn(mock);
        User user = patientService.enableUser(token.getToken(), "password");
        assertEquals(user.isEnabled(), true);
        //assertNotNull(user.getPassword());
        verify(userRepository, times(2)).save(user);
    }

    @Test
    public void validateToken() {
        UserToken token = new UserToken("x", testDate, TokenType.ACTIVATION, false, new Patient(1L, "x", "x","x", "x", true, testDate));
        User user = new Patient("x", "x", "x", true, testDate);
        user.addToken(token);
        when(userRepository.findAllByToken(token.getToken())).thenReturn(user);
        assertEquals(user, patientService.validateToken(token.getToken()));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void generateRandomToken() {
        assertNotNull(patientService.generateRandomToken(10));
    }

}