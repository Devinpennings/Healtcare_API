package com.pharmacy.healthcare.repository;


import com.pharmacy.healthcare.domain.TimeSlot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Collection;
import java.util.Date;

@Repository
public interface TimeSlotRepository extends CrudRepository<TimeSlot, Long> {
    Collection<TimeSlot>findAll();
    @Query(value = "SELECT * FROM timeslot a inner join users_appointments ua on a.id = ua.appointments_id\n" +
            "inner join users u on ua.patient_user_id = u.user_id where u.user_id = 1 AND u.dtype = 'patient'", nativeQuery = true)
    Collection<TimeSlot>findAllByPatientId(@Param("id") long id);
    @Query(value = "SELECT * FROM timeslot a WHERE a.doctors_user_id = :id", nativeQuery = true)
    Collection<TimeSlot> findAllByDoctorId(@Param("id") long id);
    @Query(value = "SELECT * FROM timeslot t WHERE t.doctors_user_id = :doctor_id AND t.starttime = :starttime", nativeQuery = true)
    TimeSlot findTimeSlotByStartTime(@Param("starttime")Date starttime, @Param("doctor_id")long doctor_id);
    Collection<TimeSlot> findAllByAvailableIsTrue();
    Collection<TimeSlot> findAllByApprovalIsTrue();
    Collection<TimeSlot> findAllByApprovalIsTrueAndMappedDoctorIs(long id);
}
