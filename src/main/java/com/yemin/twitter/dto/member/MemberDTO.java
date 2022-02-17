package com.yemin.twitter.dto.member;

import com.yemin.twitter.domain.AuthVO;
import com.yemin.twitter.domain.MemberVO;
import com.yemin.twitter.dto.ValidationGroups;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class MemberDTO {

    Long idx;

    @NotBlank(groups = {ValidationGroups.memberSignUpGroup.class}, message = "아이디가 입력되지 않았습니다.")
    @NotBlank(groups = {ValidationGroups.memberAuthenticationGroup.class}, message = "아이디가 입력되지 않았습니다.")
    String email;

    @NotBlank(groups = {ValidationGroups.memberSignUpGroup.class}, message = "비밀번호가 입력되지 않았습니다.")
    @NotBlank(groups = {ValidationGroups.memberAuthenticationGroup.class}, message = "아이디가 입력되지 않았습니다.")
    String password;

    @NotBlank(groups = {ValidationGroups.memberSignUpGroup.class}, message = "이름이 입력되지 않았습니다.")
    String username;

    String auth;

    LocalDateTime createdAt;

    public MemberVO toEntity(String encodedPassword, AuthVO auth){
        return MemberVO.builder()
                .email(this.email)
                .password(encodedPassword)
                .username(this.username)
                .auth(auth)
                .activated(true)
                .build();
    }

}
