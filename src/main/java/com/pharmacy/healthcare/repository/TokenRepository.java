package com.pharmacy.healthcare.repository;

import com.pharmacy.healthcare.domain.UserToken;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface TokenRepository extends CrudRepository<UserToken, Long> {

    }

