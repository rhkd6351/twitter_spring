package com.yemin.twitter.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "AUTH_TB")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "name", length = 45, updatable = false)
    private String name;

    @Column(name="description", length = 200, nullable = false)
    private String description;

}
