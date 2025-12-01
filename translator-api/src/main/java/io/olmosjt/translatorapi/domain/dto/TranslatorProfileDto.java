package io.olmosjt.translatorapi.domain.dto;

import io.olmosjt.translatorapi.domain.enums.TranslatorBadgeType;
import java.util.UUID;

public record TranslatorProfileDto(
        UUID id,
        UUID userId,
        String displayName,
        String bio,
        String avatarUrl,
        TranslatorBadgeType badge,
        Integer totalProjects,
        Integer totalFollowers
) {}
