package com.pharmacy.healthcare.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "diagnosis")
public class Diagnosis implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "report", nullable = false)
    private String report;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Patient patient;

    public Diagnosis(String category, String report) {
        this.category = category;
        this.report = report;
    }

    public Diagnosis(Long id, String category, String report, Date date) {
        this.id = id;
        this.category = category;
        this.report = report;
        this.date = date;
    }

    public Diagnosis()
    {

    }

    public String getCategory() {
        return category;
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

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    @Override
    public String toString() {
        return "Diagnosis{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", report='" + report + '\'' +
                ", date=" + date +
                ", patient=" + patient +
                '}';
    }
}
