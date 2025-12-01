package io.olmosjt.contentservice.service;

import io.olmosjt.common.dto.ProjectDto;
import io.olmosjt.common.dto.ProjectStatsDto;
import io.olmosjt.common.dto.request.CreateProjectRequest;
import io.olmosjt.common.enums.ProjectStatus;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectService {
    /**
     * Includes projects in status of DRAFT, ONGOING, COMPLETED, DROPPED (DELETED)
     * @param translatorId is UUID
     * @return List of Projects belong to given Translator
     */
    List<ProjectDto> getProjectsByTranslator(UUID translatorId);

    List<ProjectDto> getOngoingProjectsByTranslator(UUID translatorId);
    List<ProjectDto> getCompletedProjectsByTranslator(UUID translatorId);
    List<ProjectDto> getDroppedProjectsByTranslator(UUID translatorId);

    Optional<ProjectDto> getProjectById(UUID projectId);

    ProjectDto updateProjectStatus(UUID projectId, ProjectStatus newStatus);

    void publishProject(UUID projectId);


    ProjectStatsDto getProjectStatistics(UUID projectId);
    EnumSet<ProjectStatus> getAvailableProjectStatuses();

    ProjectDto createProject(CreateProjectRequest request);
}


