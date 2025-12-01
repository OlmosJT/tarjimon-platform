package io.olmosjt.contentservice.service;

import io.olmosjt.common.dto.CommentStatsDto;
import io.olmosjt.common.dto.DashboardCommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TranslatorCommentsService {
    Page<DashboardCommentDto> getDashboardComments(UUID translatorId, Pageable pageable);

    void replyToComment(UUID originalCommentId, UUID translatorUserId, String content);

    CommentStatsDto getCommentStats(UUID translatorId);
}
