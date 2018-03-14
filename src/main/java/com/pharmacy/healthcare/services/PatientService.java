package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.*;
import com.pharmacy.healthcare.repository.DiagnosesRepository;
import com.pharmacy.healthcare.repository.PatientRepository;
import com.pharmacy.healthcare.repository.TokenRepository;
import com.pharmacy.healthcare.repository.UserRepository;
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

    public void deleteDiagnosis(long userid, long id)
    {
        Patient p = patientRepository.findOne(userid);
        p.removeDiagnosis(diagnosisRepository.findDiagnosesById(id));
        patientRepository.save(p);
    }

    public void deleteDossier(long userid)
    {
        Patient p = patientRepository.findOne(userid);
        p.removeAllDiagnoses();
        patientRepository.save(p);
    }
  
    public Patient save(Patient patient){
        Patient p = patientRepository.save(patient);
        if (p != null){
            UserToken userToken = new UserToken(generateRandomToken(10), getActivationExpireDate(), TokenType.ACTIVATION, false, p);
            p.addToken(userToken);
            tokenRepository.save(userToken);
            p.sendActivationMail(p);
        }
        return patient;
    }

    public User validateToken(String token){
        User user = userRepository.findAllByToken(token, TokenType.ACTIVATION);
        if(user!=null){
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
