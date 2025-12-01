package io.olmosjt.common.dto;

import lombok.Data;

import java.util.UUID;

public final class FollowersDto {
    private FollowersDto() {}

    @Data
    public static final class Translator {
        private UUID userId;
        private String displayName;
        private String avatarUrl;
        private Integer totalProjects;
        private Integer totalFollowers;
        private boolean isFollowedByMe ;
    }

    @Data
    public static final class Reader {
        private UUID userId;
        private String email;
        private String avatarUrl;
        private boolean isFollowedByMe ;
    }

}
