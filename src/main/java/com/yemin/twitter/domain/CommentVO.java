package com.yemin.twitter.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "COMMENT_TB")
public class CommentVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="post_idx_fk") //연관관계 주인
    private PostVO postVO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx_fk")//연관관계 주인
    private MemberVO memberVO;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreationTimestamp
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Lob
    @Column(name = "content")
    private String content;

    /** 연관관계 메서드post와 comment의 관계는 1:N 관계,
     하나의 포스트가 여러개의 댓글 가질수있으니 post의 필드에 comment객체 추가하는 메소드**/
    public void setPostVO(PostVO postVO) {
        this.postVO = postVO;
        postVO.getComments().add(this);
    }
}
