package io.olmosjt.contentservice.service;

import io.olmosjt.common.dto.ChapterDto;
import io.olmosjt.common.dto.request.CreateChapterRequest;
import io.olmosjt.common.enums.ChapterStatus;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChapterService {
    List<ChapterDto> getChaptersByProject(UUID projectId);
    List<ChapterDto> getChaptersByProject(UUID projectId, Sort sort);

    Optional<ChapterDto> getChapterById(UUID chapterId);

    ChapterDto createChapter(UUID projectId, CreateChapterRequest request);

    ChapterDto updateChapterDetails(UUID chapterId, ChapterDto updateDto);

    void updateChapterStatus(UUID chapterId, ChapterStatus newStatus);
}
