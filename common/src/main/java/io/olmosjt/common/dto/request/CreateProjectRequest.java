package io.olmosjt.common.dto.request;

import io.olmosjt.common.enums.ProjectType;

import java.util.List;
import java.util.UUID;

public record CreateProjectRequest(
        UUID translatorId,
        String projectName,
        ProjectType projectType,
        String originalAuthorName,
        String originalAuthorDescription,
        String originalLanguage,
        String targetLanguage,
        String ProjectDescription,
        String genre,
        List<String> tags,
        String coverImageUrl
) {
}
