package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.Diagnosis;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.repository.DiagnosesRepository;
import com.pharmacy.healthcare.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service("diagnosesService")
public class DiagnosesService {

    @Autowired
    private DiagnosesRepository diagnosisRepository;

    @Autowired
    private PatientRepository patientRepository;

    public Collection<Diagnosis> findAllByUserId(long userid)
    {
        return diagnosisRepository.findAllByUserId(userid);
    }

    public Patient save(Diagnosis diagnosis, long user_id)
    {
        Patient patient = patientRepository.findOneByUser(user_id);
        patient.addDiagnosis(diagnosis);
        patientRepository.save(patient);
        return patient;
    }

    public void deleteDiagnosis(long id)
    {
       diagnosisRepository.delete(diagnosisRepository.findDiagnosesById(id));
    }

    public void deleteDossier(long userid)
    {
       Collection<Diagnosis> diagnoses = diagnosisRepository.findAllByUserId(userid);
       diagnosisRepository.delete(diagnoses);
    }

}
