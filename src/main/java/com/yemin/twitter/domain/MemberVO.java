package com.yemin.twitter.domain;

import com.yemin.twitter.dto.member.MemberDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "MEMBER_TB")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", updatable = false)
    private Long idx;

    @NotEmpty
    @Column(name = "email", length = 100, updatable = false, unique = true, nullable = false)
    private String email;

    @NotEmpty
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @NotEmpty
    @Column(name = "username", length = 45, nullable = false)
    private String username;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name="activated", nullable = false)
    private boolean activated;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_name_fk")
    private AuthVO auth;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_idx_fk")
    private MemberImageVO image;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<PostVO> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<CommentVO> comments = new ArrayList<>();

    @Builder
    public MemberVO(String email, String password, String username, AuthVO auth, boolean activated, MemberImageVO image) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.auth = auth;
        this.activated = activated;
        this.image = image;
    }

    public MemberDTO dto(boolean MemberImage){
        return MemberDTO.builder()
                .idx(this.idx)
                .email(this.email)
                .password("*")
                .username(this.username)
                .auth(this.auth.getName())
                .image(MemberImage ? (this.image != null ? this.image.dto() : null) : null)
                .build();
    }

    public void addPost(PostVO post){
        this.posts.add(post);
        post.setMember(this);
    }

    public void addComment(CommentVO comment){
        this.comments.add(comment);
        comment.setMember(this);
    }

    public void setMemberImage(MemberImageVO memberImage) {
        this.image = memberImage;
    }
}
