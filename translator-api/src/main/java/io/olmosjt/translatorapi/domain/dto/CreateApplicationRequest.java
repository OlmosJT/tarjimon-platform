package io.olmosjt.translatorapi.domain.dto;

import io.olmosjt.translatorapi.domain.enums.TranslatorBadgeType;
import java.util.List;

public record CreateApplicationRequest(
        String fullName,
        String profession,
        TranslatorBadgeType badgeRequest,
        List<String> knownLanguages,
        String testSubmissionText
) {}
