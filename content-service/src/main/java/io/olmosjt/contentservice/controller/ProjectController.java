package io.olmosjt.contentservice.controller;

import io.olmosjt.common.dto.ProjectDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/content/projects")
@RequiredArgsConstructor
public class ProjectController {
//    private final ProjectService projectService;


    @GetMapping
    public ResponseEntity<List<ProjectDto>> getProjectsByTranslator(
            @RequestParam("translator-type") UUID translatorId
    ) {
        return ResponseEntity.ok(null);
    }

}
