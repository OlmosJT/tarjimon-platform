package io.olmosjt.translatorapi.controller;

import io.olmosjt.translatorapi.domain.dto.TranslatorStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/translators/dashboard")
@RequiredArgsConstructor
public class TranslatorDashboardController {


    @GetMapping("/stats")
    public ResponseEntity<TranslatorStatsDto> getMyStats(@AuthenticationPrincipal UserDetails userDetails) {
        // TODO: In a real app, resolve User ID from UserDetails
        // For now, assuming we find the profile via email or similar logic
        // User user = userRepository.findByEmail(userDetails.getUsername())...

//        var profile = profileRepository.findByUserId(UUID.fromString("..."))
//                .orElseThrow(() -> new RuntimeException("Profile not found"));
//
//        // Note: 'totalChapters' might need to be fetched via FeignClient from ContentService
//        // or calculated if you add a counter column to the profile table.
//        return ResponseEntity.ok(new TranslatorStatsDto(
//                profile.getTotalFollowers(),
//                profile.getTotalProjects(), // Assuming this counts completed ones
//                402 // Placeholder or fetched value for Total Chapters
//        ));
        return ResponseEntity.ok(null);
    }
}
