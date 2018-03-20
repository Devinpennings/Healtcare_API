package com.pharmacy.healthcare.repository;

import com.pharmacy.healthcare.domain.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {
}
