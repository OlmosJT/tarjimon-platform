package io.olmosjt.common.dto;

import io.olmosjt.common.enums.ProjectStatus;
import io.olmosjt.common.enums.ProjectType;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record ProjectDto(
        UUID id,
        UUID translatorId,
        String title,
        String originalAuthorName,
        String originalLanguage,
        String targetLanguage,
        String description,
        String authorDescription,
        String coverImageUrl,
        ProjectType projectType,
        String genre,
        List<String> tags,
        ProjectStatus status,
        Integer totalChapters,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
