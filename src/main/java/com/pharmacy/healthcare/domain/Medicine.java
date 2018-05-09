package com.pharmacy.healthcare.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "medicine")
public class Medicine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "barcode", nullable = false, unique = true)
    private String barcode;

    @Column(name = "weight", nullable = false)
    private Long weight;

    @Column(name = "stock")
    private Long stock;

    public Medicine() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBarcode() {
        return barcode;
    }

    public Long getWeight() {
        return weight;
    }

    public Long getStock() {
        return stock;
    }

    public Long orderMedicine(long quantity)
    {
        return stock -= quantity;
    }

    public Long updateStock(Long quantity) {
        this.stock += quantity;
        return stock;
    }
}
