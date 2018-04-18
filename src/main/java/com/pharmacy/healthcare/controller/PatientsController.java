package com.pharmacy.healthcare.controller;

import com.pharmacy.healthcare.domain.Diagnosis;
import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.domain.User;
import com.pharmacy.healthcare.repository.DoctorRepository;
import com.pharmacy.healthcare.repository.PatientRepository;
import com.pharmacy.healthcare.repository.UserRepository;
import com.pharmacy.healthcare.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    PatientService patientService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<Patient>> getPatients()
    {
        return new ResponseEntity<>(patientRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> updatePatient(@PathVariable("id") long id,
                                        @RequestBody Patient patient){
        Patient currentUser = (Patient) userRepository.findOne(id);

        if (currentUser == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else{
            currentUser.setUsername(patient.getUsername());
            currentUser.setFirstname(patient.getFirstname());
            currentUser.setLastname(patient.getLastname());
            currentUser.setAge(patient.getAge());
            currentUser.setGender(patient.getGender());
            currentUser.setStreet(patient.getStreet());
            currentUser.setCity(patient.getCity());
            currentUser.setZipcode(patient.getZipcode());
            currentUser.setHousenumber(patient.getHousenumber());

            userRepository.save(currentUser);
            return new ResponseEntity<Patient>(currentUser, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPatient(@PathVariable long id )
    {
        return new ResponseEntity<>(patientRepository.findOne(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/dossier/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getDiagnosis(@PathVariable long id)
    {
        if (id != 0)
        {
            return new ResponseEntity<>(patientService.findAllByUserId(id), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/dossier/{user_id}", method = RequestMethod.POST)
    public ResponseEntity<?> setDiagnose(@PathVariable long user_id, @RequestBody Diagnosis diagnosis)
    {
        return new ResponseEntity<>(patientService.save(diagnosis, user_id), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/dossier/diagnosis/{diagnosisId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDiagnosis(@PathVariable long diagnosisId, @RequestParam long user_id)
    {
        try{
            patientService.deleteDiagnosis(user_id , diagnosisId);
            return ResponseEntity.noContent().build();
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePatient(@PathVariable("id") long patientId) {

        try{
            patientRepository.delete(patientId);
            return ResponseEntity.noContent().build();
        }
        catch(ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }

    }

    @RequestMapping(value = "/dossier/{user_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDossier(@PathVariable long user_id)
    {
        try {
            patientService.deleteDossier(user_id);
            return ResponseEntity.noContent().build();
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }
    }
  
    @RequestMapping(value = "/validate/{token}", method = RequestMethod.PUT)
    public ResponseEntity<?> validateUser(@PathVariable String token){
        User user = patientService.validateToken(token);
        if(user!=null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/activate/{token}", method = RequestMethod.PUT)
    public ResponseEntity<?> enableUser(@PathVariable String token, @Param("password") String password)
    {
        User user = patientService.validateToken(token);
        if (user != null)
        {
            user.setPassword(passwordEncoder.encode(password));
            user.setEnabled(true);
            userRepository.save(user);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/{doctor_id}", method = RequestMethod.POST)
    public ResponseEntity<?> addPatient(@PathVariable long doctor_id, @RequestBody Patient patient){
        return new ResponseEntity<>(patientService.save(patient, doctor_id), HttpStatus.CREATED);
    }
}
