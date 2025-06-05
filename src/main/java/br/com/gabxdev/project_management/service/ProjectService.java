package br.com.gabxdev.project_management.service;

import br.com.gabxdev.core.common.AuthUtil;
import br.com.gabxdev.core.properties.FleetHubProperties;
import br.com.gabxdev.core.properties.ServiceRegister;
import br.com.gabxdev.iam.authorization.AuthorizationGuard;
import br.com.gabxdev.project_management.domain.Project;
import br.com.gabxdev.project_management.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;

    private final AuthUtil authUtil;

    private final AuthorizationGuard authorizationGuard;

    private final FleetHubProperties fleetHubProperties;

    public Project save(Project project) {
        authorizationGuard.checkReadPermission(getServiceRegister());

        var accountId = authUtil.getCurrentUser().getAccountId();
        project.setAccountId(accountId);

        return repository.save(project);
    }

    private ServiceRegister getServiceRegister() {
        return fleetHubProperties.getProjectManagement();
    }
}
