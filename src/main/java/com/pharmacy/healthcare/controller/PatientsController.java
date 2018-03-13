package com.pharmacy.healthcare.controller;

import com.pharmacy.healthcare.domain.Diagnosis;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.repository.PatientRepository;
import com.pharmacy.healthcare.services.DiagnosesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
            return new ResponseEntity<>(diagnosesService.findAllById(id), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @RequestMapping(value = "/dossier/new/{id}", method = RequestMethod.POST)
//    public ResponseEntity<?> setDiagnose(@PathVariable long id , @RequestBody Diagnosis diagnosis)
//    {
//        System.out.println(id);
//        System.out.println(diagnosis.toString());
//        return new ResponseEntity<>(diagnosesService.save(diagnosis, id), HttpStatus.CREATED);
//    }

    @RequestMapping(value = "/dossier/new/{userId}")
    public ResponseEntity<?> setDiagnose(@PathVariable int userId, @RequestBody Diagnosis diagnosis)
    {
        System.out.println(diagnosis.toString());
        return new ResponseEntity<>(diagnosesService.save(diagnosis, userId), HttpStatus.CREATED);
    }
}
