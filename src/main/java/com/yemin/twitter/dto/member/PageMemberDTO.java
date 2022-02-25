package com.yemin.twitter.dto.member;

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
       private MemberImageVO memberImageDTO;

        @Builder
        public PageMemberDTO(Long idx, String username, MemberImageVO memberImage)
        {
            this.idx=idx;
            this.username=username;
            this.memberImageDTO=memberImage;
        }
}
