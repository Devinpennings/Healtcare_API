package com.pharmacy.healthcare.repository;

import com.pharmacy.healthcare.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Patient, Long> {
    Patient findOneByUsername(String username);
}
