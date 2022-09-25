package com.example.ffdemo.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class UserDto {
    private String email;
    private String username;
    private String password;
    private String id;
    private Collection<GrantedAuthority> authority;

    public UserDto() {
    }

    public UserDto(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;

        this.authority = new ArrayList<>();
        this.authority.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
