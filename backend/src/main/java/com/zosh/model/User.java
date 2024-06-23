package com.zosh.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    private String location;

    private String website;

    private String birthDate;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private String mobile;

    private String image;

    private String backgroundImage;
    
    private String bio;
    
    private boolean req_user;
    
    private boolean login_with_google;
    
    private boolean is_req_user=false;
    
//    @ManyToMany(mappedBy = "retwitUser",cascade = CascadeType.ALL)
//    private List<Twit> retwits = new ArrayList<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Twit> twit = new ArrayList<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Like> likes  = new ArrayList<>();
    
    @Embedded
    private Varification verification;
    
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    private List<User>followers=new ArrayList<>();
    
    @JsonIgnore
    @ManyToMany(mappedBy = "followers")
    private List<User>followings=new ArrayList<>();
    
    
}
