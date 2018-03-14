package com.pharmacy.healthcare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.parser.Token;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "users")
public abstract class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, updatable = false)
    protected Long user_id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;

    public abstract String getType();

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        return authorities;
    }

    @JsonIgnore
    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Set<UserToken> tokens = new HashSet<>();

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

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

    public String getActivationToken(){
        for(UserToken t: tokens){
            if(t.getTokenType().equals(TokenType.ACTIVATION)){
                return t.getToken();
            }
        }
        return null;
    }


}
