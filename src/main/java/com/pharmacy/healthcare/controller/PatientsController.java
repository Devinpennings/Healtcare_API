package com.pharmacy.healthcare.controller;

import com.pharmacy.healthcare.domain.Diagnosis;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.repository.PatientRepository;
import com.pharmacy.healthcare.services.DiagnosesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/patients")
public class PatientsController {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    @Qualifier("diagnosesService")
    DiagnosesService diagnosesService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<Patient>> getPatients()
    {
        return new ResponseEntity<>(patientRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/dossier/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getDiagnosis(@PathVariable long id)
    {
        if (id != 0)
        {
            return new ResponseEntity<>(diagnosesService.findAllByUserId(id), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/dossier/new/{userId}", method = RequestMethod.POST)
    public ResponseEntity<?> setDiagnose(@PathVariable long userId, @RequestBody Diagnosis diagnosis)
    {
        return new ResponseEntity<>(diagnosesService.save(diagnosis, userId), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/dossier/delete/diagnosis/{diagnosisId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDiagnosis(@PathVariable long diagnosisId)
    {
        try{
            diagnosesService.deleteDiagnosis(diagnosisId);
            return ResponseEntity.noContent().build();
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/dossier/delete/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDossier(@PathVariable long userId)
    {
        try {
            diagnosesService.deleteDossier(userId);
            return ResponseEntity.noContent().build();
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }
    }
}
