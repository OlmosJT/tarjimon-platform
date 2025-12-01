package io.olmosjt.translatorapi.service;

import io.olmosjt.translatorapi.domain.dto.TranslatorProfileDto;

import java.util.UUID;

public interface TranslatorProfileService {
    /**
     * Get profile by User ID (not the profile ID).
     */
    TranslatorProfileDto getProfileByUserId(UUID userId);

    /**
     * Get public profile by Translator ID.
     */
    TranslatorProfileDto getProfileById(UUID profileId);

    /**
     * Updates the current user's translator profile.
     */
    TranslatorProfileDto updateProfile(UUID userId, String displayName, String bio, String avatarUrl);

    /**
     * Internal use: Create a profile automatically when an application is approved.
     */
    void createProfile(UUID userId, String fullName);
}
