package com.pharmacy.healthcare.repository;

import com.pharmacy.healthcare.domain.Medicine;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface MedicineRepository extends CrudRepository<Medicine, Long> {
    Collection<Medicine> findAll();
}
