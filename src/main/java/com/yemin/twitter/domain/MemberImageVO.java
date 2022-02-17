package com.yemin.twitter.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "MEMBER_IMAGE_TB")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberImageVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "original_name", length = 255, nullable= false)
    private String originalName;

    @Column(name = "save_name", length = 255, nullable = false)
    private String saveName;

    @Column(name = "size", nullable = false)
    private Integer size;

    @Column(name = "upload_path", length = 255, nullable = false)
    private String uploadPath;

    @Column(name = "extension", length = 45, nullable = false)
    private String extension;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "memberImage")
    private MemberVO member;

}
