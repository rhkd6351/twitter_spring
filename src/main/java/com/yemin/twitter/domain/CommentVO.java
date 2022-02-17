package com.yemin.twitter.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "COMMENT_TB")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="post_idx_fk") //연관관계 주인
    private PostVO post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx_fk")//연관관계 주인
    private MemberVO member;

    @Builder
    public CommentVO(String content, PostVO post, MemberVO member) {
        this.content = content;
        this.post = post;
        this.member = member;
    }

    public void setPost(PostVO post) {
        this.post = post;
    }
}
