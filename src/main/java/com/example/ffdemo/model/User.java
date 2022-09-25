package com.example.ffdemo.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Document
public class User {
    @Id
    private String id;
    private String username;
    @Indexed(unique = true)
    private String email;
    private String password;
    private Collection<GrantedAuthority> authority;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;

        this.authority = new ArrayList<>();
        this.authority.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public User(String username, String email, String password, Collection<GrantedAuthority> authority) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.authority = authority;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.email;
    }

    public Collection<GrantedAuthority> getAuthority() {
        return authority;
    }

    public void setAuthority(Collection<GrantedAuthority> authority) {
        this.authority = authority;
    }

    public void addAuthority(String authority) {
        this.authority.add(new SimpleGrantedAuthority(authority));
    }
}
