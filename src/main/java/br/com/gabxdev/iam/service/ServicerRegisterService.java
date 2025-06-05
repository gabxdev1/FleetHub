package br.com.gabxdev.iam.service;

import br.com.gabxdev.core.properties.FleetHubProperties;
import br.com.gabxdev.core.properties.ServiceRegister;
import br.com.gabxdev.iam.domain.ServiceCatalog;
import br.com.gabxdev.iam.repository.ServiceCatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ServicerRegisterService implements ApplicationRunner {

    private final ServiceCatalogRepository serviceCatalogRepository;

    private final FleetHubProperties fleetHubProperties;

    @Override
    public void run(ApplicationArguments args) {
        var iam = fleetHubProperties.getIam();
        var serviceCatalogIam = createServiceCatalog(iam);

        var projectManagement = fleetHubProperties.getProjectManagement();
        var serviceCatalogProject = createServiceCatalog(projectManagement);

        var serviceCatalogList = List.of(serviceCatalogIam, serviceCatalogProject);
        serviceCatalogRepository.saveAll(serviceCatalogList);
    }

    private ServiceCatalog createServiceCatalog(ServiceRegister serviceRegister) {
        return ServiceCatalog
                .builder()
                .id(serviceRegister.getId())
                .name(serviceRegister.getName())
                .code(serviceRegister.getCode())
                .description(serviceRegister.getDescription())
                .build();
    }
}
