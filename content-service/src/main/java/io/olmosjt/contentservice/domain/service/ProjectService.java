package io.olmosjt.contentservice.domain.service;

import io.olmosjt.common.dto.ProjectDto;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    List<ProjectDto> getProjectsByTranslator(UUID translatorId);
}
