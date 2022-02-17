package com.yemin.twitter.dto;

import lombok.Getter;

@Getter
public class MessageDTO {

    String message;

    public MessageDTO(String message) {
        this.message = message;
    }
}
