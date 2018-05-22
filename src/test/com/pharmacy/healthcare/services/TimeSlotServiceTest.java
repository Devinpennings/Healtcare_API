package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.domain.TimeSlot;
import com.pharmacy.healthcare.domain.User;
import com.pharmacy.healthcare.repository.PatientRepository;
import com.pharmacy.healthcare.repository.TimeSlotRepository;
import javafx.beans.binding.When;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Time;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringJUnit4ClassRunner.class)
public class TimeSlotServiceTest {

    @Mock
    private TimeSlotRepository timeSlotRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private TimeSlotService timeSlotService;

    private Date date;
    private List timeSlots;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        date = c.getTime();
    }


    @Test
    public void reserveTimeSlot() {
        TimeSlot timeSlot = new TimeSlot(1, date, date, "", true);
        Patient patient = new Patient(1,"x", "x", "x", "x", true, date);
        when(timeSlotRepository.findOne(1L)).thenReturn(timeSlot);
        when(patientRepository.findOne(1L)).thenReturn(patient);
        timeSlotService.reserveTimeSlot(1,patient.getUser_id(), "blabla");
        assertEquals(false, timeSlot.getAvailable());
        assertEquals(patient, timeSlot.getMappedPatient());
        assertEquals("blabla", timeSlot.getNote());
        verify(timeSlotRepository, times(1)).save(timeSlot);
    }

    @Test
    public void clearTimeSlot() {
        TimeSlot timeSlot = new TimeSlot(1, date, date, "", true);
        when(timeSlotRepository.findOne(timeSlot.getId())).thenReturn(timeSlot);
        timeSlotService.clearTimeSlot(timeSlot.getId());
        assertEquals(null, timeSlot.getMappedPatient());
        assertEquals(true, timeSlot.getAvailable());
        assertEquals(null, timeSlot.getNote());
        verify(timeSlotRepository, times(1)).save(timeSlot);
    }

    @Test
    public void approveTimeSlot() {
        TimeSlot timeSlot = new TimeSlot(1, date, date, "", true);
        when(timeSlotRepository.findOne(timeSlot.getId())).thenReturn(timeSlot);
        timeSlotService.approveTimeSlot(timeSlot.getId());
        assertEquals(true, timeSlot.getApproval());
        verify(timeSlotRepository, times(1)).save(timeSlot);
    }

    @Test
    public void getDoctorAppointments() {
        Doctor doctor = new Doctor(1, "x", "x","x", "x", true);
        timeSlots = new ArrayList();
        TimeSlot timeSlot1 = new TimeSlot(1, date, date, "test", false);

        //scenario 1
        timeSlot1.setMappedDoctor(doctor);
        timeSlots.add(timeSlot1);
        when(timeSlotRepository.findAllByAvailableIsFalse()).thenReturn(timeSlots);
        Collection<TimeSlot> result = timeSlotService.getDoctorAppointments(2, 0);
        assertEquals(1, result.size());

        //scenario 2
        timeSlots.add(new TimeSlot(4, date, date, "test", true));
        TimeSlot timeSlot2 = new TimeSlot(2, date, date, "test", false);
        timeSlot2.setMappedDoctor(doctor);
        timeSlots.add(timeSlot2);
        when(timeSlotRepository.findAllByDoctorId(doctor.getUser_id())).thenReturn(timeSlots);
        Collection<TimeSlot>result2 = timeSlotService.getDoctorAppointments(1, doctor.getUser_id());
        assertEquals(1, result2.size());

        //scenario 3
        timeSlots = new ArrayList();
        TimeSlot timeSlot3 = new TimeSlot(3, date, date, "test", false);
        timeSlot3.setMappedDoctor(doctor);
        timeSlots.add(timeSlot3);
        when(timeSlotRepository.findAllByDoctorId(doctor.getUser_id())).thenReturn(timeSlots);
        Collection<TimeSlot> result3 = timeSlotService.getDoctorAppointments(0, doctor.getUser_id());
        assertEquals(1, result3.size());
    }

    @Test
    public void getAllTimeslots() {
        timeSlots = new ArrayList();
        timeSlots.add(new TimeSlot(1, date, date, "test", true));
        timeSlots.add(new TimeSlot(2, date, date, "test", true));
        when(timeSlotRepository.findAll()).thenReturn(timeSlots);
        Collection<TimeSlot> result = timeSlotService.getAllTimeslots();
        assertEquals(result.size(), 2);
    }

    @Test
    public void getApproved() {
        Doctor doctor = new Doctor(1, "x", "x","x", "x", true);
        timeSlots = new ArrayList();
        TimeSlot timeSlot1 = new TimeSlot(1, date, date, "test", true);

        //scenario 1
        timeSlot1.setApproval(true);
        timeSlot1.setMappedDoctor(doctor);
        timeSlots.add(timeSlot1);
        when(timeSlotRepository.findAllByApprovalIsTrue()).thenReturn(timeSlots);
        Collection<TimeSlot> result = timeSlotService.getApproved(2, 0);
        assertEquals(1, result.size());

        //scenario 2
        timeSlots.add(new TimeSlot(4, date, date, "test", true));
        TimeSlot timeSlot2 = new TimeSlot(2, date, date, "test", false);
        timeSlot2.setMappedDoctor(doctor);
        timeSlots.add(timeSlot2);
        when(timeSlotRepository.findAllByDoctorId(doctor.getUser_id())).thenReturn(timeSlots);
        Collection<TimeSlot>result2 = timeSlotService.getApproved(1, doctor.getUser_id());
        assertEquals(1, result2.size());

        //scenario 3
        timeSlots = new ArrayList();
        TimeSlot timeSlot3 = new TimeSlot(3, date, date, "test", false);
        timeSlot3.setMappedDoctor(doctor);
        timeSlots.add(timeSlot3);
        when(timeSlotRepository.findAllByDoctorId(doctor.getUser_id())).thenReturn(timeSlots);
        Collection<TimeSlot> result3 = timeSlotService.getApproved(0, doctor.getUser_id());
        assertEquals(1, result3.size());
    }

    @Test
    public void disapproveTimeSlot() {
        TimeSlot timeSlot1 = new TimeSlot(1, date, date, "test", true);
        when(timeSlotRepository.findOne(1L)).thenReturn(timeSlot1);
        timeSlotService.disapproveTimeSlot(1L);
        assertEquals(false, timeSlot1.getApproval());
        verify(timeSlotRepository, times(1)).save(timeSlot1);

    }
}