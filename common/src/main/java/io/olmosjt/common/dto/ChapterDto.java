package io.olmosjt.common.dto;

import io.olmosjt.common.enums.ChapterStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ChapterDto(
        UUID id,
        UUID projectId,
        Integer sequenceNumber,
        String title,
        String googleDocId,
        String googleDocUrl,
        String contentBody,
        ChapterStatus status,
        OffsetDateTime publishedAt,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
