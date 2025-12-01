package io.olmosjt.contentservice.service;

import io.olmosjt.common.dto.TranslatorStatsDto;

import java.util.UUID;

public interface TranslatorService {
    TranslatorStatsDto getTranslatorStats(UUID translatorId);
}
