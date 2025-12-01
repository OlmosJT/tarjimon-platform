package io.olmosjt.common.dto;

import java.util.UUID;

public record ProjectStatsDto(
        UUID projectId,
        int totalChapters,
        int completedCount,
        int draftCount,
        int inProgressCount
) {
}
