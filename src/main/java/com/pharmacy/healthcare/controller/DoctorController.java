package com.pharmacy.healthcare.controller;


import com.pharmacy.healthcare.domain.Doctor;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.repository.DoctorRepository;
import com.pharmacy.healthcare.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> addPatient(@PathVariable long id,  @Param("patient_id") long patient_id)
    {
        try {
            Patient patient = patientRepository.findOne(patient_id);
            Doctor doctor = doctorRepository.findOne(id);
            doctor.addPatient(patient);
            doctorRepository.save(doctor);
            return ResponseEntity.ok().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }



    }

}
