package com.yemin.twitter.domain;

import com.yemin.twitter.dto.post.PostImageDTO;
import lombok.AccessLevel;
import lombok.Builder;
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
    private Long idx;

    @Column(name = "name")
    private String name;

    @Embedded
    private FileInfo fileInfo;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_idx_fk")
    private PostVO post;

    @Builder
    public PostImageVO(String name, FileInfo fileInfo) {
        this.name = name;
        this.fileInfo = fileInfo;
    }

    public PostImageDTO dto(){
        return PostImageDTO.builder()
                .idx(this.idx)
                .name(this.name)
                .originalName(this.fileInfo.getOriginalName())
                .saveName(this.fileInfo.getSaveName())
                .size(this.fileInfo.getSize())
                .uploadPath(this.fileInfo.getUploadPath())
                .extension(this.fileInfo.getExtension())
                .url("/api/posts/images/" + this.name)
                .createdAt(this.createdAt)
                .build();
    }

    public void setPost(PostVO post) {
        this.post = post;
    }
}
