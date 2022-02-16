package com.yemin.twitter.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "POST_TB")
public class PostVO {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn (name="member_idx_fk")
    private MemberVO memberVO;

   @OneToMany(mappedBy = "postVO")
   private List<CommentVO> comments=new ArrayList<>();

    @OneToMany(mappedBy = "postVO")
    private List<PostImageVO> postImages=new ArrayList<>();

    @Lob
    @Column(name = "content")
    private String content;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @CreationTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;






}
