package com.yemin.twitter.domain;

import com.yemin.twitter.dto.member.MemberImageDTO;
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
@Table(name = "MEMBER_IMAGE_TB")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberImageVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Embedded
    private FileInfo fileInfo;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "memberImage")
    private MemberVO member;

    @Builder
    public MemberImageVO(Long idx, String name, FileInfo fileInfo, LocalDateTime createdAt) {
        this.idx = idx;
        this.name = name;
        this.fileInfo = fileInfo;
    }

    public void setMember(MemberVO member) {
        this.member = member;
        member.setMemberImage(this);
    }

    public MemberImageDTO dto(){
        return MemberImageDTO.builder()
                .idx(this.idx)
                .name(this.name)
                .originalName(this.fileInfo.getOriginalName())
                .saveName(this.fileInfo.getSaveName())
                .size(this.fileInfo.getSize())
                .uploadPath(this.fileInfo.getUploadPath())
                .extension(this.fileInfo.getExtension())
                .url("/api/member/image/" + this.name)
                .createdAt(this.createdAt)
                .build();
    }
}
