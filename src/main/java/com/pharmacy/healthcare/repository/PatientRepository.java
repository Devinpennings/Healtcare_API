package com.pharmacy.healthcare.repository;

import com.pharmacy.healthcare.domain.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
    Collection<Patient> findAll();
}
