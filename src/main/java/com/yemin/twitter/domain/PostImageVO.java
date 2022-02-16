package com.yemin.twitter.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "POST_IMAGE_TB")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImageVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", updatable = false)
    private int idx;

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

    @Column(name = "extension",length = 45)
    private String extension;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_idx_fk")
    private PostVO post;

}
