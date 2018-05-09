package com.pharmacy.healthcare.repository;

import com.pharmacy.healthcare.domain.Pharmacy;
import org.springframework.data.repository.CrudRepository;

public interface PharmacyRepository extends CrudRepository<Pharmacy, Long> {
}
