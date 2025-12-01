package io.olmosjt.translatorapi.controller;

import io.olmosjt.translatorapi.service.TranslatorProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/translators/profile")
@RequiredArgsConstructor
public class TranslatorProfileController {
    private final TranslatorProfileService profileService;


}
