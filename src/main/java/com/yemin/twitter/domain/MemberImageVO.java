package com.yemin.twitter.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "MEMBER_IMAGE_TB")
public class MemberImageVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;

    @OneToOne(mappedBy = "memberImages")
    private MemberVO memberVO;

    @Column(name = "name")
    private String name;
    @Column(name = "original_name")
    private String originalName;
    @Column(name = "save_name")
    private String saveName;
    @Column(name = "size")
    private Integer size;
    @Column(name = "upload_path")
    private String uploadPath;

    @Column(name = "extenstion",length = 45)
    private String extension;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
