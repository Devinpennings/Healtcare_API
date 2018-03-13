package com.pharmacy.healthcare.domain;

import org.springframework.core.io.ClassPathResource;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.*;
import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Properties;

@Entity
@DiscriminatorValue("patient")
public class Patient extends User {

    @Column(name = "age", nullable = false)
    protected Long age;

    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL
            //mappedBy = "user"
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


    /**
     * Outgoing Mail (SMTP) Server
     * requires TLS or SSL: smtp.gmail.com (use authentication)
     * Use Authentication: Yes
     * Port for SSL: 465
     */

    //for testing
    public static void emailTest(String email) {
        String[] to = {email};
        sendFromGMail(getEmailProperties().getProperty("username"), getEmailProperties().getProperty("password"), to, "test", createHtmlStringBody("email.html"));
    }

    public static void sendConformationMail(Patient patient) {
        //todo change template add date and name
        String[] to = {patient.getUsername()}; // can be changed to a list of recipient email addresses
        sendFromGMail(getEmailProperties().getProperty("username"), getEmailProperties().getProperty("password"), to, "Activeer alstublieft uw account.", createHtmlStringBody("email.html"));
    }


    public static void sendActivationMail(Patient patient) {
        //todo add confirmation link and name
        String[] to = {patient.getUsername()}; // can be changed to a list of recipient email addresses
        sendFromGMail(getEmailProperties().getProperty("username"), getEmailProperties().getProperty("password"), to, "Activation", createHtmlStringBody("email.html"));
    }

    private static String createHtmlStringBody(String htmlTemplateName) {
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

    private static Properties getEmailProperties() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            ClassPathResource resource = new ClassPathResource("email.properties");
            File file = new File(resource.getClassLoader().getResource("email.properties").getFile());
            input = new FileInputStream(file);

            // load the mail properties file with cred
            prop.load(input);
            return prop;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
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

