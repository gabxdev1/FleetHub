package br.com.gabxdev.iam.service;

import br.com.gabxdev.core.exception.NotFoundException;
import br.com.gabxdev.iam.domain.ServiceCatalog;
import br.com.gabxdev.iam.repository.ServiceCatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceCatalogService {

    private final ServiceCatalogRepository serviceCatalogRepository;

    public ServiceCatalog findByIdOrThrowNotFound(Long id) {
        return serviceCatalogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Service Catalog Not Found"));
    }
}
