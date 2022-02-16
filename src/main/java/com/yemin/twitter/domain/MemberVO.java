package com.yemin.twitter.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "MEMBER_TB")
public class MemberVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long Idx;

    @NotEmpty
    @Column(name = "email",length = 45)
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_name_fk")//연관관계 주인
    @Column(length = 45)
    private AuthVO authVO;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_image_idx")//연관관계 주인
    private MemberImageVO memberImages;

    @OneToMany(mappedBy = "memberVO")
    private List<PostVO>posts=new ArrayList<>();

    @NotEmpty
    @Column(name = "password",length = 200)
    private String password;

    @NotEmpty
    @Column(name = "username",length = 45)
    private String username;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;



}
