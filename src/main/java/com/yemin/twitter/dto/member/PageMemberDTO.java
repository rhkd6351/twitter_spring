package com.yemin.twitter.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yemin.twitter.domain.FileInfo;
import com.yemin.twitter.domain.MemberImageVO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageMemberDTO {


    List<MemberDTO> memberDTOList;

    @JsonProperty("total_page")
    int totalPage;

    @JsonProperty("current_page")
    int currentPage;

    @Builder
    public PageMemberDTO(List<MemberDTO> memberDTOList, int totalPage, int currentPage) {
        this.memberDTOList = memberDTOList;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
    }
}
