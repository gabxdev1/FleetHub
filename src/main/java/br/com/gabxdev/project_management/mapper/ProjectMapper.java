package br.com.gabxdev.project_management.mapper;

import br.com.gabxdev.project_management.domain.Project;
import br.com.gabxdev.project_management.request.ProjectPostRequest;
import br.com.gabxdev.project_management.response.ProjectPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectMapper {
    Project toEntity(ProjectPostRequest request);

    ProjectPostResponse toProjectPostResponse(Project project);
}
