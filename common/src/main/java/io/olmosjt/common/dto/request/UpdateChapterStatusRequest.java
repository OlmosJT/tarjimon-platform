package io.olmosjt.common.dto.request;

import io.olmosjt.common.enums.ChapterStatus;

import java.util.UUID;

public record UpdateChapterStatusRequest(
        UUID chapterId,
        ChapterStatus chapterStatus
) {
}
