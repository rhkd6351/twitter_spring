package com.yemin.twitter.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PagePostDTO {

    List<PostDTO> posts = new ArrayList<>();

    @JsonProperty("total_page")
    int totalPage;

    @JsonProperty("current_page")
    int currentPage;


    @Builder
    public PagePostDTO(List<PostDTO> posts, int totalPage, int currentPage) {
        this.posts = posts;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
    }
}
