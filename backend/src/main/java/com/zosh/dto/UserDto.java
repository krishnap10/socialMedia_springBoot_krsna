package com.zosh.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zosh.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	private Long id;
	private String fullName;
	private String email;
	private String image;
	
    private String location;

    private String website;

    private String birthDate;

    private String mobile;

    private String backgroundImage;
    
    private String bio;
    
    private boolean req_user;
    
    private boolean login_with_google;
    
    private List<UserDto>followers=new ArrayList<>();
    
    private List<UserDto>followings=new ArrayList<>();
    
    private boolean followed;
    
    private boolean isVerified;

}
