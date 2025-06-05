package br.com.gabxdev.project_management.controller;

import br.com.gabxdev.core.common.HeaderUtils;
import br.com.gabxdev.project_management.mapper.ProjectMapper;
import br.com.gabxdev.project_management.request.ProjectPostRequest;
import br.com.gabxdev.project_management.response.ProjectPostResponse;
import br.com.gabxdev.project_management.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    private final HeaderUtils headerUtils;

    private final ProjectMapper mapper;

    @PostMapping(headers = HttpHeaders.AUTHORIZATION)
    public ResponseEntity<ProjectPostResponse> saveProject(@Valid @RequestBody ProjectPostRequest request) {
        var entity = mapper.toEntity(request);

        var project = projectService.save(entity);

        var projectPostResponse = mapper.toProjectPostResponse(project);

        var headerLocation = headerUtils.createHeaderLocation(projectPostResponse.id().toString());

        return ResponseEntity.created(headerLocation).body(projectPostResponse);
    }
}
