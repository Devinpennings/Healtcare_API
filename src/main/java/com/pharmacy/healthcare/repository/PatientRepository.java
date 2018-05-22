package com.pharmacy.healthcare.repository;

import com.pharmacy.healthcare.domain.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
    Collection<Patient> findAll();
    @Query(value = "SELECT * FROM users u where u.user_id = :id AND u.dtype = 'patient'", nativeQuery = true)
    Patient findOneByUser(@Param("id") long id);

//    @Query(name = "sp_deletePatient")
//    void deletePatient(@Param("ID") Long id);

}


