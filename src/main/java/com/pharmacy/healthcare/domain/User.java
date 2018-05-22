package com.pharmacy.healthcare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "users")
public abstract class User implements UserDetails, Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, updatable = false)
    protected Long user_id;

    @Column(name = "firstname", nullable = false, unique = false)
    private String firstname;

    @Column(name = "lastname", nullable = false, unique = false)
    private String lastname;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    //@JsonIgnore
    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;

    @Column(name = "gender", nullable = true)
    private String gender;

    @Column(name = "street", nullable = true)
    private String street;

    @Column(name = "housenumber", nullable = true)
    private String housenumber;

    @Column(name = "zipcode", nullable = true)
    private String zipcode;

    @Column(name = "city", nullable = true)
    private String city;

    public abstract String getType();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        for(Role role: roles){
            list.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        return list;

    }



    @JsonIgnore
    @OneToMany(
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Set<UserToken> tokens = new HashSet<>();

    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(String number) {
        this.housenumber = number;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

//    public void setRoles(Collection<Role> roles) {
//        this.roles = roles;
//    }
//
//    public Collection<Role> getRoles() {
//        return roles;
//    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void addToken(UserToken token)
    {
        tokens.add(token);
        System.out.println(token.toString());
    }

    @JsonIgnore
    public String getActivationToken(){
        for(UserToken t: tokens){
            if(t.getTokenType().equals(TokenType.ACTIVATION)){
                return t.getToken();
            }
        }
        return null;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUser_id() {
        return user_id;
    }

    public User() {

    }

    public User(String firstname, String lastname, String username, boolean enabled) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.enabled = enabled;
    }

    public User(long id, String firstname, String lastname, String username, String password, boolean enabled) {
        this.user_id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public void setRoles(List<Role> roles) {
        for (Role role: roles){
            this.roles.add(role);
        }
    }
}
