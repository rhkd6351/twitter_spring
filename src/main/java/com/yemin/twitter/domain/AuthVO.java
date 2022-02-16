package com.yemin.twitter.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "AUTH_TB")
public class AuthVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "name" ,length = 45)
    private String name;

    @Column(name="description",length = 200)
    private String description;

    @OneToOne(mappedBy = "authVO",fetch = FetchType.LAZY) //연관관계 주인
    private MemberVO memberVO;




}
