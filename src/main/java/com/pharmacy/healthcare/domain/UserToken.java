package com.pharmacy.healthcare.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="userToken")
public class UserToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "expireDate", nullable = false)
    private Date expireDate;

    @Column(name = "tokenType", nullable = false)
    private TokenType tokenType;

    @Column(name = "used", nullable = false)
    private Boolean used = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Patient user;

    public UserToken(String token, Date expireDate, TokenType tokenType, Boolean used, Patient user) {
        this.token = token;
        this.expireDate = expireDate;
        this.tokenType = tokenType;
        this.used = used;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public Boolean getUsed() {
        return used;
    }

    public Patient getUser() {
        return user;
    }
}
