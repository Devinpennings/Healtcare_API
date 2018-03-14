package com.pharmacy.healthcare.repository;

import com.pharmacy.healthcare.domain.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
    Collection<Patient> findAll();
    @Query(value = "SELECT * FROM users u where u.user_id = :id AND u.dtype = 'patient'", nativeQuery = true)
    Patient findOneByUser(@Param("id") long id);
    @Query(value = "select * from users u inner join users_tokens uts on u.user_id = uts.user_user_id inner join user_token ut on uts.tokens_id = ut.id where token = :token and ut.expire_date > GETDATE() and ut.used = 0", nativeQuery = true)
    Patient findPatientByToken(@Param("token") String token);
}
