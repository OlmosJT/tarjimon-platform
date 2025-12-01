package io.olmosjt.common.dto.request;

import java.util.UUID;

public record CreateChapterRequest(
        UUID projectId,
        String title
) {
}
