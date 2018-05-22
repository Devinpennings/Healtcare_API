package com.pharmacy.healthcare.domain;

import com.pharmacy.healthcare.aes.EncryptionUtil;
import org.hibernate.annotations.Type;
import org.hibernate.jpa.criteria.expression.function.AggregationFunction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.Date;


@Entity
@Table(name = "diagnosis")
public class Diagnosis implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

//    @Column(name = "category", nullable = false)
//    private byte[] category;
//
//    @Column(name = "report", nullable = false)
//    private byte[] report;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "report", nullable = false)
    //@Length(max=10000)
    @Type(type="text")
    private String report;

    @Column(name = "date", nullable = false)
    private java.util.Date date;

    public Diagnosis()
    {
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

//    public String getCategory() throws GeneralSecurityException {
//        return EncryptionUtil.decrypt(category);
//    }
//
//    public void setCategory(String category) throws GeneralSecurityException {
//        this.category = EncryptionUtil.encrypt(category);
//    }
//
//    public String getReport() throws GeneralSecurityException {
//        return EncryptionUtil.decrypt(report);
//    }
//
//    public void setReport(String report) throws GeneralSecurityException {
//        this.report = EncryptionUtil.encrypt(report);
//    }


    public String getCategory() throws Exception {
        return EncryptionUtil.decrypt(category);
    }

    public void setCategory(String category) throws Exception {
        this.category = EncryptionUtil.encrypt(category);
    }

    public String getReport() throws Exception {
        return EncryptionUtil.decrypt(report);
    }

    public void setReport(String report) throws Exception {
        this.report = EncryptionUtil.encrypt(report);
    }

    public Diagnosis(String category, String report, Date date) {
        this.category = category;
        this.report = report;
        this.date = date;
    }


    public Diagnosis(long id, String category, String report, Date date) {
        this.id = id;
        this.category = category;
        this.report = report;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Diagnosis{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", report='" + report + '\'' +
                ", date=" + date +
//                ", patient=" + patient +
                '}';
    }
}