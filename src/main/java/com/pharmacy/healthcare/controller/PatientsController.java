package com.pharmacy.healthcare.controller;

import com.pharmacy.healthcare.domain.Diagnosis;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.domain.User;
import com.pharmacy.healthcare.repository.PatientRepository;
import com.pharmacy.healthcare.repository.UserRepository;
import com.pharmacy.healthcare.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/patients")
public class PatientsController {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("patientService")
    PatientService diagnosesService;


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

    @RequestMapping(value = "/dossier/{userId}", method = RequestMethod.POST)
    public ResponseEntity<?> setDiagnose(@PathVariable long userId, @RequestBody Diagnosis diagnosis)
    {
        return new ResponseEntity<>(diagnosesService.save(diagnosis, userId), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/dossier/diagnosis/{diagnosisId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDiagnosis(@PathVariable long diagnosisId, @RequestParam long userid)
    {
        try{
            diagnosesService.deleteDiagnosis(userid , diagnosisId);
            return ResponseEntity.noContent().build();
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/dossier/{userId}", method = RequestMethod.DELETE)
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
  
    @RequestMapping(value = "/validate/{token}", method = RequestMethod.PUT)
    public ResponseEntity<?> validateUser(@PathVariable String token){
        User user = diagnosesService.validateToken(token);
        if(user!=null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/activate/{token}", method = RequestMethod.PUT)
    public ResponseEntity<?> enableUser(@PathVariable String token, @Param("password") String password)
    {
        User user = diagnosesService.validateToken(token);
        if (user != null)
        {
            user.setPassword(passwordEncoder.encode(password));
            user.setEnabled(true);
            userRepository.save(user);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<?> addPatient(@RequestBody Patient patient){
        return new ResponseEntity<>(diagnosesService.save(patient), HttpStatus.CREATED);
    }
}
