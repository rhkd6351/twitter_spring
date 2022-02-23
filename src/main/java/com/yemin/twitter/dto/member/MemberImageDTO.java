package com.yemin.twitter.dto.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberImageDTO {

    Long idx;

    String name;

    String originalName;

    String saveName;

    Long size;

    String uploadPath;

    String extension;

    String url;

    LocalDateTime createdAt;

    @Builder
    public MemberImageDTO(Long idx, String name, String originalName, String saveName, Long size, String uploadPath, String extension, String url, LocalDateTime createdAt) {
        this.idx = idx;
        this.name = name;
        this.originalName = originalName;
        this.saveName = saveName;
        this.size = size;
        this.uploadPath = uploadPath;
        this.extension = extension;
        this.url = url;
        this.createdAt = createdAt;
    }
}
