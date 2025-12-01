package io.olmosjt.translatorapi.domain.dto;

public record TranslatorStatsDto(
        Integer totalFollowers,
        Integer completedProjects,
        Integer totalChapters
) {
}
