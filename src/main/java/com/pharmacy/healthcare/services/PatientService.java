package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.*;
import com.pharmacy.healthcare.repository.*;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.Properties;

@Service("patientService")
public class PatientService {

    @Autowired
    private DiagnosesRepository diagnosisRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Collection<Diagnosis> GetAllDiagnosisByUserId(long user_id) {
        return diagnosisRepository.findAllByUserId(user_id);
    }

    public Patient createPatient(Diagnosis diagnosis, long user_id) {
        Patient patient = patientRepository.findOneByUser(user_id);
        patient.addDiagnosis(diagnosis);
        patientRepository.save(patient);
        return patient;
    }

    public Collection<Patient> getPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatient(long id){
        return patientRepository.findOne(id);
    }

    @Transactional
    public Patient getTransactionalPatient(long id){
        return patientRepository.findOne(id);
    }



    public Patient updatePatient(long id, Patient patient) {
        Patient currentUser = (Patient) userRepository.findOne(id);

        if (currentUser == null) {
            return null;
        } else {
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
            return currentUser;
        }

    }

    public boolean deleteDiagnosis(long user_id, long id)
    {
        Patient p = patientRepository.findOne(user_id);
        if(p!=null){
            p.removeDiagnosis(diagnosisRepository.findDiagnosesById(id));
            patientRepository.save(p);
            return true;
        }
        else{
            return false;
        }


    }

    public boolean deleteDossier(long user_id)
    {
        Patient p = patientRepository.findOne(user_id);
        if(p!=null){
            p.removeAllDiagnoses();
            patientRepository.save(p);
            return true;
        }
        else{
            return false;
        }

    }

    public User enableUser(String token, String password){
        User user = validateToken(token);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(password));
            user.setEnabled(true);
            userRepository.save(user);
            return user;
        }
        else{
            return null;
        }
    }

    public Patient createPatient(Patient patient, long doctor_id){
        Doctor doctor = doctorRepository.findOne(doctor_id);
        Patient p = patientRepository.save(patient);
        doctor.addPatientToDoctor(p);
        doctorRepository.save(doctor);
        if (p != null){
            UserToken userToken = new UserToken(generateRandomToken(10), getActivationExpireDate(), TokenType.ACTIVATION, false, p);
            p.addToken(userToken);
            tokenRepository.save(userToken);
            sendActivationMail(p);
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

//    public User setPassAndEnable(User user){
//        if(user!=null){
//            user.setEnabled(true);
//            userRepository.createPatient(user);
//            return user;
//        }
//        return null;
//    }

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


    public void sendAppointmentCancelMail(Patient patient){
        String[] to = {patient.getUsername()};
        String email = createHtmlStringBody("cancelEmail.html");
        email = email.replaceAll("USERNAME", patient.getFirstname());
        email = email.replaceAll("AFSPRAAK", "Uw afspraak met dr. " + patient.mappedDoctor.getLastname() + " is afgezegd vanwege een dubbele afspraak van uw huisarts. Excuses voor het ongemak");
        sendFromGMail(getEmailProperties().getProperty("username"), getEmailProperties().getProperty("password"), to, "Uw afspraak is afgezegd.", email);
    }


    public void sendActivationMail(Patient patient) {
        //todo maak de placeholders in de html en user moet naam en achternaam krijgen
        String[] to = {patient.getUsername()}; // can be changed to a list of recipient email addresses
        String email = createHtmlStringBody("email.html");
        email = email.replaceAll("USERNAME", patient.getFirstname());
        email = email.replaceAll("endpoint", "http://localhost:8080/register?token=" + patient.getActivationToken());
        sendFromGMail(getEmailProperties().getProperty("username"), getEmailProperties().getProperty("password"), to, "Activation", email);
    }

    private String createHtmlStringBody(String htmlTemplateName) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            InputStream template = new ClassPathResource(htmlTemplateName).getInputStream();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(template, "UTF-8"))) {
                String str;
                while ((str = in.readLine()) != null) {
                    contentBuilder.append(str);
                }
                in.close();
                return contentBuilder.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Properties getEmailProperties() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new ClassPathResource("email.properties").getInputStream();
            prop.load(input);
            return prop;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setContent(body, "text/html");
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }

}
