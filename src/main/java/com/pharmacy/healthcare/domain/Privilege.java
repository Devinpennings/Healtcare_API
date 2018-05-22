//package com.pharmacy.healthcare.domain;
//
//import org.hibernate.action.internal.OrphanRemovalAction;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Collection;
//
//@Entity
//public class Privilege implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private String name;
//
//    @ManyToMany(
//            cascade = CascadeType.ALL,
//            fetch = FetchType.EAGER,
//            mappedBy = "privileges")
//    private Collection<Role> roles;
//
//    public Privilege(String name) {
//        this.name = name;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Privilege() {
//    }
//}