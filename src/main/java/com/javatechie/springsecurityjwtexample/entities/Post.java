package com.javatechie.springsecurityjwtexample.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @Lob
    @Column(name = "msg", length = 100000)
    private String message;
    @Column(name = "datePub")
    private long datePub;
    @Column(name = "addIP")
    private String addIP;
    @Column(name = "typePost")
    private String type;
    @Column(name = "pays")
    private String pays;
    @ManyToOne
    private AppUser appUsers;

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public AppUser getAppUsers() {
        return appUsers;
    }

    public void setAppUsers(AppUser appUsers) {
        this.appUsers = appUsers;
    }

    public Post() {
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDatePub() {
        return datePub;
    }

    public void setDatePub(long datePub) {
        this.datePub = datePub;
    }

    public String getAddIP() {
        return addIP;
    }

    public void setAddIP(String addIP) {
        this.addIP = addIP;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
