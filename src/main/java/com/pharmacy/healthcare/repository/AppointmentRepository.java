package com.pharmacy.healthcare.repository;


import com.pharmacy.healthcare.domain.Appointment;
import com.pharmacy.healthcare.domain.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    Collection<Appointment>findAll();
    @Query(value = "SELECT * FROM appointment a inner join users_appointments ua on a.id = ua.appointments_id\n" +
            "inner join users u on ua.patient_user_id = u.user_id where u.user_id = 1 AND u.dtype = 'patient'", nativeQuery = true)
    Collection<Appointment>findAllByPatientId(@Param("id") long id);
    @Query(value = "SELECT * FROM appointment a inner join users_appointments ua on a.id = ua.appointments_id\n" +
            "inner join users u on ua.patient_user_id = u.user_id where u.user_id = 1 AND u.dtype = 'doctor'", nativeQuery = true)
    Collection<Appointment> findAllByDoctorId(@Param("id") long id);


}
