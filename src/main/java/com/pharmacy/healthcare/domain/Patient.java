package com.pharmacy.healthcare.domain;

import org.springframework.core.io.ClassPathResource;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Properties;

@Entity
@DiscriminatorValue("patient")
public class Patient extends User implements Serializable {

    @Column(name = "age", nullable = true)
    protected Long age;

    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Set<Diagnosis> diagnoses = new HashSet<>();

    public Long getAge() {
        return age;
    }

     public void addDiagnosis(Diagnosis diagnosis)
    {
        diagnoses.add(diagnosis);
        System.out.println(diagnoses.toString());
    }

    public void removeDiagnosis(Diagnosis diagnosis)
    {
        diagnoses.remove(diagnosis);
    }

    public void removeAllDiagnoses()
    {
        diagnoses.clear();
    }

    /**
     * Outgoing Mail (SMTP) Server
     * requires TLS or SSL: smtp.gmail.com (use authentication)
     * Use Authentication: Yes
     * Port for SSL: 465
     */

    //for testing
    public void emailTest(String email) {
        String[] to = {email};
        sendFromGMail(getEmailProperties().getProperty("username"), getEmailProperties().getProperty("password"), to, "test", createHtmlStringBody("email.html"));
    }

    public void sendConfirmationMail(Patient patient) {
        //todo change template add date and name
        String[] to = {patient.getUsername()}; // can be changed to a list of recipient email addresses
        sendFromGMail(getEmailProperties().getProperty("username"), getEmailProperties().getProperty("password"), to, "Activeer alstublieft uw account.", createHtmlStringBody("email.html"));
    }


    public void sendActivationMail(Patient patient) {
        //todo maak de placeholders in de html en user moet naam en achternaam krijgen
        String[] to = {patient.getUsername()}; // can be changed to a list of recipient email addresses
        String email = createHtmlStringBody("email.html");
        email = email.replaceAll("USERNAME", patient.getFirstname());
        email = email.replaceAll("endpoint", "www.zonnevelt.nl/patients/activate/" + patient.getActivationToken());
        sendFromGMail(getEmailProperties().getProperty("username"), getEmailProperties().getProperty("password"), to, "Activation", email);
    }

    private String createHtmlStringBody(String htmlTemplateName) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            File template = new ClassPathResource(htmlTemplateName).getFile();
            try (BufferedReader in = new BufferedReader(new FileReader(template))) {
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
            input = new FileInputStream(new File("WEB-INF/email.properties").getAbsolutePath());

            // load the mail properties file with cred
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

    @Override
    public String getType() {
        return "patient";
    }
}

