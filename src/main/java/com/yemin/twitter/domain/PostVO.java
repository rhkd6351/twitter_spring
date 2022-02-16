package com.yemin.twitter.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "POST_TB")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", updatable = false)
    private Long idx;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @UpdateTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @CreationTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx_fk")
    private MemberVO member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<CommentVO> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostImageVO> postImages = new ArrayList<>();

    @Builder
    public PostVO(String title, String content, MemberVO member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void addComment(CommentVO comment){
        this.comments.add(comment);
        comment.setPost(this);
    }



}
