package com.yemin.twitter.dto.member;

import com.yemin.twitter.domain.FileInfo;
import com.yemin.twitter.domain.MemberImageVO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class PageMemberDTO {
       private Long idx;
       private String username;
       private FileInfo fileInfo;


        @Builder
        public PageMemberDTO(Long idx, String username, FileInfo fileInfo)
        {
            this.idx=idx;
            this.username=username;
            this.fileInfo=fileInfo;
        }
}
