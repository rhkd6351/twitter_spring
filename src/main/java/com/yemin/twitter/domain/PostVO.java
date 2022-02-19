package com.yemin.twitter.domain;

import com.yemin.twitter.dto.post.PostDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx_fk")
    private MemberVO member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentVO> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
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

    //파라미터는 dto에 해당 연관관계 객체들을 포함시킬지 여부
    public PostDTO dto(Boolean member, Boolean comments, Boolean postImages){
        return PostDTO.builder()
                .idx(this.idx)
                .title(this.title)
                .content(this.content)
                .createdAt(this.createdAt)
                .deletedAt(this.deletedAt)
                .updatedAt(this.updatedAt)
                .postImages(postImages ? this.postImages.stream().map(PostImageVO::dto).collect(Collectors.toList()) : null)
                //TODO member, comments, postImages 엔티티 dto() 구현 후 추가
                .build();
    }

    public void setMember(MemberVO member) {
        this.member = member;
    }

    public void addImage(PostImageVO vo){
        this.postImages.add(vo);
        vo.setPost(this);
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void clearImages(){
        this.postImages.clear();
    }
}
