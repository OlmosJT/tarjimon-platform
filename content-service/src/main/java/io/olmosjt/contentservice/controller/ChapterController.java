package io.olmosjt.contentservice.controller;

import io.olmosjt.common.dto.ChapterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/content/projects/{projectId}/chapters")
@RequiredArgsConstructor
public class ChapterController {

//    private final ChapterService chapterService;

    @GetMapping
    public ResponseEntity<List<ChapterDto>> getChaptersByProject(
            @PathVariable UUID projectId
    ) {
        return ResponseEntity.ok(null); /*chapterService.getChaptersByProject(projectId)*/
    }

}
