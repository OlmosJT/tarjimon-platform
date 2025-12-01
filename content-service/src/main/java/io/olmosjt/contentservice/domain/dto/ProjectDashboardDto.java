package io.olmosjt.contentservice.domain.dto;

import java.time.Instant;
import java.util.UUID;

public record ProjectDashboardDto(
        UUID id,
        String title,
        String coverImageUrl,
        Instant lastUpdatedOrPublished,
        String status, // "ONGOING", "COMPLETED"
        String statusLabel // "In Progress", "Completed" (For UI)
) {

}
