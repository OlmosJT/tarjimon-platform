package io.olmosjt.contentservice.domain.service;

import io.olmosjt.common.dto.ChapterDto;

import java.util.List;
import java.util.UUID;

public interface ChapterService {
    List<ChapterDto> getChaptersByProject(UUID projectId);
}
