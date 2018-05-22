package com.pharmacy.healthcare.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(
            fetch = FetchType.EAGER,
            mappedBy = "roles")
    private Collection<User> users;

    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }

    public String getName() {
        return name;
    }
}