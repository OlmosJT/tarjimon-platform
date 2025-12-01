package io.olmosjt.translatorapi.service;

import io.olmosjt.translatorapi.domain.dto.CreateApplicationRequest;
import io.olmosjt.common.entity.identity.TranslatorApplicationEntity;

import java.util.List;
import java.util.UUID;

public interface TranslatorApplicationService {

    TranslatorApplicationEntity submitApplication(UUID userId, CreateApplicationRequest request);

    List<TranslatorApplicationEntity> getMyApplications(UUID userId);

    // Admin only methods
    List<TranslatorApplicationEntity> getAllPendingApplications();

    void approveApplication(UUID applicationId, UUID reviewerId);

    void rejectApplication(UUID applicationId, UUID reviewerId, String reason);
}
