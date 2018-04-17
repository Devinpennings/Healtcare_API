package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.*;
import com.pharmacy.healthcare.repository.*;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;

@Service("patientService")
public class PatientService {

    @Autowired
    private DiagnosesRepository diagnosisRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Collection<Diagnosis> findAllByUserId(long user_id)
    {
        return diagnosisRepository.findAllByUserId(user_id);
    }

    public Patient save(Diagnosis diagnosis, long user_id)
    {
        Patient patient = patientRepository.findOneByUser(user_id);
        patient.addDiagnosis(diagnosis);
        patientRepository.save(patient);
        return patient;
    }

    public void deleteDiagnosis(long user_id, long id)
    {
        Patient p = patientRepository.findOne(user_id);
        p.removeDiagnosis(diagnosisRepository.findDiagnosesById(id));
        patientRepository.save(p);
    }

    public void deleteDossier(long user_id)
    {
        Patient p = patientRepository.findOne(user_id);
        p.removeAllDiagnoses();
        patientRepository.save(p);
    }

    public Patient save(Patient patient, long doctor_id){
        Doctor doctor = doctorRepository.findOne(doctor_id);
        Patient p = patientRepository.save(patient);
        doctor.addPatientToDoctor(p);
        doctorRepository.save(doctor);
        if (p != null){
            UserToken userToken = new UserToken(generateRandomToken(10), getActivationExpireDate(), TokenType.ACTIVATION, false, p);
            p.addToken(userToken);
            tokenRepository.save(userToken);
            p.sendActivationMail(p);
        }
        return patient;
    }

    public User validateToken(String token){
        User user = userRepository.findAllByToken(token);
        if(user!=null){
            userRepository.save(user);
            return user;
        }
        return null;
    }

    public User setPassAndEnable(User user){
        if(user!=null){
            user.setEnabled(true);
            userRepository.save(user);
            //todo token to used
            return user;
        }
        return null;
    }

    public String generateRandomToken(int length){
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('a', 'z').build();
        return generator.generate(length);
    }


    public Date getActivationExpireDate(){
        java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(sqlDate);
        cal.add(Calendar.DAY_OF_YEAR,7);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new java.sql.Date(cal.getTimeInMillis());
    }

}
