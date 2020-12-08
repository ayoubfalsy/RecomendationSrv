package com.javatechie.springsecurityjwtexample.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
//@Data @AllArgsConstructor
//@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "AppUser")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    private String addIp;
    private String pays;
    private String sexe;
    private long timeCreate;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> roles = new ArrayList<>();


    public AppUser() {
    }


    public AppUser(String email, String password, Collection<AppRole> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }


    public AppUser(String email, String password, String addIp, String pays, long timeCreate, Collection<AppRole> roles) {
        this.email = email;
        this.password = password;
        this.addIp = addIp;
        this.pays = pays;
        this.timeCreate = timeCreate;
        this.roles = roles;
    }

    public AppUser(String email, String password, String pays, String sexe, long timeCreate) {
        this.email = email;
        this.password = password;
        this.pays = pays;
        this.sexe = sexe;
        this.timeCreate = timeCreate;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    @JsonIgnore
    public String getAddIp() {
        return addIp;
    }

    @JsonIgnore
    public void setAddIp(String addIp) {
        this.addIp = addIp;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public long getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(long timeCreate) {
        this.timeCreate = timeCreate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonSetter
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public Collection<AppRole> getRoles() {
        return roles;
    }

    @JsonIgnore
    public void setRoles(Collection<AppRole> roles) {
        this.roles = roles;
    }
}
