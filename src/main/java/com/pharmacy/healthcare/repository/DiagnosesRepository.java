package com.pharmacy.healthcare.repository;

import com.pharmacy.healthcare.domain.Diagnosis;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DiagnosesRepository extends CrudRepository<Diagnosis, Long> {
    @Query(value = "SELECT * FROM diagnosis d LEFT JOIN users_diagnoses us on d.id = us.diagnoses_id LEFT JOIN users u on us.patient_id = u.id WHERE u.id = :id", nativeQuery = true)
    Collection<Diagnosis> findAllById(@Param("id") long id);
}
