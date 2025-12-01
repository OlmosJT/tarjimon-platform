package io.olmosjt.common.dto;

public record CommentStatsDto(
        long totalComments,
        String topCommenterNames,
        String mostActiveProject
) {
}
