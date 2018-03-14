package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.*;
import com.pharmacy.healthcare.repository.DiagnosesRepository;
import com.pharmacy.healthcare.repository.PatientRepository;
import com.pharmacy.healthcare.repository.TokenRepository;
import com.pharmacy.healthcare.repository.UserRepository;
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
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

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
  
    public Patient save(Patient patient){
        Patient p = patientRepository.save(patient);
        if (p != null){
            //todo generate random token
            UserToken userToken = new UserToken("token", getTomorrowDate(), TokenType.ACTIVATION, false, p);
            p.addToken(userToken);
            tokenRepository.save(userToken);
            p.sendActivationMail(p);
        }
        return patient;
    }

    public User validateToken(String token){
        User user = userRepository.findAllByToken(token);
        if(user!=null){
            user.setEnabled(true);
            userRepository.save(user);
            return user;
        }
        return null;
    }


    public Date getTomorrowDate(){
        java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(sqlDate);
        cal.add(Calendar.DAY_OF_YEAR,1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new java.sql.Date(cal.getTimeInMillis());
    }

}
