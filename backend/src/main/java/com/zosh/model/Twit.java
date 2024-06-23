package com.zosh.model;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Twit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "twit", cascade = CascadeType.ALL)
    private List<Like> likes =new ArrayList<>();

    @OneToMany
    private List<Twit> replyTwits= new ArrayList<>();

    @ManyToMany
    private List<User> retwitUser = new ArrayList<>();
    
    @ManyToOne
    private Twit replyFor;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private String image; 
    private String video;

    private boolean isReply;
    private boolean isTwit;
    private boolean is_liked = false;
    private boolean is_retwit=false;
    

    


}
