package io.olmosjt.common.dto;

import java.time.Instant;
import java.util.UUID;

public record DashboardCommentDto(
        UUID id,
        String content,
        Instant createdAt,

        // Context (fetched via joins)
        UUID projectId,
        String projectTitle,
        UUID chapterId,
        String chapterTitle,

        // User Info (fetched via join)
        UUID userId,
        String username,   // or email/displayName
        String userAvatar,

        boolean isReply    // true if parentCommentId is not null
) {
}
