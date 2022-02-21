package com.yemin.twitter.dto.post;

import com.yemin.twitter.domain.PostVO;
import com.yemin.twitter.dto.ValidationGroups;
import com.yemin.twitter.dto.comment.CommentDTO;
import com.yemin.twitter.dto.member.MemberDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDTO {

    private Long idx;

    @NotBlank(groups = {ValidationGroups.postSaveGroup.class}, message = "제목이 입력되지 않았습니다.")
    @NotBlank(groups = {ValidationGroups.postUpdateGroup.class}, message = "제목이 입력되지 않았습니다.")
    private String title;

    @NotBlank(groups = {ValidationGroups.postSaveGroup.class}, message = "내용이 입력되지 않았습니다.")
    @NotBlank(groups = {ValidationGroups.postUpdateGroup.class}, message = "내용이 입력되지 않았습니다.")
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    private LocalDateTime updatedAt;

    private List<PostImageDTO> postImages;
    private List<CommentDTO> comments;
    private MemberDTO member;

    @Builder
    public PostDTO(Long idx, String title, String content, LocalDateTime createdAt, LocalDateTime deletedAt, LocalDateTime updatedAt, List<PostImageDTO> postImages, List<CommentDTO>comments, MemberDTO member) {
        this.idx = idx;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.updatedAt = updatedAt;
        this.comments=comments;
        this.postImages = postImages;
        this.member = member;
    }

    public PostVO toEntity(){
        return PostVO.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
