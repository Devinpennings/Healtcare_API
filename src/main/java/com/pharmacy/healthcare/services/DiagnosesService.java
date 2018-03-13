package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.Diagnosis;
import com.pharmacy.healthcare.repository.DiagnosesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service("diagnosesService")
public class DiagnosesService {

    @Autowired
    private DiagnosesRepository diagnosisRepository;

    public Collection<Diagnosis> findAllById(long id)
    {
        return diagnosisRepository.findAllById(id);
    }


}
