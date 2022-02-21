package com.yemin.twitter.dto.comment;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageCommentDTO {
    //페이징을 위한 DTO 작성
    List<CommentDTO>comments=new ArrayList<>();

    @JsonProperty("total page")
    int totalPage;

    @JsonProperty("current_page")
    int currentPage;

    @Builder
    public PageCommentDTO(List<CommentDTO> comments,int totalPage,int currentPage){
        this.comments=comments;
        this.totalPage=totalPage;
        this.currentPage=currentPage;
    }
}
