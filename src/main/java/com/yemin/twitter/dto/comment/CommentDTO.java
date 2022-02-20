package com.yemin.twitter.dto.comment;


import com.yemin.twitter.domain.CommentVO;
import com.yemin.twitter.domain.MemberVO;
import com.yemin.twitter.domain.PostVO;
import com.yemin.twitter.dto.ValidationGroups;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDTO {
    private Long idx;

    @NotBlank(groups = {ValidationGroups.commentSaveGroup.class}, message = "댓글 내용이 입력되지 않았습니다.")
    @NotBlank(groups = {ValidationGroups.commentUpdateGroup.class}, message = "댓글 내용이 입력되지 않았습니다.")
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    private MemberVO member;

    private PostVO post;

    @Builder
    public CommentDTO(Long idx,String content,LocalDateTime createdAt,LocalDateTime updatedAt,LocalDateTime deletedAt,MemberVO member){
        this.idx=idx;
        this.content=content;
        this.createdAt=createdAt;
        this.deletedAt=deletedAt;
        this.updatedAt=updatedAt;
        this.member=member;
    }

    public CommentVO toEntity(){
        return CommentVO.builder()
                .content(this.content)
                .build();
    }
}
