package com.yemin.twitter.dto.post;

import com.yemin.twitter.domain.PostVO;
import com.yemin.twitter.dto.ValidationGroups;
import com.yemin.twitter.dto.comment.CommentDTO;
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

    private List<CommentDTO> comments;
    @Builder
    public PostDTO(Long idx, String title, String content, LocalDateTime createdAt, LocalDateTime deletedAt, LocalDateTime updatedAt, List<CommentDTO>comments) {
        this.idx = idx;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.updatedAt = updatedAt;
        this.comments=comments;
    }

    public PostVO toEntity(){
        return PostVO.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
