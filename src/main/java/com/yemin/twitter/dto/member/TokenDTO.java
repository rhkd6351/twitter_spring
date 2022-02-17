package com.yemin.twitter.dto.member;

import lombok.Getter;

@Getter
public class TokenDTO {

    String jwt;

    public TokenDTO(String jwt) {
        this.jwt = jwt;
    }
}
